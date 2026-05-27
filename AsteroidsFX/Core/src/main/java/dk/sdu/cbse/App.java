package dk.sdu.cbse;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class App extends Application{


    @Override
    public void start (Stage stage){
        Pane root = new Pane();

        Scene scene = new Scene (root, 500, 500);

        stage.setTitle("Asteroids");
    }
}
