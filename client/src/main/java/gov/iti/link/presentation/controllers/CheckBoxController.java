package gov.iti.link.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class CheckBoxController {

    @FXML
    private CheckBox chboxName;

    @FXML
    private Circle circleImage;

    public String getChboxName() {
        return chboxName.getText();
    }

    public void setChboxName(String chboxName) {
        this.chboxName.setText(chboxName);
    }
    public void setCircleImage(Byte[] image) {
        // Image image = new Image();
        // this.circleImage = circleImage;
    }
    

}
