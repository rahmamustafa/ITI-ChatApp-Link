package gov.iti.link.presentation.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;

import com.mysql.cj.jdbc.Blob;

import gov.iti.link.business.DTOs.ContactDto;
import gov.iti.link.business.DTOs.GroupDto;
import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ClientServices;
import gov.iti.link.business.services.ClientServicesImp;
import gov.iti.link.business.services.InvitationsState;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.StateManager;
import gov.iti.link.business.services.UserAuth;
import gov.iti.link.business.services.UserService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;

import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

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
    private Button btnFile;

    @FXML
    private ListView<Pane> lstFriend;

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
    private Label lblContactChatName;

    @FXML
    private Circle circleContactChat;

    @FXML
    private VBox vboxChatFriend;
    @FXML
    private GridPane logoPane;

    @FXML
    Circle circleUserImage;

    @FXML
    private Label lblInvitesNotifications;
    @FXML
    private ComboBox<String> StatusCombo;

    private ServiceManager serviceManager;
    private UserService userService;
    private StateManager stateManager;
    private StageManager stageManager;

    Vector<ContactDto> allContacts;
    Vector<GroupDto> allGroups;
    IntegerBinding noOfInvitations;
    BooleanBinding hasInvitations;

    Vector<String> toPhones = new Vector<>();
    FileControllerGroup fileControllerGroup;
    FileControllerSingle fileControllerSingle;
    Map<String, VBox> chatVBoxs = new HashMap<>();
    Map<String, LabelContactController> contactLabels = new HashMap<>();
    Map<Integer, LabelGroupController> groupLabels = new HashMap<>();
    String clickedContact;

    ObservableList<Pane> friendsList = FXCollections.observableArrayList();
    ObservableList<Pane> groupList = FXCollections.observableArrayList();
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");

    ClientServices clientServices;
    private double xOffset = 0;
    private double yOffset = 0;

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
        try {
            toPhones.clear();
            String message = txtMessage.getText().trim();

            if (clickedContact.startsWith("01")) {
                userService.sendMessage(stateManager.getUser().getPhone(), message, clickedContact);
                chatVBoxs.get(clickedContact).getChildren()
                        .add(senderMessage(stateManager.getUser(), message, "rightMessageSingle"));

                contactLabels.get(clickedContact).setPhone(message);
                sendUserTopList(clickedContact);
            } else {
                toPhones = userService.getAllGroupMembers(Integer.parseInt(clickedContact));
                userService.sendMessageToGroup(stateManager.getUser().getPhone(), Integer.parseInt(clickedContact),
                        message, toPhones);
                chatVBoxs.get(clickedContact).getChildren()
                        .add(senderMessageGroup(stateManager.getUser(), message, "rightMessageGroup"));
                groupLabels.get(Integer.parseInt(clickedContact)).setLastMessage(message);
                sendGroupTopList(Integer.parseInt(clickedContact));
            }

            txtMessage.setText("");

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Node senderMessage(UserDTO userDTO, String message, String type) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(String.format("/views/components/%s.fxml", type)));
        VBox node = loader.load();
        if (type.startsWith("rightMessage"))
            node.setAlignment(Pos.TOP_RIGHT);
        else
            node.setAlignment(Pos.TOP_LEFT);
        MessageControllerSingle messageController = loader.getController();
        messageController.setMessage(message);
        messageController.setTime(simpleDateFormat.format(new Date()));
        return node;
    }

    private Node senderMessageGroup(UserDTO userDTO, String message, String type) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(String.format("/views/components/%s.fxml", type)));
        VBox node = loader.load();
        if (type.startsWith("rightMessage"))
            node.setAlignment(Pos.TOP_RIGHT);
        else
            node.setAlignment(Pos.TOP_LEFT);
        MessageControllerGroup messageController = loader.getController();
        messageController.setImage(userDTO.getPicture());
        messageController.setMessage(message);
        messageController.setName(userDTO.getName());
        messageController.setTime(simpleDateFormat.format(new Date()));
        return node;
    }

    @FXML
    void sendFile(ActionEvent event) {
        toPhones.clear();
        // toPhones.add(clickedContact);

        FileChooser fileChooser = new FileChooser();
        // FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("*.*");
        // fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(StageManager.getInstance().getCurrentStage());
        if (file == null) {
            return;
        }
        byte[] filebytes = new byte[(int) file.length()];

        try {
            FileInputStream in = new FileInputStream(file);
            filebytes = Files.readAllBytes(Paths.get(file.toURI()));
            System.out.println("Bytes len " + filebytes.length);
            in.read(filebytes, 0, filebytes.length);
            System.out.println("Try to send file : " + file.getName());
            if (clickedContact.startsWith("01")) {
                userService.sendFile(stateManager.getUser().getPhone(), filebytes, file.getName(), (int) file.length(),
                        clickedContact);
                chatVBoxs.get(clickedContact).getChildren()
                        .add(senderFile(stateManager.getUser(), "rightMessageFileSingle"));
            } else {
                toPhones = userService.getAllGroupMembers(Integer.parseInt(clickedContact));
                userService.sendFileToGroup(stateManager.getUser().getPhone(), Integer.parseInt(clickedContact),
                        filebytes, file.getName(), (int) file.length(),
                        toPhones);
                chatVBoxs.get(clickedContact).getChildren()
                        .add(senderFileGroup(stateManager.getUser(), "rightMessageFileGroup"));
                in.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Node senderFile(UserDTO userDTO, String type) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(String.format("/views/components/%s.fxml", type)));
        VBox node = loader.load();
        if (type.equals("rightMessageFileSingle"))
            node.setAlignment(Pos.TOP_RIGHT);
        else
            node.setAlignment(Pos.TOP_LEFT);

        fileControllerSingle = loader.getController();
        // isClicked = fileController.isCheck();
        fileControllerSingle.setTime(simpleDateFormat.format(new Date()));
        return node;
    }

    private Node senderFileGroup(UserDTO userDTO, String type) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(String.format("/views/components/%s.fxml", type)));
        VBox node = loader.load();
        if (type.equals("rightMessageFileGroup"))
            node.setAlignment(Pos.TOP_RIGHT);
        else
            node.setAlignment(Pos.TOP_LEFT);

        fileControllerGroup = loader.getController();
        fileControllerGroup.setImage(userDTO.getPicture());
        // isClicked = fileController.isCheck();
        fileControllerGroup.setName(userDTO.getName());
        fileControllerGroup.setTime(simpleDateFormat.format(new Date()));
        return node;
    }

    public void recieveFile(String file, byte[] data, UserDTO user) {
        try {
            chatVBoxs.get(user.getPhone()).getChildren().add(senderFile(user, "leftMessageFileSingle"));
            contactLabels.get(user.getPhone()).setPhone("File Recieved");
            contactLabels.get(user.getPhone()).setSeenLastMessage(false);
            sendUserTopList(user.getPhone());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fileControllerSingle.ShowConfirmation();

        File filePath = new File(file);
        if (fileControllerSingle.isCheck()) {
            byte[] mydata = data;

            // mydata = new byte[(int) filePath.length()];
            FileInputStream in;
            try {

                // mydata = Files.readAllBytes(Paths.get(file));
                in = new FileInputStream(filePath);
                try {
                    in.read(mydata, 0, mydata.length);
                } catch (IOException e) {

                    e.printStackTrace();

                }
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
            System.out.println("Downloading");
            try {
                FileOutputStream out = new FileOutputStream(new File(file));
                out.write(mydata);
                out.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            if (filePath.delete())
                System.out.println("Cannot download file");
        }
    }

    public void recieveFileFromGroup(String file, int groupId, byte[] data, UserDTO user) {

        try {
            chatVBoxs.get(Integer.toString(groupId)).getChildren().add(senderFileGroup(user, "leftMessageFileGroup"));
            groupLabels.get(groupId).setLastMessage("File Recieved");
            groupLabels.get(groupId).setSeenLastMessage(false);
            sendGroupTopList(groupId);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fileControllerGroup.ShowConfirmation();

        File filePath = new File(file);
        if (fileControllerGroup.isCheck()) {
            byte[] mydata = data;

            // mydata = new byte[(int) filePath.length()];
            FileInputStream in;
            try {

                // mydata = Files.readAllBytes(Paths.get(file));
                in = new FileInputStream(filePath);
                try {
                    in.read(mydata, 0, mydata.length);
                } catch (IOException e) {

                    e.printStackTrace();

                }
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
            System.out.println("Downloading");
            try {
                FileOutputStream out = new FileOutputStream(new File(file));
                out.write(mydata);
                out.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            if (filePath.delete())
                System.out.println("Cannot download file");
        }
    }

    @FXML
    void onClickFriend(MouseEvent event) {
        System.out.println("clicked");
        try {
            byte[] contactImgArr = null;
            clickedContact = lstFriend.getSelectionModel().getSelectedItem().getId();
            if (clickedContact != null) {
                logoPane.setVisible(false);
                btnSend.setVisible(true);
                btnFile.setVisible(true);
                TITLE_FINAL_CONTAINER.setVisible(true);
                txtMessage.setVisible(true);
            }
            System.out.println(clickedContact);
            if (clickedContact.startsWith("01")) {
                lblContactChat.setText(allContacts.stream()
                        .filter((contact) -> contact.getPhoneNumber().equals(clickedContact))
                        .map(cont -> cont.getName()).collect(Collectors.toList()).get(0));

                lblContactChatName.setText(allContacts.stream()
                        .filter((contact) -> contact.getPhoneNumber().equals(clickedContact))
                        .map(cont -> cont.getPhoneNumber()).collect(Collectors.toList()).get(0));
                contactImgArr = allContacts.stream()
                        .filter((contact) -> contact.getPhoneNumber().equals(clickedContact))
                        .map(cont -> cont.getImage()).collect(Collectors.toList()).get(0);
                contactLabels.get(clickedContact).setSeenLastMessage(true);
            } else {
                lblContactChat.setText(allGroups.stream()
                        .filter((group) -> group.getGroupId() == Integer.valueOf(clickedContact))
                        .map(grop -> grop.getGroupName()).collect(Collectors.toList()).get(0));
                lblContactChatName.setText("");
                contactImgArr = allGroups.stream()
                        .filter((group) -> group.getGroupId() == Integer.valueOf(clickedContact))
                        .map(cont -> cont.getPicture()).collect(Collectors.toList()).get(0);
                groupLabels.get(Integer.parseInt(clickedContact)).setSeenLastMessage(true);

            }
            Image contactImage = new Image(new ByteArrayInputStream(contactImgArr));
            circleContactChat.setFill(new ImagePattern(contactImage));
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
        UserAuth.logOut();
        stateManager.deleteUser();
        stageManager.deleteView("home");
        stageManager.switchToLogin();

    }

    @FXML
    void onInvitesClick() {
        System.out.println("Show invites");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/invites-list.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<Boolean> dialog = new Dialog<>();
            InviteListController controller = fxmlLoader.getController();
            makeDialogDraggable(dialogPane, dialog);
            controller.setDialog(dialog);
            dialog.setDialogPane(dialogPane);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onAddContactClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/add-contact.fxml"));
            DialogPane addDialogPane = fxmlLoader.load();
            Dialog<Boolean> dialog = new Dialog<>();
            AddContactController controller = fxmlLoader.getController();
            makeDialogDraggable(addDialogPane, dialog);
            controller.setDialog(dialog);
            dialog.setDialogPane(addDialogPane);
            // dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            lstFriend.setItems(friendsList);
            byte[] imgb = stateManager.getUser().getPicture();
            InputStream imgStream = new ByteArrayInputStream(imgb);
            Image img = new Image(imgStream);
            circleUserImage.setFill(new ImagePattern(img));
            StatusCombo.getItems().clear();

            StatusCombo.getItems().addAll("Available", "Busy", "Away");
            StatusCombo.getSelectionModel().select("Available");
            // System.out.println();
            // String imgStr = stateManager.getUser().getPicture();
            // InputStream stream = new
            // ByteArrayInputStream(imgStr.getBytes(StandardCharsets.UTF_8));
            // Image img = new Image(stream);
            // System.out.println(img);
            // lblInvitesNotifications.setText(String.valueOf(stateManager.getUser().getInvitations().size()));
            lblInvitesNotifications.visibleProperty().bind(hasInvitations);
            lblInvitesNotifications.textProperty().bind(noOfInvitations.asString());
            lblUserName.setText(stateManager.getUser().getName());

            clientServices = new ClientServicesImp(this);
            userService.userLoggedIn(clientServices, stateManager.getUser());
            fileControllerGroup = new FileControllerGroup(this);
            fileControllerSingle = new FileControllerSingle(this);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            StageManager.getInstance().switchToServerDown();
            e.printStackTrace();
        }

        try {
            allContacts = userService.getAllContacts(stateManager.getUser().getPhone());
            allGroups = userService.getAllGroups(stateManager.getUser().getPhone());
            createDatainListView(allContacts, allGroups);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void createDatainListView(Vector<ContactDto> allContacts, Vector<GroupDto> allGroups) {
        friendsList.clear();
        for (ContactDto contactDto : allContacts) {
            addCardinListView(contactDto, friendsList.size());
            chatVBoxs.put(contactDto.getPhoneNumber(), new VBox());
        }
        for (GroupDto groupDto : allGroups) {
            addGroupinListView(groupDto, groupList.size());
            chatVBoxs.put(Integer.toString(groupDto.getGroupId()), new VBox());
        }
    }

    void addCardinListView(ContactDto contactDto, int index) {
        String pageName = "lblcontact";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(String.format("/views/%s.fxml", pageName)));
        Pane label;
        try {
            label = fxmlLoader.load();
            label.setId(contactDto.getPhoneNumber());
            LabelContactController labelContactController = fxmlLoader.getController();
            labelContactController.setName(contactDto.getName());
            labelContactController.setImage(contactDto.getImage());
            labelContactController.setPhone("");
            labelContactController.setStatus(contactDto.isActive());
            contactLabels.put(contactDto.getPhoneNumber(), labelContactController);
            friendsList.add(index, label);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void addGroupinListView(GroupDto groupDto, int index) {
        String pageName = "lblGroup";
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(String.format("/views/components/%s.fxml", pageName)));
        Pane label;
        try {
            label = fxmlLoader.load();
            label.setId(Integer.toString(groupDto.getGroupId()));
            LabelGroupController labelGroupController = fxmlLoader.getController();
            labelGroupController.setGroupDto(groupDto);
            if (!groupDto.getAdminPhone().equals(StateManager.getInstance().getUser().getPhone()))
                labelGroupController.setAddMemberDisable();
            labelGroupController.setLastMessage("");
            groupLabels.put(groupDto.getGroupId(), labelGroupController);
            groupList.add(index, label);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addNewGroup(GroupDto groupDto) {
        allGroups.add(groupDto);
        addGroupinListView(groupDto, groupList.size());
        chatVBoxs.put(Integer.toString(groupDto.getGroupId()), new VBox());
        if (!stateManager.getUser().getPhone().equals(groupDto.getAdminPhone()))
            groupLabels.get(groupDto.getGroupId())
                    .setLastMessage("you have joined the group " + groupDto.getGroupName());
        else
            groupLabels.get(groupDto.getGroupId()).setLastMessage("you created group " + groupDto.getGroupName());

        sendGroupTopList(groupDto.getGroupId());

    }

    public void addNewContact(String phoneNumber) {
        try {
            ContactDto newContact = new ContactDto(userService.findByPhone(phoneNumber));
            allContacts.add(newContact);
            addCardinListView(newContact, friendsList.size());
            chatVBoxs.put(newContact.getPhoneNumber(), new VBox());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void changeOnFriendState(ContactDto contactDto) {
        contactLabels.get(contactDto.getPhoneNumber()).setStatus(contactDto.isActive());
    }
    public void changeMyFriendState(ContactDto contactDto ,String status) {
        contactLabels.get(contactDto.getPhoneNumber()).setDifferentStatus(status);
    }

    public void changeOnGroupState(GroupDto groupDto, String newMemberAdded) {
        try {
            if (!stateManager.getUser().getPhone().equals(groupDto.getAdminPhone()))
                groupLabels.get(groupDto.getGroupId())
                        .setLastMessage(userService.findByPhone(newMemberAdded).getName() + " has joined");

            else
                groupLabels.get(groupDto.getGroupId())
                        .setLastMessage("you added " + userService.findByPhone(newMemberAdded).getName());

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        groupLabels.get(groupDto.getGroupId()).setGroupDto(groupDto);
        sendGroupTopList(groupDto.getGroupId());

    }

    public void recieveMessage(String message, UserDTO user) {
        try {
            chatVBoxs.get(user.getPhone()).getChildren().add(senderMessage(user, message, "leftMessageSingle"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        contactLabels.get(user.getPhone()).setPhone(message);
        contactLabels.get(user.getPhone()).setSeenLastMessage(false);
        sendUserTopList(user.getPhone());

    }

    void sendUserTopList(String userId) {
        for (int i = 0; i < friendsList.size(); i++)
            if (friendsList.get(i).getId().equals(userId)) {
                Pane temp = friendsList.get(i);
                friendsList.remove(friendsList.get(i));
                friendsList.add(0, temp);
            }
    }

    private void sendGroupTopList(int groupId) {
        for (int i = 0; i < groupList.size(); i++)
            if (groupList.get(i).getId().equals(Integer.toString(groupId))) {
                Pane temp = groupList.get(i);
                groupList.remove(groupList.get(i));
                groupList.add(0, temp);
            }
    }

    public void recieveAnnounc(String announcement) {

        System.out.println(announcement);

    }

    public void recieveMessageFromGroup(String message, int groupId, UserDTO user) {
        try {
            chatVBoxs.get(Integer.toString(groupId)).getChildren()
                    .add(senderMessageGroup(user, message, "leftMessageGroup"));
            groupLabels.get(groupId).setLastMessage(message);
            groupLabels.get(groupId).setSeenLastMessage(false);
            sendGroupTopList(groupId);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void onCreateGroup(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/create-group.fxml"));
            DialogPane addDialogPane = fxmlLoader.load();
            CreateGroupController createGroupController = fxmlLoader.getController();
            Dialog<Boolean> dialog = new Dialog<>();
            createGroupController.setChatController(this);
            makeDialogDraggable(addDialogPane, dialog);
            createGroupController.setDialog(dialog);
            dialog.setDialogPane(addDialogPane);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onOpenGroups() {
        lstFriend.setItems(groupList);
    }

    @FXML
    void onOpenContacts() {
        lstFriend.setItems(friendsList);
    }

    private void makeDialogDraggable(Pane pane, Dialog dialog) {
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.setX(event.getScreenX() - xOffset);
                dialog.setY(event.getScreenY() - yOffset);
            }
        });
    }

    @FXML
    void ChanagingStatus(ActionEvent event) throws RemoteException {
        StatusCombo.getSelectionModel().getSelectedItem();
        System.out.println(StatusCombo.getSelectionModel().getSelectedItem());
        userService.changingUserStatus(clientServices, stateManager.getUser(),
        StatusCombo.getSelectionModel().getSelectedItem());
    }

}
