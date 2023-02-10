package gov.iti.link.presentation.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class MessageController {
    @FXML
    private Circle circleImage;

    @FXML
    private Label lblMessage;

    @FXML
    private Label lblName;

    @FXML
    private Label lblTime;

    public String getMessage() {
        return lblMessage.getText();
    }

    public void setMessage(String message) {
        this.lblMessage.setText(message);
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
    public void setImage(byte[] image) {
       ///
       InputStream imgStream = new ByteArrayInputStream(image);
       Image img = new Image(imgStream);
       // Image img = new Image(image,false);
       circleImage.setFill(new ImagePattern(img));

    }
    

}
