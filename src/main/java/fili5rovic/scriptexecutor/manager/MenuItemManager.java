package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.FileOpenRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.FileOpenedEvent;
import fili5rovic.scriptexecutor.events.myEvents.NewFileRequest;
import fili5rovic.scriptexecutor.events.myEvents.SaveFileRequestEvent;
import fili5rovic.scriptexecutor.util.FileHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

import java.io.File;

public class MenuItemManager implements IManager {
    private final MenuItem newScript;
    private final MenuItem openFile;
    private final MenuItem saveFile;

    public MenuItemManager(MenuItem newScript, MenuItem openFile, MenuItem saveFile) {
        this.newScript = newScript;
        this.openFile = openFile;
        this.saveFile = saveFile;
    }

    @Override
    public void initialize() {
        setupListeners();
    }

    private void setupListeners() {
        newScript.setOnAction(this::handleNewScript);
        openFile.setOnAction(this::handleOpenFile);
        saveFile.setOnAction(this::handleSaveFile);
    }

    private void handleSaveFile(ActionEvent event) {
        EventBus.instance().publish(new SaveFileRequestEvent());
    }

    private void handleOpenFile(ActionEvent e) {
        File file = FileHelper.openFileChooser();
        if (file == null)
            return;

        EventBus.instance().publish(new FileOpenRequestEvent(file));
    }

    private void handleNewScript(ActionEvent e) {
        EventBus.instance().publish(new NewFileRequest());
    }

}
