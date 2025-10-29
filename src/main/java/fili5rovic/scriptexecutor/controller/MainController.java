package fili5rovic.scriptexecutor.controller;

import fili5rovic.scriptexecutor.manager.CodeManager;
import fili5rovic.scriptexecutor.manager.IManager;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class MainController extends ControllerBase {

    @FXML
    private BorderPane codeBP;
    @FXML
    private BorderPane consoleBP;


    @Override
    protected List<IManager> createManagers() {
        List<IManager> managers = new ArrayList<>();
        managers.add(new CodeManager(codeBP, consoleBP));
        return managers;
    }
}