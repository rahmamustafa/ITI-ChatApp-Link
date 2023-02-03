package gov.iti.link.presentation.controllers;

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
    
    public LabelContactController(String imageUrl, String lblText,boolean isActive) {
       setImage(imageUrl);
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
  
    public void setImage(String imageUrl){
        // Image image = new Image(imageUrl);
        // circlePic.setFill(new ImagePattern(image));
        System.out.println("ads");
    }



}
