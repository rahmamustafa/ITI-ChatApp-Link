package gov.iti.link.presentation.controllers;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class FileController {

    @FXML
    private Circle circleImage;

    @FXML
    private Label lblMessage;

    @FXML
    private Label lblName;

    @FXML
    private Label lblTime;

    private boolean check;
    ChatController chatController;
    
    public FileController(ChatController chatController) {
        this.chatController = chatController;
	}

	public void setName(String senderName) {
        this.lblName.setText(senderName);;
    }

    public String getTime() {
        return lblTime.getText();
    }

    public void setTime(String lblTime) {
        this.lblTime.setText(lblTime);
    }
    public void setImage(String imageUrl) {
       ///
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
            check = true;
            
        }
        else if (result.isPresent() &&  result.get().getText() == "Cancel") {
            check = false;
        }
        
    }

    @FXML
    void OnDownload(ActionEvent event) {

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
