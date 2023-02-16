package gov.iti.link.presentation;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ServerManager;
import gov.iti.link.business.services.UserServiceImp;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class mangeServiceController implements Initializable {
    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);
    private UserServiceImp users;
    ServerManager serverManager = ServerManager.getInstance();

    // @FXML
    // private BarChart<?, ?> UserGender;

    // @FXML
    // private BarChart<?, ?> onLinUser;

    @FXML
    private Button Gunderbtn;

    @FXML
    private Button conutrybtn;

    @FXML
    private Button onlinebtn;

    @FXML
    private PieChart piechart;

    @FXML
    private Button switchBtn;
    @FXML
    private Label caption;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void GunderbtnAction(ActionEvent event) {
        try {
            users = ServerManager.getInstance().getUserSeviceImp();
            Vector<UserDTO> userdto = new Vector<>(users.getAllUsers());
            ArrayList<String> gunder = new ArrayList<>();
            caption.setText("");
            final int allgunderSise=userdto.size();
            for (int i = 0; i < userdto.size(); i++) {
                gunder.add(userdto.elementAt(i).getGender());
            }
            Map<String, Long> gendermap = gunder.stream() // Stream<String>
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            // gendermap.forEach((t, u) -> System.out.println(t +" :"+u));
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            gendermap.forEach((t, u) -> pieChartData.add(new PieChart.Data(t, u)));
            piechart.getData().clear();
            piechart.getData().addAll(pieChartData);
            piechart.setTitle("User Gunder");
            caption.setTextFill(Color.BLACK);
            caption.setStyle("-fx-font: 24 arial;");

            for (final PieChart.Data data : piechart.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        caption.setTranslateX(e.getSceneX()-caption.getLayoutX());
                        caption.setTranslateY(e.getSceneY()-caption.getLayoutY());
                        double d = data.getPieValue();
                        int x = (int) d;
                        caption.setText(String.valueOf(x));   }
                });
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void countrybtnAction(ActionEvent event) {
        try {
            users = ServerManager.getInstance().getUserSeviceImp();
            Vector<UserDTO> userdto = new Vector<>(users.getAllUsers());
            ArrayList<String> country = new ArrayList<>();
            caption.setText("");
            final int allcontrysize=userdto.size();
            for (int i = 0; i < userdto.size(); i++) {
                country.add(userdto.elementAt(i).getCountry());
            }
            Map<String, Long> gendermap = country.stream() // Stream<String>
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            // gendermap.forEach((t, u) -> System.out.println(t +" :"+u));
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            gendermap.forEach((t, u) -> pieChartData.add(new PieChart.Data(t, u)));
            piechart.getData().clear();
            piechart.getData().addAll(pieChartData);
            piechart.setTitle("User country");
            caption.setTextFill(Color.BLACK);
            caption.setStyle("-fx-font: 24 arial;");

            for (final PieChart.Data data : piechart.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        caption.setTranslateX(e.getSceneX()-caption.getLayoutX());
                        caption.setTranslateY(e.getSceneY()-caption.getLayoutY());
                        double d = data.getPieValue();
                        int x = (int) d;
                        caption.setText(String.valueOf(x));   }
                });
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onlinebtnAction(ActionEvent event) {
        try {
           final int allUser ;
            int onlinuser = 0;
            users = ServerManager.getInstance().getUserSeviceImp();
            if (users.getAllUsers() != null) {
                allUser = users.getAllUsers().size();
            }
            else allUser=0;
            if (!users.allOnlineUser.isEmpty()) {
                onlinuser = users.allOnlineUser.size();
            }
            caption.setText("");
            System.out.println("All:" + allUser);
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Online ", onlinuser), new PieChart.Data("OffLine ", allUser - onlinuser));

            piechart.getData().clear();
            piechart.getData().addAll(pieChartData);
            piechart.setTitle("Online User");
            caption.setTextFill(Color.BLACK);
            caption.setStyle("-fx-font: 24 arial;");

            for (final PieChart.Data data : piechart.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        
                        caption.setTranslateX(e.getSceneX()-caption.getLayoutX());
                        caption.setTranslateY(e.getSceneY()-caption.getLayoutY());
                        double d = data.getPieValue();
                        int x = (int) d;
                        caption.setText(String.valueOf(x));
                    }
                });
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void changeState(ActionEvent event) throws IOException {

        switchedOn.set(!switchedOn.get());

        if (switchedOn.get()) {
            serverManager.bindUserService();
            switchBtn.setText("Server is ON");
            switchBtn.setStyle("-fx-background-color: green;-fx-text-fill:white;-fx-cursor:hand;");
            switchBtn.setContentDisplay(ContentDisplay.RIGHT);
        } else {
            serverManager.unbindUserService();
            switchBtn.setText("Server is OFF");
            switchBtn.setStyle("-fx-background-color: red;-fx-text-fill:black;-fx-cursor:hand;");
            switchBtn.setContentDisplay(ContentDisplay.LEFT);
        }
    }


    @FXML
    void announcebtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Announcement.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            AnnouncementController controller = fxmlLoader.getController();
            Dialog<Boolean> dialog = new Dialog<>();
            controller.setDialog(dialog);
            makeDialogDraggable(dialogPane, dialog);
            dialog.setDialogPane(dialogPane);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
      
            switchBtn.setText("Server is ON");
            switchBtn.setStyle("-fx-background-color: green;-fx-text-fill:white;");
            switchBtn.setContentDisplay(ContentDisplay.RIGHT);
            GunderbtnAction(new ActionEvent());
    }

    public SimpleBooleanProperty switchOnProperty() {
        return switchedOn;
    }

    private void makeDialogDraggable(Pane pane, Dialog dialog) {
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.setX(event.getScreenX() - xOffset);
                dialog.setY(event.getScreenY() - yOffset);
            }
        });
    }
}
