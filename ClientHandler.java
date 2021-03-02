package server.src.chat.handler;

import NetworkClientServer.src.clientserver.Command;
import server.src.chat.MyServer;
import server.src.chat.auth.AuthService;
import NetworkClientServer.src.clientserver.CommandType;
import NetworkClientServer.src.clientserver.commands.AuthCommandData;
import NetworkClientServer.src.clientserver.commands.PrivateMessageCommandData;
import NetworkClientServer.src.clientserver.commands.PublicMessageCommandData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler {
    private final MyServer myServer;
    private final Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String clientUsername;

    public ClientHandler(MyServer myServer, Socket clientSocket) {
        this.myServer = myServer;
        this.clientSocket = clientSocket;
    }

    public void handle() throws IOException {
        in = new ObjectInputStream(clientSocket.getInputStream());
        out = new ObjectOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            try {
                clientSocket.setSoTimeout(120000);
                authentication();
                readMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void authentication() throws IOException {

        while (true) {

            Command command = readCommand();
            if (command == null) {
                continue;
            }
            if (command.getType() == CommandType.AUTH) {

                boolean isSuccessAuth = processAuthCommand(command);
                if (isSuccessAuth) {
                    break;
                }
            } else {
                sendMessage(Command.authErrorCommand("Ошибка авторизации"));

            }
        }
    }

    private boolean processAuthCommand(Command command) throws IOException {
        AuthCommandData cmdData = (AuthCommandData) command.getData();
        String login = cmdData.getLogin();
        String password = cmdData.getPassword();

        AuthService authService = myServer.getAuthService();
        this.clientUsername = authService.getUsernameByLoginAndPassword(login, password);
        if (clientUsername != null) {
            if (myServer.isUsernameBusy(clientUsername)) {
                sendMessage(Command.authErrorCommand("Логин уже используется"));
                return false;
            }

            sendMessage(Command.authOkCommand(clientUsername));
            String message = String.format(">>> %s присоединился к чату", clientUsername);
            myServer.broadcastMessage(this, Command.messageInfoCommand(message, null));
            myServer.subscribe(this);
            return true;
        } else {
            sendMessage(Command.authErrorCommand("Логин или пароль не подходят"));
            return false;
        }
    }

    private Command readCommand() throws IOException {
        try {
            return (Command) in.readObject();
        } catch (ClassNotFoundException e) {
            String errorMessage = "Получен неизвестный объект";
            System.err.println(errorMessage);
            e.printStackTrace();
            return null;
        }
    }

    private void readMessage() throws IOException {
        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }

            switch (command.getType()) {
                case END:
                    return;
                case PUBLIC_MESSAGE: {
                    PublicMessageCommandData data = (PublicMessageCommandData) command.getData();
                    String message = data.getMessage();
                    String sender = data.getSender();
                    myServer.broadcastMessage(this, Command.messageInfoCommand(message, sender));
                    break;
                }
                case PRIVATE_MESSAGE:
                    PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                    String recipient = data.getReceiver();
                    String message = data.getMessage();
                    myServer.sendPrivateMessage(recipient, Command.messageInfoCommand(message, recipient));
                    break;
                default:
                    String errorMessage = "Неизвестный тип команды" + command.getType();
                    System.err.println(errorMessage);
                    sendMessage(Command.errorCommand(errorMessage));
            }
        }
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void sendMessage(Command command) throws IOException {
        out.writeObject(command);
    }
}
