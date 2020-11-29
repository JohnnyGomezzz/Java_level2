package client;

import client.controllers.AuthDialogController;
import client.controllers.ChatMainController;
import client.controllers.InfoController;
import client.models.Network;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NetworkClient extends Application {

    public Stage primaryStage;
    private Stage authStage;
    private Network network;
    private ChatMainController chatMainController;

    private static ObservableList<String> contactsList = FXCollections.observableArrayList(
            "JohnnyGomezzz", "Pavel_Saf", "Ivan_Saf4ik"
    );

    public static ObservableList<String> getContactsList()
    {
        return contactsList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;

        FXMLLoader authLoader = new FXMLLoader();
        authLoader.setLocation(NetworkClient.class.getResource("views/auth-dialog.fxml"));

        Parent page = authLoader.load();
        authStage = new Stage();

        authStage.setTitle("CoolChat! - authentication");
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        authStage.setScene(scene);
        authStage.getIcons().add(new Image("client/views/images/coolchat_logo_32.png"));
        authStage.show();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(NetworkClient.class.getResource("/client/views/chatmain.fxml"));

        Parent root = loader.load();
        Scene rootScene = new Scene(root);

        primaryStage.setScene(rootScene);
        primaryStage.getIcons().add(new Image("client/views/images/coolchat_logo_32.png"));
        //primaryStage.show();

        network = new Network();

        AuthDialogController authDialogController = authLoader.getController();
        authDialogController.setNetwork(network);
        authDialogController.setNetworkClient(this);

        if(!network.connect())
        {
            showErrorMessage("", "Ошибка подключения к серверу");
        }
        chatMainController = loader.getController();
        chatMainController.setNetwork(network);
        chatMainController.setMainApp(this);

        //network.waitMessage(chatMainController);
        primaryStage.setOnCloseRequest(windowEvent -> network.close());
    }

    public static void showErrorMessage(String message, String errorMessage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Проблемы с соединением");
        alert.setHeaderText(errorMessage);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showInfo()
    {
        try {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(NetworkClient.class.getResource("/client/views/info.fxml"));
        Parent infoPage = loader.load();
        Stage infoStage = new Stage();
        infoStage.setTitle("CoolChat! - info");
        infoStage.initOwner(primaryStage);
        infoStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(infoPage);
        infoStage.setScene(scene);
        infoStage.getIcons().add(new Image("client/views/images/coolchat_logo_32.png"));

        infoStage.show();
        InfoController controller = loader.getController();
        controller.setInfoStage(infoStage);

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void openChat(){
        authStage.close();
        primaryStage.show();
        primaryStage.setTitle("CoolChat! - " + network.getUsername());
        network.waitMessage(chatMainController);
    }
}
