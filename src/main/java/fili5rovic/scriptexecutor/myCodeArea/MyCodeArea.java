package fili5rovic.scriptexecutor.myCodeArea;


import fili5rovic.scriptexecutor.myCodeArea.errorLineHandle.ErrorLineHandler;
import fili5rovic.scriptexecutor.myCodeArea.theme.ThemeHelper;
import javafx.collections.ObservableList;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class MyCodeArea extends CodeArea {

    public static final int TAB_SIZE = 4;

    private final VirtualizedScrollPane<CodeArea> scrollPane;

    public MyCodeArea() {
        super();

        ThemeHelper themeHelper = new ThemeHelper(this);
        themeHelper.applyListener();

        LineNumberFactory lineNumberFactory = new LineNumberFactory(this);
        lineNumberFactory.setup();
        this.setParagraphGraphicFactory(lineNumberFactory);

        MySyntaxHighlighter.setupHighlighting(this);

        CodeActionsManager codeActionsManager = new CodeActionsManager(this);
        codeActionsManager.setup();
        codeActionsManager.applyShortcuts(this);

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

