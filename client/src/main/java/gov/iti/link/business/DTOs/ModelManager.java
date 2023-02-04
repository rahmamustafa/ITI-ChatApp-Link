package gov.iti.link.business.DTOs;

public class ModelManager {
    
    private static final ModelManager instance = new ModelManager();

    private UserDTO userInstance = new UserDTO();

    private ModelManager(){}

    public static ModelManager getInstance(){
        return instance;
    }

    public UserDTO getUserInstance(){
        return userInstance;
    }

}
