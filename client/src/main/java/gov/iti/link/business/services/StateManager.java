package gov.iti.link.business.services;

import gov.iti.link.business.DTOs.UserDTO;

public class StateManager {

    private UserDTO user;
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

    private StateManager() {}

}
