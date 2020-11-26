package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {

    public static final int SERVER_PORT = 8189;

    public static void main(String[] args)
    {

        new EchoServer().start();

    }

    private void start()
    {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);) {
            System.out.println("Ожидание подключения...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Соединение установлено!");

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            Thread inputThread = null;

            inputThread = InputListeningThread(in);
            startOutputListening(out);

        }catch (IOException e) {
            System.out.println("Сервер остановлен.");
            e.printStackTrace();
        }
    }

    private Thread InputListeningThread(DataInputStream in)
    {
        Thread thread = new Thread(() -> {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                String message = in.readUTF();
                System.out.println("Сообщение пользователя: " + message);

                if (message.equals("/exit"))
                {
                    break;
                }
            } catch (IOException e) {
                System.out.println("Соединение закрыто.");
                e.printStackTrace();
                break;
            }
        }
        });
        thread.start();
        return thread;
    }


    private void startOutputListening(DataOutputStream out) throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            String consoleMessage = scanner.nextLine();
            out.writeUTF("Сообщение от сервера: " + consoleMessage);
            if(consoleMessage.equals("/exit"))
            {
                break;
            }
        }
    }

}
