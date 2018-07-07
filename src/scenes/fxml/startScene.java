package scenes.fxml;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class startScene extends Application {

    @FXML
    private Stage stage;
    @FXML
    private Button testBtn;
    private Parent newScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("startScene.fxml"));

        Scene scene = new Scene(root, 1275, 850);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void setNewScene(ActionEvent event) throws IOException {
        System.out.println("test");
        newScene = FXMLLoader.load(getClass().getResource("scene2.fxml"));
//        stage.getScene().setRoot(newScene);

        testBtn.getScene().setRoot(newScene);

    }

}
