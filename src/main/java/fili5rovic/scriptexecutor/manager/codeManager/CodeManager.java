package fili5rovic.scriptexecutor.manager.codeManager;

import fili5rovic.scriptexecutor.console.ConsoleArea;
import fili5rovic.scriptexecutor.myCodeArea.MyCodeArea;
import fili5rovic.scriptexecutor.manager.IManager;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CodeManager implements IManager {
    private final BorderPane codeBP;
    private final BorderPane consoleBP;
    private final Stage stage;

    private MyCodeArea myCodeArea;
    private ConsoleArea myConsoleArea;

    public CodeManager(Stage stage, BorderPane codeBP, BorderPane consoleBP) {
        this.stage = stage;
        this.codeBP = codeBP;
        this.consoleBP = consoleBP;
    }

    @Override
    public void initialize() {
        createCodeAreas();
        CodeEventManager eventManager = new CodeEventManager(stage, myCodeArea, myConsoleArea);
        eventManager.initialize();
    }

    private void createCodeAreas() {
        myCodeArea = new MyCodeArea();
        myConsoleArea = new ConsoleArea();
        codeBP.setCenter(myCodeArea);
        consoleBP.setCenter(myConsoleArea);
    }


}