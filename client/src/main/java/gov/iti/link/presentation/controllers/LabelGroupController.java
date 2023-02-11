package gov.iti.link.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class LabelGroupController {

  
    @FXML
    private Circle circlePic;

    @FXML
    private ImageView imgAddMember;

    @FXML
    private Label lablGroupNumber;

    @FXML
    private Label lblGroupName;

    @FXML
    private Text txtLastMessage;

    @FXML
    void onAddGroupMember(MouseEvent mouseEvent) {

    }
    public LabelGroupController(){

    }
    
    public LabelGroupController(String groupName) {
       setGroupName(groupName);
    }

    public void setGroupName(String name){
        lblGroupName.setText(name);
        
    }
    
    public void setLastMessage(String message){
      txtLastMessage.setText(message);
    }
    public void setGroupSize(int number){
        lablGroupNumber.setText(Integer.toString(number));
      }

    
    public String getName(){
        return(lblGroupName.getText());
        
    }
   
    public String getGroupSize(){
        return lablGroupNumber.getText();
        
    }
  
   



}
