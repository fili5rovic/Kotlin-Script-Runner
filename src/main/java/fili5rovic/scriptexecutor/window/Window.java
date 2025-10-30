package fili5rovic.scriptexecutor.window;


import fili5rovic.scriptexecutor.Main;
import fili5rovic.scriptexecutor.controller.ControllerBase;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.util.Objects;

public abstract class Window {
    protected Stage stage;

    protected String title;
    protected String fxmlName;
    protected boolean maximized = false;

    protected Window(String title, String fxmlName) {
        this.title = title;
        this.fxmlName = fxmlName;
    }

    public void init(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/" + this.fxmlName + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            if (!(fxmlLoader.getController() instanceof ControllerBase baseController)) {
                System.err.println("Controller for " + title + " is not an instance of ControllerBase");
                return;
            }

            stage.setMaximized(maximized);

            try {
                scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("css/styles.css")).toExternalForm());
            } catch (Exception e) {
                System.err.println("Error loading CSS for window " + title + ": " + e.getMessage());
            }

            stage.setScene(scene);
            stage.setTitle(this.title);
            this.stage = stage;
            listeners();

            baseController.setup(stage);
        } catch (Exception e) {
            System.err.println("Error loading window " + title + ": " + e.getMessage());
        }
    }

    public void listeners() {
    }


    public abstract String getWindowId();

    public Stage getStage() {
        return stage;
    }

}
