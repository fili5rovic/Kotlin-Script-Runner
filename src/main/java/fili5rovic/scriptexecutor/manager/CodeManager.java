package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.fxcode.MyCodeArea;
import fili5rovic.scriptexecutor.fxcode.MyConsoleArea;
import javafx.scene.layout.BorderPane;

public class CodeManager implements IManager {
    private final BorderPane codeBP;
    private final BorderPane consoleBP;

    public CodeManager(BorderPane codeBP, BorderPane consoleBP) {
        this.codeBP = codeBP;
        this.consoleBP = consoleBP;
    }

    @Override
    public void initialize() {
        createCodeAreas(codeBP);
    }

    private void createCodeAreas(BorderPane codeBorderPane) {
        codeBorderPane.setCenter(new MyCodeArea());
        consoleBP.setCenter(new MyConsoleArea());
    }
}
