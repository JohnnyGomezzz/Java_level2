package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InfoController {
    @FXML
    Button closeButton;

    @FXML
    public void initialize()
    {

    }

    @FXML
    private void closeButtonAction(){

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

}
