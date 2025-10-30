package fili5rovic.scriptexecutor;

import fili5rovic.scriptexecutor.util.FontUtil;
import fili5rovic.scriptexecutor.window.MainWindow;
import fili5rovic.scriptexecutor.window.WindowManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        FontUtil.loadFonts();
        WindowManager windowManager = new WindowManager();

        windowManager.registerWindow(new MainWindow());

        windowManager.initWindow(MainWindow.class, stage);
        windowManager.showWindow(MainWindow.class);
    }

    public static void main(String[] args) {
        launch();
    }
}