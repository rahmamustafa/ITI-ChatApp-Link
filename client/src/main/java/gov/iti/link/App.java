package gov.iti.link;

import java.io.IOException;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.UserAuth;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        StageManager stageManager = StageManager.getInstance();
        stageManager.initStage(stage);
        if(UserAuth.isAuthorized())
            stageManager.switchToHome();
        else
            stageManager.switchToRegister();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}