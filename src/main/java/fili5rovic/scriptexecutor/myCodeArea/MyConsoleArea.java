package fili5rovic.scriptexecutor.myCodeArea;


import org.fxmisc.richtext.CodeArea;

public class MyConsoleArea extends CodeArea {


    private final LineNumberFactory lineNumberFactory;

    public MyConsoleArea() {
        super();

        lineNumberFactory = new LineNumberFactory(this);
        this.setParagraphGraphicFactory(lineNumberFactory);

        MySyntaxHighlighter.setupHighlighting(this);

        CodeActionsManager codeActionsManager = new CodeActionsManager(this);
        codeActionsManager.setup();
    }
}

