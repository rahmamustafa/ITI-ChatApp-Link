package  gov.iti.link.presentation.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.Vector;

import gov.iti.link.business.DTOs.ContactDto;
import gov.iti.link.business.DTOs.GroupDto;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StateManager;
import gov.iti.link.business.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class FriendsListController  implements Initializable{


    ObservableList<CheckBox> friendsList = FXCollections.observableArrayList();
    Vector<ContactDto> allContacts;
    Vector<String> allGroupMembers;
    StateManager stateManager = StateManager.getInstance();
    UserService userService = ServiceManager.getInstance().getUserService();


    @FXML
    // private ListView<CheckBox> lvFriendsList;
    private VBox vbListContainer;
    @FXML
    private Button btnAddToGroup;

    private GroupDto groupDto;

    public FriendsListController() {

    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       
        //lvFriendsList.setItems(friendsList);
        //todo notify when admin add someone to group
        //todo notify allgroupmembers in list
        
    }
    
    @FXML
    void onAddToGroup(ActionEvent event) {
        for(CheckBox label:friendsList){
            System.out.println(label.isSelected() + " " + label.getId());
            if(label.isSelected())
                try {
                    userService.addMemberToGroup(groupDto,label.getId());
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
     }
    }
    
    void setGroupDto(GroupDto groupDto){
        this.groupDto = groupDto;
        try {
            allContacts = userService.getAllContacts(stateManager.getUser().getPhone());
            allGroupMembers = (groupDto.getAllMembers());
            
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(allGroupMembers.size());

        for (ContactDto contactDto : allContacts) {
            if(!allGroupMembers.contains(contactDto.getPhoneNumber())){
                System.out.println(contactDto.getName());
                addCardinListView(contactDto);
            }
        }
    }
    void addCardinListView(ContactDto contactDto) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/checkBox.fxml"));
        CheckBox label;
        try {
            label = fxmlLoader.load();
            label.setId(contactDto.getPhoneNumber());
            label.setText(contactDto.getName());
            CheckBoxController checkBoxController = fxmlLoader.getController();
            checkBoxController.setCircleImage(contactDto.getImage());
            friendsList.add(label);
            vbListContainer.getChildren().add(0,label);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
