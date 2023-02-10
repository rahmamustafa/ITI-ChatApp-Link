package gov.iti.link.presentation.controllers;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;

import com.mysql.cj.jdbc.Blob;

import gov.iti.link.business.DTOs.ContactDto;
import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ClientServices;
import gov.iti.link.business.services.ClientServicesImp;
import gov.iti.link.business.services.InvitationsState;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.StateManager;
import gov.iti.link.business.services.UserService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;

import javafx.scene.text.Text;

public class ChatController implements Initializable {

    @FXML
    private AnchorPane MAIN_FRM;

    @FXML
    private ScrollPane scrollPaneChat;

    @FXML
    private ScrollPane SCROLL_BAR_CONTACTS;

    @FXML
    private TextField SEARCH_TXT;

    @FXML
    private HBox TITLE_FINAL_CONTAINER;

    @FXML
    private Button btnSend;

    @FXML
    private ListView<Parent> lstFriend;

    @FXML
    private VBox messageContainer;

    @FXML
    private TextArea txtMessage;

    @FXML
    private ImageView img;

    @FXML
    private Label lblUserName;
    @FXML
    private Label lblContactChat;
    @FXML
    Circle circleUserImage;

    @FXML
    private Label lblInvitesNotifications;

    private ServiceManager serviceManager;
    private UserService userService;
    private StateManager stateManager;
    private StageManager stageManager;

    Vector<ContactDto> allContacts;
    IntegerBinding noOfInvitations;
    BooleanBinding hasInvitations; 

    Vector<String> toPhones = new Vector<>();

    Map<String, VBox> chatVBoxs = new HashMap<>();
    String clickedContact;

    ObservableList<Parent> friendsList = FXCollections.observableArrayList();
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");

    ClientServices clientServices;

    public ChatController() {
        serviceManager = ServiceManager.getInstance();
        userService = serviceManager.getUserService();
        stateManager = StateManager.getInstance();
        List<InvitationDTO> invitations;
        try {
            invitations = userService.getInvitations(stateManager.getUser().getPhone());
            System.out.println("user invitations: " + invitations);
            InvitationsState.setInvitations(invitations);
            System.out.println("user obs invitations: " + InvitationsState.getInvitations());
            noOfInvitations = Bindings.size(InvitationsState.getInvitations());
            hasInvitations = noOfInvitations.greaterThan(0);
            stateManager.getUser().setInvitations(invitations);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        stageManager = StageManager.getInstance();
    }

    @FXML
    void sendMessage(ActionEvent event) {
        toPhones.clear();
        toPhones.add(clickedContact);
        try {
            String message = txtMessage.getText().trim();
            userService.sendMessage(stateManager.getUser().getPhone(), message, toPhones);
            chatVBoxs.get(clickedContact).getChildren().add(senderMessage(stateManager.getUser(),message,"rightMessage"));
            txtMessage.setText("");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Node senderMessage(UserDTO userDTO,String message,String type) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(String.format("/views/components/%s.fxml", type)));
        VBox node=loader.load();
        if(type.equals("rightMessage"))
            node.setAlignment(Pos.TOP_RIGHT);
        else
        node.setAlignment(Pos.TOP_LEFT);
        MessageController messageController = loader.getController();
        messageController.setImage(userDTO.getPicture());
        messageController.setMessage(message);
        messageController.setName(userDTO.getName());
        messageController.setTime(simpleDateFormat.format(new Date()));
        return node;
    }
    

    @FXML
    void onClickFriend(MouseEvent event) {
        System.out.println("clicked");
        btnSend.setDisable(false);

        try {
            clickedContact = lstFriend.getSelectionModel().getSelectedItem().getId();
            lblContactChat.setText(allContacts.stream()
                    .filter((contact) -> contact.getPhoneNumber().equals(clickedContact))
                    .map(cont -> cont.getName()).collect(Collectors.toList()).get(0));

            handleChatView(clickedContact);
        } catch (RuntimeException e) {

        }
    }

    private void handleChatView(String clickedContact) {
        if (chatVBoxs.get(clickedContact) == null) {
            chatVBoxs.put(clickedContact, new VBox());
        }
        VBox chat = chatVBoxs.get(clickedContact);
        scrollPaneChat.setContent(chat);

    }

    @FXML
    void onProfile() {
        stageManager.switchToProfile();
    }

    @FXML
    void onLogOut() {
        try {
            userService.userLoggedOut(clientServices, stateManager.getUser());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        stageManager.switchToLogin();

    }

    @FXML
    void onInvitesClick() {
        System.out.println("Show invites");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/invites-list.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showNewDialog() {
        System.out.println("Add contact");

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/add-contact.fxml"));
            DialogPane addDialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(addDialogPane);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btnSend.setDisable(true);
        lstFriend.setItems(friendsList);
       
        // lblInvitesNotifications.setText(String.valueOf(stateManager.getUser().getInvitations().size()));
        lblInvitesNotifications.visibleProperty().bind(hasInvitations);
        lblInvitesNotifications.textProperty().bind(noOfInvitations.asString());
        lblUserName.setText(stateManager.getUser().getName());
        try {
            clientServices = new ClientServicesImp(this);
            userService.userLoggedIn(clientServices, stateManager.getUser());

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            allContacts = userService.getAllContacts(stateManager.getUser().getPhone());
            createDatainListView(allContacts);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for(ContactDto contactDto:allContacts){
            chatVBoxs.put(contactDto.getPhoneNumber(), new VBox());
        }

    }

    private void createDatainListView(Vector<ContactDto> allContacts) {
        friendsList.clear();
        for (ContactDto contactDto : allContacts) {
            addCardinListView(contactDto);
        }
    }

    void addCardinListView(ContactDto contactDto) {
        String pageName = "lblcontact";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(String.format("/views/%s.fxml", pageName)));
        Parent label;
        try {
            label = fxmlLoader.load();
            label.setId(contactDto.getPhoneNumber());
            LabelContactController labelContactController = fxmlLoader.getController();
            labelContactController.setName(contactDto.getName());
            labelContactController.setImage(contactDto.getImageUrl());
            labelContactController.setPhone(contactDto.getPhoneNumber());
            labelContactController.setStatus(contactDto.isActive());
            friendsList.add(label);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addNewContact(String phoneNumber){
        try {
            addCardinListView(new ContactDto(userService.findByPhone(phoneNumber)));
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void changeOnFriendState(ContactDto contactDto) {
        for (Parent label : friendsList) {
            if (label.getId().equals(contactDto.getPhoneNumber())) {
                friendsList.remove(label);
                addCardinListView(contactDto);

            }
        }
   
    }

    public void recieveMessage(String message, UserDTO user) {
        try {
            chatVBoxs.get(user.getPhone()).getChildren().add(senderMessage(user,message,"leftMessage"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
