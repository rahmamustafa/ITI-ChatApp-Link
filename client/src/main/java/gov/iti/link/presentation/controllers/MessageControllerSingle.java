package gov.iti.link.presentation.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;

public class MessageControllerSingle {
   
    @FXML
    private Label lblMessage;

    @FXML
    WebView lblMessage1;
    
    @FXML
    private Label lblTime;

    public String getMessage() {
        return lblMessage.getText();
    }

    public void setMessage(String message) {
        //this.lblMessage.setText(message);
        lblMessage1.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/msg.css").toString());
        lblMessage1.getEngine().loadContent(message);

        
    }


  
    public String getTime() {
        return lblTime.getText();
    }

    public void setTime(String lblTime) {
        this.lblTime.setText(lblTime);
    }
   
    

}
