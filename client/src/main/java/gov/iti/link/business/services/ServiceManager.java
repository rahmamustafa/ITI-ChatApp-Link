package gov.iti.link.business.services;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.commons.codec.digest.DigestUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public class ServiceManager {
    private final int PORT_NUMBER = 5678;
    private final String USER_SERVICE = "user-service";
    private UserService userService;

    private static ServiceManager instance = new ServiceManager();
    private Registry reg;
    private boolean connectionEstablished = false;
    private StageManager stageManager;

    public static ServiceManager getInstance() {
        return instance;
    }

    private ServiceManager() {
        stageManager = StageManager.getInstance();
    }

    public void connectToServer() {
        try {
            reg = LocateRegistry.getRegistry(PORT_NUMBER);
            userService = (UserService) reg.lookup(USER_SERVICE);
            connectionEstablished = true;
            if (UserAuth.isAuthorized())
                stageManager.switchToHome();
            else
                stageManager.switchToLogin();

        } catch (RemoteException | NotBoundException e) {
            System.out.println("Server Error, " + e.getMessage());
            connectionEstablished = false;
            stageManager.switchToNoConnection();
            // e.printStackTrace();
            // StageManager.getInstance().loadView("ServerDown");
            ///////////////////////////////////
            // handel sever is dawon

        }

    }

    public UserService getUserService() {
        return userService;
    }

    public String hashingPassword(String Passoword) {

        return Passoword = DigestUtils.sha256Hex(Passoword);
    }

    public boolean isConnectionEstablished() {
        return connectionEstablished;
    }

}
