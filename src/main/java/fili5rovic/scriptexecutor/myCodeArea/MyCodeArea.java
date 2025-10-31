package fili5rovic.scriptexecutor.myCodeArea;


import fili5rovic.scriptexecutor.myCodeArea.errorLineHandle.ErrorLineHandler;
import fili5rovic.scriptexecutor.myCodeArea.shortcuts.ShortcutHelper;
import javafx.collections.ObservableList;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class MyCodeArea extends CodeArea {

    private final LineNumberFactory lineNumberFactory;
    private final VirtualizedScrollPane<CodeArea> scrollPane;

    public MyCodeArea() {
        super();

        lineNumberFactory = new LineNumberFactory(this);
        this.setParagraphGraphicFactory(lineNumberFactory);

        MySyntaxHighlighter.setupHighlighting(this);

        CodeActionsManager codeActionsManager = new CodeActionsManager(this);
        codeActionsManager.setup();
        ShortcutHelper.applyShortcuts(this);

        ErrorLineHandler errorLineHandler = new ErrorLineHandler(this);
        errorLineHandler.registerListener();

        scrollPane = new VirtualizedScrollPane<>(this);
    }

    @SuppressWarnings("RedundantCast")
    public int getParagraphsCount() {
        return ((ObservableList<?>) this.getParagraphs()).size();
    }

    public boolean hasSelection() {
        return !this.getSelectedText().isEmpty();
    }

    public VirtualizedScrollPane<CodeArea> getScrollPane() {
        return scrollPane;
    }

}

