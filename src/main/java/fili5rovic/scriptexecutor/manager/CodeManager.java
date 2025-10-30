package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.ExitAppRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.FileOpenRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.SaveFileRequestEvent;
import fili5rovic.scriptexecutor.fxcode.MyCodeArea;
import fili5rovic.scriptexecutor.fxcode.MyConsoleArea;
import fili5rovic.scriptexecutor.util.FileHelper;
import fili5rovic.scriptexecutor.util.OpenFileTracker;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class CodeManager implements IManager {
    private final BorderPane codeBP;
    private final BorderPane consoleBP;
    private final Stage stage;

    private MyCodeArea myCodeArea;
    private MyConsoleArea myConsoleArea;

    public CodeManager(Stage stage, BorderPane codeBP, BorderPane consoleBP) {
        this.stage = stage;
        this.codeBP = codeBP;
        this.consoleBP = consoleBP;
    }

    @Override
    public void initialize() {
        createCodeAreas();
        EventBus.instance().register(FileOpenRequestEvent.class, this::onFileOpenRequest);
        EventBus.instance().register(SaveFileRequestEvent.class, this::onSaveFileRequest);

        this.stage.setOnCloseRequest(this::onExitAppRequest);
    }

    private void onExitAppRequest(WindowEvent e) {
        String currentContent = myCodeArea.getText();
        if (OpenFileTracker.instance().isModified(currentContent)) {
            File openedFile = OpenFileTracker.instance().getFile();
            boolean cancelledDialog = alertShouldSaveCurrent(openedFile);

            if (cancelledDialog)
                e.consume();
        }
    }

    private void onSaveFileRequest(SaveFileRequestEvent event) {
        if (event == null)
            return;

        String currentContent = myCodeArea.getText();
        OpenFileTracker.instance().save(currentContent);
    }

    private void createCodeAreas() {
        myCodeArea = new MyCodeArea();
        myConsoleArea = new MyConsoleArea();
        codeBP.setCenter(myCodeArea);
        consoleBP.setCenter(myConsoleArea);
    }

    private void onFileOpenRequest(FileOpenRequestEvent event) {
        if (event == null || event.getFile() == null)
            return;

        File newFile = event.getFile();
        File openedFile = OpenFileTracker.instance().getFile();

        if (openedFile != null) {
            String currentContent = myCodeArea.getText();

            if (OpenFileTracker.instance().isModified(currentContent)) {
                boolean cancelledDialog = alertShouldSaveCurrent(openedFile);

                if (cancelledDialog)
                    return;
            }
        }

        openFile(newFile);
    }

    private boolean alertShouldSaveCurrent(File openedFile) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText("Do you want to save changes to the current file?");
        alert.setContentText(openedFile.getName());

        ButtonType saveButton = new ButtonType("Save");
        ButtonType dontSaveButton = new ButtonType("Don't Save");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(saveButton, dontSaveButton, cancelButton);

        AtomicBoolean cancelled = new AtomicBoolean(false);

        String currentContent = myCodeArea.getText();

        alert.showAndWait().ifPresent(response -> {
            if (response == saveButton) {
                OpenFileTracker.instance().save(currentContent);
            } else if (response == cancelButton) {
                cancelled.set(true);
            }
        });
        return cancelled.get();
    }

    public void openFile(File file) {
        myCodeArea.clear();
        String content = FileHelper.readFromFile(file.getAbsolutePath());
        myCodeArea.insertText(0, content);

        OpenFileTracker.instance().registerOpenedFile(file);

        this.stage.setTitle("ScriptExecutor - " + file.getName());
    }
}
