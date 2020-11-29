package client.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import client.NetworkClient;
import client.models.Network;

import java.io.IOException;

public class ChatMainController {

    @FXML
    public ListView<String> contacts;
    @FXML
    private Button sendButton;
    @FXML
    private TextArea chatWindow;
    @FXML
    private TextField messageWindow;

    private Network network;
    private NetworkClient mainApp;

    public void setMainApp(NetworkClient mainApp)
    {
        this.mainApp = mainApp;
    }

    public void initialize()
    {
        contacts.setItems(FXCollections.observableArrayList(NetworkClient.getContactsList()));
        sendButton.setOnAction(event -> ChatMainController.this.sendMessage());
        messageWindow.setOnAction(event -> ChatMainController.this.sendMessage());
    }

    public void sendMessage()
    {
        String message = messageWindow.getText();
        if (!message.isBlank()) {
            appendMessage("Я: " + message);

            /*----------в текущий чат---------------*/
        }
        messageWindow.clear();

        try {
            network.getDataOutputStream().writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
            String errorMessage = "Ошибка при отправке сообщения";
            NetworkClient.showErrorMessage(e.getMessage(), errorMessage);
        }
    }

    public void setNetwork(Network network)
    {
        this.network = network;
    }

    public void appendMessage(String message)
    {
        chatWindow.appendText(message);
        chatWindow.appendText(System.lineSeparator());
    }

    @FXML
    private void handleAbout() {
        mainApp.showInfo();
    }

    @FXML
    private void handleClear()
    {
        chatWindow.clear();
    }

    @FXML
    private void handleExit()
    {
        System.exit(0);
    }
}
