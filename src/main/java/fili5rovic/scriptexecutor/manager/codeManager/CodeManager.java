package fili5rovic.scriptexecutor.manager.codeManager;

import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.FileOpenRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.NewFileRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.SaveFileRequestEvent;
import fili5rovic.scriptexecutor.fxcode.MyCodeArea;
import fili5rovic.scriptexecutor.fxcode.MyConsoleArea;
import fili5rovic.scriptexecutor.manager.IManager;
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

    private CodeEventManager eventManager;

    public CodeManager(Stage stage, BorderPane codeBP, BorderPane consoleBP) {
        this.stage = stage;
        this.codeBP = codeBP;
        this.consoleBP = consoleBP;
    }

    @Override
    public void initialize() {
        createCodeAreas();
        this.eventManager = new CodeEventManager(stage,myCodeArea);
        this.eventManager.initialize();


    }

    private void createCodeAreas() {
        myCodeArea = new MyCodeArea();
        myConsoleArea = new MyConsoleArea();
        codeBP.setCenter(myCodeArea);
        consoleBP.setCenter(myConsoleArea);
    }


}