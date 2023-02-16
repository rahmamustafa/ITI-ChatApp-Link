package gov.iti.link.business.services;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
            registry.rebind(USER_SERVICE, serviceImp);
            // UnicastRemoteObject.exportObject(registry, PORT_NUMBER);
        } catch (RemoteException  e) {
            e.printStackTrace();
        }
    }

    public void unbindUserService() {
        if (this.registry == null)
            return;
        try {
            
            this.registry.unbind(USER_SERVICE);
            // UnicastRemoteObject.unexportObject(serviceImp,true);
            System.out.println("services unbinded");
            serviceImp.disconnectUsers();
        } catch (RemoteException | NotBoundException  e) {
            e.printStackTrace();
        }

    }
    public UserServiceImp getUserSeviceImp(){
           return serviceImp;
    }
}
