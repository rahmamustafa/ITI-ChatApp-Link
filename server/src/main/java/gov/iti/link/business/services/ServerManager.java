package gov.iti.link.business.services;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerManager {
    private Registry registry;
    private final int PORT_NUMBER = 5678;
    private final String USER_SERVICE = "user-service";

    private static final ServerManager instance = new ServerManager();
    UserServiceImp serviceImp;
    public static ServerManager getInstance() {
        return instance;
    }

    private ServerManager() {
        try {
            this.registry = LocateRegistry.createRegistry(PORT_NUMBER);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void bindUserService() {
        if (this.registry == null)
            return;
        
        try {
            serviceImp = new UserServiceImp();
            registry.bind(USER_SERVICE, serviceImp);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public void unbindUserService() {
        if (this.registry == null)
            return;
        try {
            this.registry.unbind(USER_SERVICE);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }
    public UserServiceImp getUserSeviceImp(){
           return serviceImp;
    }
}
