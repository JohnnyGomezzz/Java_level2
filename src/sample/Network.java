package sample;

import controllers.ChatMainController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {

    public static final String SERVER_ADDRESS = "localhost";
    public static final int SERVER_PORT = 8189;

    private final String host;
    private final int port;

    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    private Socket socket;

    public Network()
    {
        this(SERVER_ADDRESS, SERVER_PORT);
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect()
    {
        try {
            Socket socket = new Socket("localhost", 8189);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            return true;

        } catch (IOException e) {
            System.out.println("Соединение не было установлено.");
            e.printStackTrace();
            return false;
        }
    }

    public void close()
    {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitMessage(ChatMainController chatMainController)
    {
        Thread thread = new Thread(() -> {
            try {
                while (true) {

                    String message = dataInputStream.readUTF();
                    chatMainController.appendMessage(message);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                System.out.println("Соединение потеряно!");
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
