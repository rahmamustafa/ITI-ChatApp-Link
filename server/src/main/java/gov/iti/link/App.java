package gov.iti.link;



import gov.iti.link.business.services.ServerManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        ServerManager serverManager = ServerManager.getInstance();
        serverManager.bindUserService();
        var label = new Label("Server running..");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();


        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
            System.out.println("Exiting");
        });
  
       
    }

    public static void main(String[] args) {
        launch();
    }

}