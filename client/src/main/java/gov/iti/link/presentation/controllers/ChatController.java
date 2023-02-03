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
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.sql.rowset.serial.SerialBlob;

import com.mysql.cj.jdbc.Blob;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ChatController implements Initializable {

    @FXML
    private Button ADD_NEW_BTN;

    @FXML
    private AnchorPane MAIN_FRM;

    @FXML
    private ScrollPane SCROLL_BAR;

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
    private ImageView imgView;

    private ServiceManager serviceManager;
    private UserService userService;

    ObservableList<Parent> friendsList = FXCollections.observableArrayList();
    //ObservableList<UserDTO> users = FXCollections.observableArrayList();

    public ChatController() {
        serviceManager = ServiceManager.getInstance();
        userService = serviceManager.getUserService();
    }

    @FXML
    void sendMessage(ActionEvent event) {
    }

    @FXML
    void showNewDialog(ActionEvent event) {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            // byte byte_string[] = LoginController.user.getPicture().getBytes();
            // Path path = Paths.get("...");
            // byte[] bytes = Files.readAllBytes(path);
            // byte[] img =
            // Base64.getDecoder().decode(LoginController.user.getPicture().getBytes());
            // InputStream in = Base64.getDecoder().wrap(Files.newInputStream(path));

            // Image image = new Image(img);

            // BASE64Decoder base64Decoder = new BASE64Decoder();
            // System.out.println(LoginController.user.getPicture().getBytes());
            // ByteArrayInputStream inputStream = new
            // ByteArrayInputStream(Base64.getDecoder().decode(
            // LoginController.user.getPicture().getBytes()));
            // Image img = new Image(inputStream);

            // imgView.setImage(img);
            // Blob imageBlob = new Blob(byte_string);
            // InputStream binaryStream = imageBlob.getBinaryStream(0, imageBlob.length());
            // img.setImage(new Image(binaryStream));
            Vector<UserDTO> allUsers = userService.getAllUsers();
            for (UserDTO userDTO : allUsers) {
                addCardinListView(userDTO.getPicture(), userDTO.getName(), userDTO.getPhone(), false);
            }

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // catch (SQLException e) {
        // throw new RuntimeException(e);
        // }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    void addCardinListView(String imageUrl, String name, String phone, Boolean isActive) {
        String pageName = "lblcontact";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(String.format("/views/%s.fxml", pageName)));
        Parent label;
        try {
            label = fxmlLoader.load();
            LabelContactController labelContactController = fxmlLoader.getController();
            labelContactController.setName(name);
            labelContactController.setImage(imageUrl);
            labelContactController.setPhone(phone);
            labelContactController.setStatus(isActive);
            friendsList.add(label);
            lstFriend.setItems(friendsList);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    void changeOnFriendState() {
        
        
    lstFriend.refresh();
    }

}
