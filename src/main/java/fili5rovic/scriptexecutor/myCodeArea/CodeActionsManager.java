package fili5rovic.scriptexecutor.myCodeArea;

import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.CaretChangeEvent;
import fili5rovic.scriptexecutor.myCodeArea.shortcuts.CodeActions;
import fili5rovic.scriptexecutor.myCodeArea.templates.Templates;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;

public class CodeActionsManager {

    private final CodeArea codeArea;

    public CodeActionsManager(CodeArea codeArea) {
        this.codeArea = codeArea;
    }

    public void applyShortcuts(MyCodeArea codeArea) {
        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

            if (event.getCode() == KeyCode.SPACE && event.isControlDown()) {
                Templates.applyTemplates(codeArea);
                event.consume();
            }

            if (event.getCode() == KeyCode.L && event.isControlDown() && event.isAltDown()) {
                CodeActions.formatCode(codeArea);
                event.consume();
            }

            if (event.getCode() == KeyCode.TAB && !event.isShiftDown()) {
                if (codeArea.hasSelection()) {
                    CodeActions.indentForward(codeArea);
                } else {
                    int column = codeArea.getCaretColumn();
                    int spacesToAdd = MyCodeArea.TAB_SIZE - (column % MyCodeArea.TAB_SIZE);
                    String spaces = " ".repeat(spacesToAdd);
                    codeArea.insertText(codeArea.getCaretPosition(), spaces);
                }
                event.consume();
            }

            if (event.getCode() == KeyCode.TAB && event.isShiftDown()) {
                CodeActions.indentBackward(codeArea);
                event.consume();
            }

            if (new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN).match(event)) {
                CodeActions.deleteLine(codeArea);
                event.consume();
            }

            if (new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN).match(event)) {
                CodeActions.duplicateLineAbove(codeArea);
                event.consume();
            }

            if (new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN).match(event)) {
                CodeActions.duplicateLineBelow(codeArea);
                event.consume();
            }

            if (new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN).match(event)) {
                CodeActions.moveLineUp(codeArea);
                event.consume();
            }

            if (new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN).match(event)) {
                CodeActions.moveLineDown(codeArea);
                event.consume();
            }

            if (new KeyCodeCombination(KeyCode.SLASH, KeyCombination.CONTROL_DOWN).match(event)) {
                CodeActions.toggleComment(codeArea);
                event.consume();
            }


        });

        codeArea.setOnKeyTyped(e -> {
            if(e.getCharacter().equals("(")) {
                codeArea.insertText(codeArea.getCaretPosition(), ")");
                codeArea.moveTo(codeArea.getCaretPosition() - 1);
            }
            if(e.getCharacter().equals("\"")) {
                codeArea.insertText(codeArea.getCaretPosition(), "\"");
                codeArea.moveTo(codeArea.getCaretPosition() - 1);
            }
        });
    }



    public void setup() {
        codeArea.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                autoIndent();
            }
        });

        codeArea.caretPositionProperty().addListener((obs, oldPos, newPos) -> {
            EventBus.instance().publish(new CaretChangeEvent(codeArea));
        });


    }

    private void autoIndent() {
        int caretPosition = codeArea.getCaretPosition();
        int currentLine = codeArea.getCurrentParagraph();
        if (currentLine == 0) return;

        String prevLine = codeArea.getParagraph(currentLine - 1).getText();
        String indent = getLeadingWhitespace(prevLine);
        codeArea.insertText(caretPosition, indent);
    }

    private String getLeadingWhitespace(String line) {
        int i = 0;
        while (i < line.length() && Character.isWhitespace(line.charAt(i))) i++;
        return line.substring(0, i);
    }
}