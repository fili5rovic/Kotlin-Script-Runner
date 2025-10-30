package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.FileOpenRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.NewFileRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.SaveFileRequestEvent;
import fili5rovic.scriptexecutor.util.FileHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

import java.awt.*;
import java.io.File;
import java.net.URI;

public class MenuItemManager implements IManager {
    private final MenuItem newScript;
    private final MenuItem openFile;
    private final MenuItem saveFile;
    private final MenuItem thisProject;

    public MenuItemManager(MenuItem newScript, MenuItem openFile, MenuItem saveFile, MenuItem thisProject) {
        this.newScript = newScript;
        this.openFile = openFile;
        this.saveFile = saveFile;
        this.thisProject = thisProject;
    }

    private void handleThisProject(ActionEvent e) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/fili5rovic/Kotlin-Script-Runner"));
            } catch (Exception ex) {
                System.err.println("Couldn't open browser: " + ex.getMessage());
            }
        }
    }

    @Override
    public void initialize() {
        setupListeners();
    }

    private void setupListeners() {
        newScript.setOnAction(this::handleNewScript);
        openFile.setOnAction(this::handleOpenFile);
        saveFile.setOnAction(this::handleSaveFile);
        thisProject.setOnAction(this::handleThisProject);
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
        EventBus.instance().publish(new NewFileRequestEvent());
    }

}
