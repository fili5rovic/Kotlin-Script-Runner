package fili5rovic.scriptexecutor.myCodeArea.errorLineHandle;

import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.ErrorLineClickedEvent;
import fili5rovic.scriptexecutor.myCodeArea.MyCodeArea;

public class ErrorLineHandler {
    private final MyCodeArea codeArea;

    public ErrorLineHandler(MyCodeArea codeArea) {
        this.codeArea = codeArea;
    }

    public void registerListener() {
        EventBus.instance().register(ErrorLineClickedEvent.class, this::handleErrorLineClicked);
    }

    private void handleErrorLineClicked(ErrorLineClickedEvent e) {
        int row = e.getLineNum() - 1;
        int col = e.getColNum() - 1;

        codeArea.requestFocus();

        try {
            if (row < 0 || row >= codeArea.getParagraphsCount()) {
                System.err.println("Invalid row: " + e.getLineNum());
                return;
            }

            int position = 0;
            for (int i = 0; i < row; i++) {
                position += codeArea.getParagraph(i).length() + 1;
            }

            int lineLength = codeArea.getParagraph(row).length();
            position += Math.max(0, Math.min(col, lineLength));

            codeArea.moveTo(position);
            codeArea.requestFollowCaret();

        } catch (Exception ex) {
            System.err.println("Error moving caret to line " + e.getLineNum() +
                    ", col " + e.getColNum() + ": " + ex.getMessage());
        }
    }
}
