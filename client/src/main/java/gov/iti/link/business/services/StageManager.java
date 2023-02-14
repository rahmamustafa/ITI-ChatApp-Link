package gov.iti.link.business.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageManager {
    private static final StageManager instance = new StageManager();
    private final Map<String, Scene> scenes = new HashMap<>();
    private final int SCENE_WIDTH = 800;
    private final int SCENE_HEIGHT = 620;
    private Stage primaryStage;
    // private double xOffset = 0;
    // private double yOffset = 0;

    public static StageManager getInstance() {
        return instance;
    }

    private StageManager() {

    }

    public void initStage(Stage stage) {
        if (primaryStage != null) {
            throw new IllegalArgumentException("The Stage has already been initialized");
        }
        // stage.initStyle(StageStyle.TRANSPARENT);
        primaryStage = stage;
    }

    public Stage getCurrentStage() {

        return primaryStage;
    }

    public void switchToLogin() {
        String sceneName = "login";
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }

    public void switchToRegister() {
        String sceneName = "register";
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }

    public void switchToProfile() {
        String sceneName = "profile";
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }

    public void switchToHome() {
        String sceneName = "home";
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }

    public void loadView(String name) {
        if (primaryStage == null) {
            throw new RuntimeException("Stage Coordinator should be initialized with a Stage before it could be used");
        }

        if (!scenes.containsKey(name)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(String.format("/views/%s.fxml", name)));
                Parent root = fxmlLoader.load();

                // root.setOnMousePressed(new EventHandler<MouseEvent>() {
                //     @Override
                //     public void handle(MouseEvent event) {
                //         xOffset = event.getSceneX();
                //         yOffset = event.getSceneY();
                //     }
                // });

                // root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                //     @Override
                //     public void handle(MouseEvent event) {
                //         primaryStage.setX(event.getScreenX() - xOffset);
                //         primaryStage.setY(event.getScreenY() - yOffset);
                //     }
                // });
                Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
                scenes.put(name, scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        primaryStage.setScene(scenes.get(name));

    }

    public void deleteView(String name) {
        if (scenes.containsKey(name)) {
            scenes.remove(name);
        }
    }

}
