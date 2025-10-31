package fili5rovic.scriptexecutor.myCodeArea;


import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.ErrorLineClickedEvent;
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

        EventBus.instance().register(ErrorLineClickedEvent.class, this::handleErrorLineClicked);

        scrollPane = new VirtualizedScrollPane<>(this);
    }

    public VirtualizedScrollPane<CodeArea> getScrollPane() {
        return scrollPane;
    }

    public int getParagraphsCount() {
        return ((ObservableList<?>) this.getParagraphs()).size();
    }

    public boolean hasSelection() {
        return !this.getSelectedText().isEmpty();
    }


    private void handleErrorLineClicked(ErrorLineClickedEvent e) {
        System.out.println("ERROR CLICKED");

        int row = e.getLineNum() - 1;
        int col = e.getColNum() - 1;

        this.requestFocus();

        try {
            if (row < 0 || row >= getParagraphsCount()) {
                System.err.println("Invalid row: " + e.getLineNum());
                return;
            }

            int position = 0;
            for (int i = 0; i < row; i++) {
                position += getParagraph(i).length() + 1;
            }

            int lineLength = getParagraph(row).length();
            position += Math.max(0, Math.min(col, lineLength));

            moveTo(position);
            requestFollowCaret();

        } catch (Exception ex) {
            System.err.println("Error moving caret to line " + e.getLineNum() +
                    ", col " + e.getColNum() + ": " + ex.getMessage());
        }
    }
}

