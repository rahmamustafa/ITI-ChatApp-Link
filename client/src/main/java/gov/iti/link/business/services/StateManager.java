package gov.iti.link.business.services;

import gov.iti.link.business.DTOs.UserDTO;

public class StateManager {

    private UserDTO user;
    private ClientServices client;

   

    private final static StateManager INSTANCE  = new StateManager();

    public static StateManager getInstance() {
        return INSTANCE ;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void deleteUser(){
        this.user = null;
    }
    public ClientServices getClient() {
        return client;
    }

    public void setClient(ClientServices client) {
        this.client = client;
    }
    public void deleteClient(){
        this.client = null;
    }
    private StateManager() {}

}
