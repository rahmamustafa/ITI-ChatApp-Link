package gov.iti.link.presentation.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class prfController implements Initializable{
   

    @FXML
    VBox RightContent;
    @FXML
    Pane paneContent;

    @FXML
    private Label cpss;

    @FXML
    private Pane leftPane;



    @FXML
    private Label prf;

    @FXML
    private ImageView prfImage;

    @FXML
    private Label upd;

    @FXML
    private Label usrname;


    @FXML
    private void updclkaction(){
        try {
            Parent prof=FXMLLoader.load(getClass().getResource("/views/prfUpdat.fxml"));
            paneContent.getChildren().clear();
            paneContent.getChildren().addAll(prof);
    
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
    @FXML
    private void prfclkaction(){
        try {
            Parent prof=FXMLLoader.load(getClass().getResource("/views/prfinfo.fxml"));
            paneContent.getChildren().clear();
            paneContent.getChildren().add(prof);
    
        } catch (IOException e) {
            
            e.printStackTrace();
        }
            
    }
    @FXML
    private void chgpassclkaction(){
        try {
            Parent prof=FXMLLoader.load(getClass().getResource("/views/chgpass.fxml"));
            paneContent.getChildren().clear();
            paneContent.getChildren().addAll(prof);
    
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
     try {
        Parent prof=FXMLLoader.load(getClass().getResource("/views/prfinfo.fxml"));
        paneContent.getChildren().clear();
        paneContent.getChildren().addAll(prof);

    } catch (IOException e) {
        
        e.printStackTrace();
    }
        
        
    }
    
}
