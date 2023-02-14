package gov.iti.link.presentation.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class LabelContactController implements Initializable{

    @FXML
    private Circle circlePic;

    @FXML
    private Circle circlePicStatus;

   
   private boolean seenLastMessage=true;
   private int counterLastMessage=0; 

   @FXML
   Label lblunSeenMessages;

    @FXML
    private Label lblText;

    @FXML
    private Text txtNumber;

    public LabelContactController getLabelController(){
        return this;
    }

    public LabelContactController(){
    }
     
    public LabelContactController(byte[] image, String lblText,boolean isActive) {
       setImage(image);
       setName(lblText);
    }

    public void setName(String name){
        lblText.setText(name);
        
    }
    public void setMessageCounter(String name){
        lblunSeenMessages.setText(name);
        
    }
    public void setPhone(String name){
        txtNumber.setText(name);
        
    }
    public void setStatus(boolean status){
        if(status){
            //lblStatus.setText("online");
            circlePicStatus.setFill(Color.web("#1fff3d"));
        }
        else  {
            //lblStatus.setText("ofline"); 
            circlePicStatus.setFill(Color.GRAY);
        }
        
    }

    
    public String getName(){
        return(lblText.getText());
        
    }
    
        
    
    public void setImage(byte[] image){
        InputStream imgStream = new ByteArrayInputStream(image);
        Image img = new Image(imgStream);
        // Image image = new Image(imageUrl);
        circlePic.setFill(new ImagePattern(img));
        // System.out.println("ads");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        lblunSeenMessages.setVisible(false);
		
	}

	public boolean isSeenLastMessage() {
		return seenLastMessage;
	}

	public void setSeenLastMessage(boolean seenLastMessage) {
        this.seenLastMessage = seenLastMessage;
        if(seenLastMessage){
            lblunSeenMessages.setVisible(false);
            counterLastMessage=0;
        }
        else{
            lblunSeenMessages.setVisible(true);
            counterLastMessage++;
            lblunSeenMessages.setText(Integer.toString(counterLastMessage));
        }
	}
    


    
}
