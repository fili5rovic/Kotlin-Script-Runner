package fili5rovic.scriptexecutor.myCodeArea;


import fili5rovic.scriptexecutor.myCodeArea.shortcuts.ShortcutHelper;
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
        ShortcutHelper.applyShortcuts(this);
    }
}

