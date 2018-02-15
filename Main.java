import design.Colors;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import scenes.start.StartScene;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane s = new StackPane();
        s.setMinSize(600, 400);

        Rectangle rect = new Rectangle(300, 300);
        rect.setFill(Colors.BTN_GOOD.getColor());

        StackPane st = new StackPane();
        st.setBackground(Colors.BASE_BG.getBackground());
        st.setMinSize(500, 400);
        st.setMaxSize(500, 400);

        s.getChildren().addAll(st, rect);

        primaryStage.setScene(new Scene(s));
        primaryStage.show();
    }
}