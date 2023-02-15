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
        ServiceManager.getInstance().connectToServer();
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                try {
                    ClientServices currentCleint = StateManager.getInstance().getClient();
                    if (currentCleint != null)
                        ServiceManager.getInstance().getUserService().userLoggedOut(currentCleint,
                                currentCleint.getUserDTO());
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                } catch (NullPointerException e2) {
                    e2.printStackTrace();
                } finally {
                    Platform.exit();
                    System.exit(0);
                }

            }
        });
    }

    public static void main(String[] args) {
        launch();

    }

}