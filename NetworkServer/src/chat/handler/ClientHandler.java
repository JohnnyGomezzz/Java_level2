package chat.handler;

import chat.MyServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    public static final String AUTH_CMD_PREFIX = "/auth";
    public static final String AUTHOK_CMD_PREFIX = "/authok";
    public static final String AUTHERR_CMD_PREFIX = "/autherr";

    private final MyServer myServer;
    private final Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private String clientUsername;
    private String targetUserName;
    private String privateMessage;

    public ClientHandler(MyServer myServer, Socket clientSocket) {
        this.myServer = myServer;
        this.clientSocket = clientSocket;
    }

    public void handle() throws IOException {
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            try {
                authentication();
                readMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void authentication() throws IOException {
        String message = in.readUTF();

        while (true){
            if (message.startsWith(AUTH_CMD_PREFIX)) {
                String[] parts = message.split("\\s+", 3);
                String login = parts[1];
                String password = parts[2];

                this.clientUsername = myServer.getAuthService().getUsernameByLoginAndPassword(login, password);
                if (clientUsername != null) {
                    if (myServer.isUsernameBusy(clientUsername)) {
                        out.writeUTF(AUTHERR_CMD_PREFIX + "Логин уже используется");
                    }
                    out.writeUTF(AUTHOK_CMD_PREFIX + " " + clientUsername);
                    myServer.subscribe(this);
                    myServer.broadcastMessage(" присоединился", this);
                    break;

                } else {
                    out.writeUTF(AUTHERR_CMD_PREFIX + " Логин или пароль не подходят");
                }

            }
            else {
                out.writeUTF(AUTHERR_CMD_PREFIX + "Ошибка авторизации");
            }
        }
    }

    private void readMessage() throws IOException {
        while (true){
            String message = in.readUTF();
            System.out.println("message | " + clientUsername + ": " + message);

            /*-------------на сервак-----------------*/

            if (message.startsWith("/end")){
                return;
            }

            if(message.startsWith("/w")) {
                String[] parts = message.split("\\s+", 3);
                targetUserName = parts[1];
                privateMessage = parts[2];

                out.writeUTF(privateMessage);
            }

            myServer.broadcastMessage(message, this);

        }
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public String getPrivateMessage(String s, String clientUsername) {
        return privateMessage;
    }

    public void sendMessage(String s, String senderName) throws IOException {
        out.writeUTF(senderName + ": " + s);

        /*----------в сеть отдаём имя отправителя---------------*/
    }
}
