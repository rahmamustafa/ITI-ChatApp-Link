package gov.iti.link.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class LabelContactController {

    @FXML
    private Circle circlePic;

    @FXML
    private Label lblText;

    public LabelContactController(){

    }
    
    public LabelContactController(String imageUrl, String lblText) {
       setImage(imageUrl);
       setName(lblText);
    }

    public void setName(String name){
        lblText.setText(name);
        
    }
    public void setImage(String imageUrl){
        // Image image = new Image(imageUrl);
        // circlePic.setFill(new ImagePattern(image));
        System.out.println("ads");
    }


}
