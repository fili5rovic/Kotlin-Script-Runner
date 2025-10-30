package fili5rovic.scriptexecutor.controller;

import fili5rovic.scriptexecutor.manager.CodeManager;
import fili5rovic.scriptexecutor.manager.IManager;
import fili5rovic.scriptexecutor.manager.MenuItemManager;
import javafx.fxml.FXML;
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

    @Override
    protected List<IManager> createManagers(Stage stage) {
        List<IManager> managers = new ArrayList<>();
        managers.add(new CodeManager(stage, codeBP, consoleBP));
        managers.add(new MenuItemManager(menuItemNewScript, menuItemOpenFile, menuItemSaveFile));
        return managers;
    }
}