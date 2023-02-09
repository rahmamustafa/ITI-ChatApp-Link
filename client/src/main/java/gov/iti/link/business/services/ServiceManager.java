package gov.iti.link.business.services;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.commons.codec.digest.DigestUtils;

public class ServiceManager {
    private final int PORT_NUMBER = 5678;
    private final String USER_SERVICE = "user-service";
    private UserService userService;

    private static ServiceManager instance = new ServiceManager();
    private Registry reg;

    public static ServiceManager getInstance() {
        return instance;
    }

    private ServiceManager() {
        try {
            reg = LocateRegistry.getRegistry(PORT_NUMBER);
            userService = (UserService) reg.lookup(USER_SERVICE);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }

    public UserService getUserService() {
        return userService;
    }

    public String hashingPassword(String Passoword){
        
        return Passoword=DigestUtils.sha256Hex(Passoword);
    }
}
