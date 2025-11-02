package fili5rovic.scriptexecutor.controller;

import fili5rovic.scriptexecutor.manager.*;
import fili5rovic.scriptexecutor.manager.codeManager.CodeManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private MenuItem newScript;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveFile;

    @FXML
    private MenuItem thisProject;

    @FXML
    private MenuItem templates;

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
    private MenuItem format;

    @FXML
    private Label lineStatus;

    @FXML
    private ChoiceBox<String> themeChoiceBox;

    @FXML
    private Button runBtn;

    @FXML
    private Button stopBtn;

    @Override
    protected List<IManager> createManagers(Stage stage) {
        List<IManager> managers = new ArrayList<>();
        managers.add(new CodeManager(stage, this));
        managers.add(new MenuItemManager(stage, this));
        managers.add(new ButtonManager(stage,this));
        managers.add(new LabelManager(this));
        managers.add(new ChoiceBoxManager(this));
        return managers;
    }

    //<editor-fold desc="Getters">
    public BorderPane getCodeBP() {
        return codeBP;
    }

    public BorderPane getConsoleBP() {
        return consoleBP;
    }

    public MenuItem getNewScript() {
        return newScript;
    }

    public MenuItem getOpenFile() {
        return openFile;
    }

    public MenuItem getSaveFile() {
        return saveFile;
    }

    public MenuItem getThisProject() {
        return thisProject;
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

    public ChoiceBox<String> getThemeChoiceBox() {
        return themeChoiceBox;
    }

    public MenuItem getTemplates() {
        return templates;
    }

    public MenuItem getFormat() {
        return format;
    }
    //</editor-fold>
}