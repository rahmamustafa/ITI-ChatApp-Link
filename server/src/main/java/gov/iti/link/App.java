package gov.iti.link;



import java.io.IOException;

import gov.iti.link.business.services.ServerManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ServerManager serverManager = ServerManager.getInstance();
        serverManager.bindUserService();
        var label = new Label("Server running..");
      // var scene = new Scene(new StackPane(label), 640, 480);
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(String.format("/view/%s.fxml", "ManageService")));
                 Scene scene = new Scene(fxmlLoader.load());

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