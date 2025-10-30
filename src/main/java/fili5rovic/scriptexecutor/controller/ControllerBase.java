package fili5rovic.scriptexecutor.controller;

import fili5rovic.scriptexecutor.manager.IManager;
import javafx.stage.Stage;

import java.util.List;

public abstract class ControllerBase {

    public final void setup(Stage stage) {
        List<IManager> managers = createManagers(stage);
        for (IManager manager : managers) {
            manager.initialize();
        }
    }

    protected abstract List<IManager> createManagers(Stage stage);
}
