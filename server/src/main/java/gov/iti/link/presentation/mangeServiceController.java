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
import javafx.stage.Stage;

public class mangeServiceController implements Initializable {
    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);
    private UserServiceImp users;

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
    void GunderbtnAction(ActionEvent event) {
        try {
            users = new UserServiceImp();
            Vector<UserDTO> userdto = new Vector<>(users.getAllUsers());
            ArrayList<String> gunder = new ArrayList<>();

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
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void countrybtnAction(ActionEvent event) {
        try {
            users = new UserServiceImp();
            Vector<UserDTO> userdto = new Vector<>(users.getAllUsers());
            ArrayList<String> country = new ArrayList<>();

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
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void onlinebtnAction(ActionEvent event) {
        try {
            users = ServerManager.getInstance().getUserSeviceImp();
            int allUser = users.getAllUsers().size();
            int onlinuser = users.allOnlineUser.size();
            System.out.println("All:" + allUser);
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Online ", onlinuser), new PieChart.Data("OffLine ", allUser - onlinuser));

            piechart.getData().clear();
            piechart.getData().addAll(pieChartData);
            piechart.setTitle("Online User");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void changeState(ActionEvent event) throws IOException {
        ServerManager serverManager = ServerManager.getInstance();

        switchedOn.set(!switchedOn.get());

        if (switchedOn.get()) {
            serverManager.bindUserService();
            switchBtn.setText("Server is ON");
            switchBtn.setStyle("-fx-background-color: green;-fx-text-fill:white;");
            switchBtn.setContentDisplay(ContentDisplay.RIGHT);
        } else {
            serverManager.unbindUserService();
            switchBtn.setText("Server is OFF");
            switchBtn.setStyle("-fx-background-color: red;-fx-text-fill:black;");
            switchBtn.setContentDisplay(ContentDisplay.LEFT);
        }
    }

    @FXML
    void announcebtnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Announcement.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub)

        // switchedOn.set(false);
    }

    public SimpleBooleanProperty switchOnProperty() {
        return switchedOn;
    }
}
