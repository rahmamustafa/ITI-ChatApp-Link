package gov.iti.link.presentation;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.Vector;

import gov.iti.link.business.services.ServerManager;
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
    Dialog<Boolean> dialog;

    @FXML
    void sendbtnAction(ActionEvent event) throws RemoteException {
       
        if (!texmesage.getText().equals("")){
           ServerManager.getInstance().getUserSeviceImp().sendAnnounce(texmesage.getText().toString());
           System.out.println(texmesage.getText().toString());
           texmesage.clear();
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }

    public void setDialog(Dialog<Boolean> dialog){
        this.dialog = dialog ;
    }

    @FXML
    void onClose(){
        this.dialog.setResult(Boolean.TRUE);
        dialog.close();
    }
    
}
