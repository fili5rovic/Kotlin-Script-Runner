package fili5rovic.scriptexecutor.window;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class WindowManager {
    private final Map<String, Window> windows = new HashMap<>();

    public void registerWindow(Window window) {
        windows.put(window.getWindowId(), window);
    }

    public Window getWindow(String windowId) {
        return windows.get(windowId);
    }

    public <T extends Window> T getWindow(Class<T> windowClass) {
        for (Window window : windows.values()) {
            if (windowClass.isInstance(window)) {
                return windowClass.cast(window);
            }
        }
        return null;
    }

    public void showWindow(String windowId) {
        Window window = windows.get(windowId);
        if (window != null && window.getStage() != null) {
            window.getStage().show();
        }
    }

    public <T extends Window> void showWindow(Class<T> windowClass) {
        T window = getWindow(windowClass);
        if (window != null && window.getStage() != null) {
            window.getStage().show();
        }
    }

    public void hideWindow(String windowId) {
        Window window = windows.get(windowId);
        if (window != null && window.getStage() != null) {
            window.getStage().hide();
        }
    }

    public <T extends Window> void hideWindow(Class<T> windowClass) {
        T window = getWindow(windowClass);
        if (window != null && window.getStage() != null) {
            window.getStage().hide();
        }
    }

    public void initWindow(String windowId, Stage stage) {
        Window window = windows.get(windowId);
        if (window != null) {
            window.init(stage);
        }
    }

    public <T extends Window> void initWindow(Class<T> windowClass, Stage stage) {
        T window = getWindow(windowClass);
        if (window != null) {
            window.init(stage);
        }
    }
}