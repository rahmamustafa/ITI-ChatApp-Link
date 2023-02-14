package gov.iti.link.presentation;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.Vector;

import gov.iti.link.business.services.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class AnnouncementController implements Initializable{
    @FXML
    private Button sendbtn;

    @FXML
    private TextField texmesage;

    @FXML
    void sendbtnAction(ActionEvent event) throws RemoteException {
        UserServiceImp obj=new UserServiceImp();
        String Fromphone="01000000000";
        Vector<String> toPhone=new Vector<String>();
        for(int i=0;i<obj.allOnlineUser.size();i++){
            toPhone.add(obj.allOnlineUser.get(i).getPhone());
        }
        if (!texmesage.getText().equals("")){
            obj.sendMessage(Fromphone, texmesage.getText().toString(), toPhone);
            texmesage.clear();
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }
    
}
