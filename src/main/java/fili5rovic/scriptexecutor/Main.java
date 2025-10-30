package fili5rovic.scriptexecutor;

import fili5rovic.scriptexecutor.script.ScriptRunner;
import fili5rovic.scriptexecutor.window.MainWindow;
import fili5rovic.scriptexecutor.window.WindowManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
//        WindowManager windowManager = new WindowManager();
//
//        windowManager.registerWindow(new MainWindow());
//
//        windowManager.initWindow(MainWindow.class, stage);
//        windowManager.showWindow(MainWindow.class); //
        ScriptRunner.runKotlinScript("D:/test.kts");
    }

    public static void main(String[] args) {
        launch();
    }
}