package sample;

import controllers.ChatMainController;
import controllers.InfoController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;

    private static ObservableList<String> contactsList = FXCollections.observableArrayList(
            "Nagibator666", "-Mouse-", "Zveller", "1mlnAlyhRoz"
    );

    public static ObservableList<String> getContactsList()
    {
        return contactsList;
    }

    @Override
    public void start(Stage primaryStage){

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CoolChat! - messenger");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/chatmain.fxml"));
            AnchorPane chatMain = (AnchorPane)loader.load();
            Scene scene = new Scene(chatMain);
            primaryStage.setScene(scene);

            primaryStage.show();

            Network network = new Network();

            if(!network.connect())
            {
                showErrorMessage("", "Ошибка подключения к серверу");
            }

            ChatMainController chatMainController = loader.getController();
            chatMainController.setNetwork(network);
            chatMainController.setMainApp(this);

            network.waitMessage(chatMainController);

            primaryStage.setOnCloseRequest(windowEvent -> network.close());

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            loader.setLocation(MainApp.class.getResource("/views/info.fxml"));
            AnchorPane page = (AnchorPane)loader.load();
            Stage infoStage = new Stage();
            infoStage.setTitle("CoolChat! - info");
            infoStage.initOwner(primaryStage);
            infoStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            infoStage.setScene(scene);

            infoStage.show();

            InfoController controller = loader.getController();
            controller.setInfoStage(infoStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
