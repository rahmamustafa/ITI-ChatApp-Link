package gov.iti.link.presentation.controllers;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;

public class FileControllerSingle {

    @FXML
    private Label lblMessage;

    @FXML
    private Label lblTime;

    private boolean check;
    ChatController chatController;
    
    

    public FileControllerSingle(ChatController chatController) {
        this.chatController = chatController;
    }

    

    public FileControllerSingle() {
    }



    public String getTime() {
        return lblTime.getText();
    }

    public void setTime(String lblTime) {
        this.lblTime.setText(lblTime);
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @FXML
    void OnClickFile(MouseEvent event) {

        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        
        ButtonType close = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your friend has sent a file", save, close);
        alert.setTitle("Save");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get().getText() == "Save") {
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION, "File Downloaded Successfully", new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
            conf.show();
            check = true;
            
        }
        else if (result.isPresent() &&  result.get().getText() == "Cancel") {
            check = false;
        }
        
    }


    public void ShowConfirmation (){
        
        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType close = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your friend has sent a file", save, close);
        alert.setTitle("Save");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get().getText() == "Save") {
            check = true;
        }
        else if (result.isPresent() && result.get().getText() == "Cancel") {
            check = false;
        }
        
    }



}
