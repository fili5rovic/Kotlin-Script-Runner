package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.FileOpenedEvent;
import fili5rovic.scriptexecutor.util.FileHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

import java.io.File;

public class MenuItemManager implements IManager {
    private final MenuItem newScript;
    private final MenuItem openFile;

    public MenuItemManager(MenuItem newScript, MenuItem openFile) {
        this.newScript = newScript;
        this.openFile = openFile;
    }

    @Override
    public void initialize() {
        setupListeners();
    }

    private void setupListeners() {
        newScript.setOnAction(this::handleNewScript);
        openFile.setOnAction(this::handleOpenScript);
    }

    private void handleOpenScript(ActionEvent e) {
        File file = FileHelper.openFileChooser();
        if (file == null)
            return;

        String content = FileHelper.readFromFile(file.getAbsolutePath());
        EventBus.instance().publish(new FileOpenedEvent(file, content));
    }

    private void handleNewScript(ActionEvent e) {
        System.out.println("New loaded");
    }

}
