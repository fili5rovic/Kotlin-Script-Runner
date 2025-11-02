package fili5rovic.scriptexecutor.window;

public class MainWindow extends Window {

    public MainWindow() {
        super("Kotlin Executor", "main");
//        maximized = true;
    }

    @Override
    public String getWindowId() {
        return "main";
    }
}
