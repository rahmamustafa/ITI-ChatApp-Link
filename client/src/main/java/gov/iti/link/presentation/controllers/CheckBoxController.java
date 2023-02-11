package gov.iti.link.presentation.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class CheckBoxController {

    @FXML
    private CheckBox chboxName;

    @FXML
    private Circle circleImage;

    public String getChboxName() {
        return chboxName.getText();
    }

    public CheckBoxController() {
    }

    public void setChboxName(String chboxName) {
        this.chboxName.setText(chboxName);
    }
    public void setCircleImage(byte[] image) {
        InputStream imgStream = new ByteArrayInputStream(image);
        Image img = new Image(imgStream);
        // Image image = new Image(imageUrl);
        circleImage.setFill(new ImagePattern(img));
    }
    

}
