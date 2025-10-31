package fili5rovic.scriptexecutor.myCodeArea.shortcuts;

import fili5rovic.scriptexecutor.myCodeArea.MyCodeArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;

public class ShortcutHelper {

    public static void applyShortcuts(MyCodeArea codeArea) {
        codeArea.addEventHandler(KeyEvent.KEY_PRESSED, event -> {

            if (event.getCode() == KeyCode.TAB && !event.isShiftDown()) {
                CodeActions.indentForward(codeArea);
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
                CodeActions.commentLine(codeArea);
                event.consume();
            }

        });
    }
}