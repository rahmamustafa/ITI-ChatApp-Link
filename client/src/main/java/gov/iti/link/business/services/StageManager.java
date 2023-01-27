package gov.iti.link.business.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {
    private static final StageManager instance = new StageManager();
    private final Map<String, Scene> scenes = new HashMap<>();
    private final int SCENE_WIDTH = 800;
    private final int SCENE_HEIGHT = 560;
    private Stage primaryStage;

    public static StageManager getInstance() {
        return instance;
    }

    private StageManager() {

    }

    public void initStage(Stage stage) {
        if (primaryStage != null) {
            throw new IllegalArgumentException("The Stage has already been initialized");
        }
        primaryStage = stage;
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
                Scene scene = new Scene(fxmlLoader.load(), SCENE_WIDTH, SCENE_HEIGHT);
                scenes.put(name, scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        primaryStage.setScene(scenes.get(name));

    }

}
