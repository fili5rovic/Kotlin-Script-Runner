package fili5rovic.scriptexecutor.controller;

import fili5rovic.scriptexecutor.manager.ButtonManager;
import fili5rovic.scriptexecutor.manager.codeManager.CodeManager;
import fili5rovic.scriptexecutor.manager.IManager;
import fili5rovic.scriptexecutor.manager.MenuItemManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainController extends ControllerBase {

    @FXML
    private BorderPane codeBP;
    @FXML
    private BorderPane consoleBP;

    @FXML
    private MenuItem menuItemNewScript;

    @FXML
    private MenuItem menuItemOpenFile;

    @FXML
    private MenuItem menuItemSaveFile;

    @FXML
    private MenuItem menuitemThisProject;

    @FXML
    private MenuItem undo;

    @FXML
    private MenuItem redo;

    @FXML
    private MenuItem cut;

    @FXML
    private MenuItem copy;

    @FXML
    private MenuItem paste;

    @FXML
    private MenuItem delete;

    @FXML
    private MenuItem selectAll;

    @FXML
    private Button runBtn;

    @FXML
    private Button stopBtn;

    @FXML
    private Label lineStatus;

    @Override
    protected List<IManager> createManagers(Stage stage) {
        List<IManager> managers = new ArrayList<>();
        managers.add(new CodeManager(stage, this));
        managers.add(new MenuItemManager(this));
        managers.add(new ButtonManager(stage,this));
        return managers;
    }

    //<editor-fold desc="Getters">
    public BorderPane getCodeBP() {
        return codeBP;
    }

    public BorderPane getConsoleBP() {
        return consoleBP;
    }

    public MenuItem getMenuItemNewScript() {
        return menuItemNewScript;
    }

    public MenuItem getMenuItemOpenFile() {
        return menuItemOpenFile;
    }

    public MenuItem getMenuItemSaveFile() {
        return menuItemSaveFile;
    }

    public MenuItem getMenuitemThisProject() {
        return menuitemThisProject;
    }

    public MenuItem getUndo() {
        return undo;
    }

    public MenuItem getRedo() {
        return redo;
    }

    public MenuItem getCut() {
        return cut;
    }

    public MenuItem getCopy() {
        return copy;
    }

    public MenuItem getPaste() {
        return paste;
    }

    public MenuItem getDelete() {
        return delete;
    }

    public MenuItem getSelectAll() {
        return selectAll;
    }

    public Button getRunBtn() {
        return runBtn;
    }
    public Label getLineStatus() {
        return lineStatus;
    }

    public Button getStopBtn() {
        return stopBtn;
    }
    //</editor-fold>
}