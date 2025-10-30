package fili5rovic.scriptexecutor.manager.codeManager;

import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.FileOpenRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.NewFileRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.SaveFileRequestEvent;
import fili5rovic.scriptexecutor.fxcode.MyCodeArea;
import fili5rovic.scriptexecutor.manager.IManager;
import fili5rovic.scriptexecutor.util.FileHelper;
import fili5rovic.scriptexecutor.util.OpenFileTracker;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class CodeEventManager implements IManager {
    private final MyCodeArea myCodeArea;
    private final Stage stage;

    public CodeEventManager(Stage stage, MyCodeArea codeArea) {
        this.stage = stage;
        this.myCodeArea = codeArea;
    }

    @Override
    public void initialize() {
        EventBus.instance().register(FileOpenRequestEvent.class, this::onFileOpenRequest);
        EventBus.instance().register(SaveFileRequestEvent.class, this::onSaveFileRequest);
        EventBus.instance().register(NewFileRequestEvent.class, this::onNewFileRequest);

        this.stage.setOnCloseRequest(this::onExitAppRequest);
    }

    private void onNewFileRequest(NewFileRequestEvent event) {
        if (promptSaveIfNeeded())
            return;

        myCodeArea.clear();
        OpenFileTracker.instance().registerOpenedFile(null);
        stage.setTitle("ScriptExecutor");
    }

    private void onExitAppRequest(WindowEvent e) {
        if (promptSaveIfNeeded())
            e.consume();
    }

    private void onSaveFileRequest(SaveFileRequestEvent event) {
        if (event == null)
            return;

        String currentContent = myCodeArea.getText();
        if (OpenFileTracker.instance().getFile() == null) {
            saveAs(currentContent);
        } else {
            OpenFileTracker.instance().save(currentContent);
        }
    }

    private void onFileOpenRequest(FileOpenRequestEvent event) {
        if (event == null || event.getFile() == null)
            return;

        if (promptSaveIfNeeded())
            return;

        openFile(event.getFile());
    }

    private boolean promptSaveIfNeeded() {
        String currentContent = myCodeArea.getText();

        if (!OpenFileTracker.instance().hasUnsavedContent(currentContent)) {
            return false;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Unsaved Changes");
        alert.setGraphic(null);

        File currentFile = OpenFileTracker.instance().getFile();
        String fileName = currentFile != null ? currentFile.getName() : "Untitled";

        alert.setHeaderText(null);
        alert.setContentText("Save changes to: " + fileName + " ?");

        ButtonType saveButton = new ButtonType("Save");
        ButtonType dontSaveButton = new ButtonType("Don't Save");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(saveButton, dontSaveButton, cancelButton);

        AtomicBoolean cancelled = new AtomicBoolean(false);

        alert.showAndWait().ifPresent(response -> {
            if (response == saveButton) {
                if (currentFile != null) {
                    OpenFileTracker.instance().save(currentContent);
                } else {
                    if (!saveAs(currentContent)) {
                        cancelled.set(true);
                    }
                }
            } else if (response == cancelButton) {
                cancelled.set(true);
            }
        });

        return cancelled.get();
    }

    private boolean saveAs(String content) {
        File file = FileHelper.saveFileChooser();
        if (file != null) {
            OpenFileTracker.instance().saveAs(file, content);
            stage.setTitle("ScriptExecutor - " + file.getName());
            return true;
        }
        return false;
    }

    public void openFile(File file) {
        myCodeArea.clear();
        String content = FileHelper.readFromFile(file.getAbsolutePath());
        myCodeArea.insertText(0, content);

        OpenFileTracker.instance().registerOpenedFile(file);
        stage.setTitle("ScriptExecutor - " + file.getName());
    }
}