package fili5rovic.scriptexecutor.myCodeArea;


import org.fxmisc.richtext.CodeArea;

public class MyCodeArea extends CodeArea {


    private final LineNumberFactory lineNumberFactory;

    public MyCodeArea() {
        super();

        lineNumberFactory = new LineNumberFactory(this);
        this.setParagraphGraphicFactory(lineNumberFactory);

        MySyntaxHighlighter.setupHighlighting(this);

        CodeActionsManager codeActionsManager = new CodeActionsManager(this);
        codeActionsManager.setup();
    }
}

