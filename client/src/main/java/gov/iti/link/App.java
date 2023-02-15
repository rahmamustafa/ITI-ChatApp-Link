package gov.iti.link;

import java.io.IOException;
import java.lang.Thread.State;
import java.rmi.RemoteException;

import gov.iti.link.business.services.ClientServices;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.StateManager;
import gov.iti.link.business.services.UserAuth;
import gov.iti.link.business.services.UserService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class App extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        StageManager stageManager = StageManager.getInstance();
        stageManager.initStage(stage);
        if(UserAuth.isAuthorized())
            stageManager.switchToHome();
        else
            stageManager.switchToLogin();
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                try {
                    ClientServices currentCleint =  StateManager.getInstance().getClient();
                    ServiceManager.getInstance().getUserService().userLoggedOut(currentCleint, currentCleint.getUserDTO());
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
             catch (NullPointerException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
               Platform.exit();

               System.exit(0);
              
            }
         });
    }

    public static void main(String[] args) {
        launch();
        
    }

}