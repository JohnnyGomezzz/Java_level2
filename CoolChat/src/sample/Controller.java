package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField inputField;
    @FXML
    private ListView<String> contactsView;
    @FXML
    private ListView<String> chatWindow;
    @FXML
    private Button sendButton;

    @FXML
    public void initialize()
    {
        contactsView.setItems(contacts);
    }

    private ObservableList<String> contacts = FXCollections.observableArrayList("Nagibator666", "-Mouse-",
            "Zveller", "1mlnAlyhRoz");

    @FXML
    public void addMessage()
    {
        String message = inputField.getText();

        addMessageToList(message);

        chatWindow.scrollTo(chatWindow.getItems().size() - 1);
    }

    public void addMessageToList(String message)
    {
        if (!message.isBlank())
        {
            chatWindow.getItems().add(message);
        }
        inputField.clear();
    }

    @FXML
    public void clearChatWindow()
    {
        chatWindow.getItems().clear();
    }

    @FXML
    public void exit()
    {
        System.exit(0);
    }
}
