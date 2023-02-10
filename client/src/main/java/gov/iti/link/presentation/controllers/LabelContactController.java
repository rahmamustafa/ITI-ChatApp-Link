package gov.iti.link.presentation.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class LabelContactController {

    @FXML
    private Circle circlePic;

    @FXML
    private Circle circlePicStatus;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblText;

    @FXML
    private Text txtNumber;

    public LabelContactController(){

    }
    
    public LabelContactController(byte[] image, String lblText,boolean isActive) {
       setImage(image);
       setName(lblText);
    }

    public void setName(String name){
        lblText.setText(name);
        
    }
    public void setPhone(String name){
        txtNumber.setText(name);
        
    }
    public void setStatus(boolean status){
        if(status){
            lblStatus.setText("online");
            circlePicStatus.setFill(Color.GREEN);
        }
        else  {
            lblStatus.setText("ofline"); 
            circlePicStatus.setFill(Color.GRAY);
        }
        
    }

    
    public String getName(){
        return(lblText.getText());
        
    }
    public String getphone(){
        return(txtNumber.getText());
        
    }
    public String getStatus(){
        return lblStatus.getText();
        
    }
  
    public void setImage(byte[] image){
        InputStream imgStream = new ByteArrayInputStream(image);
        Image img = new Image(imgStream);
        // Image image = new Image(imageUrl);
        circlePic.setFill(new ImagePattern(img));
        // System.out.println("ads");
    }



}
