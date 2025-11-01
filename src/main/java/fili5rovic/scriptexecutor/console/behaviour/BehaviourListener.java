package fili5rovic.scriptexecutor.console.behaviour;

import fili5rovic.scriptexecutor.console.ConsoleArea;
import fili5rovic.scriptexecutor.console.ProcessHelper;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BehaviourListener {

    private static final StringBuilder input = new StringBuilder();

    public static void clearInputBuffer() {
        input.setLength(0);
    }

    public static void apply(ConsoleArea console) {
        console.setEditable(true);

        console.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if(!ProcessHelper.canReceiveInput()) {
                e.consume();
                return;
            }

            if (e.getCode() == KeyCode.ENTER) {
                console.appendText("\n");
                console.setTextType(ConsoleArea.OUTPUT);
                if (!input.isEmpty()) {
                    String inputToSend = input.toString();
                    input.setLength(0);
                    console.getRedirector().sendInput(inputToSend);
                }
                e.consume();
            } else if (e.getCode() == KeyCode.BACK_SPACE) {
                if (!input.isEmpty()) {
                    input.setLength(input.length() - 1);

                    int caretPosition = console.getCaretPosition();

                    if (caretPosition > 0) {
                        console.deleteText(caretPosition - 1, caretPosition);
                    }
                }
                e.consume();
            }
        });

        console.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (e.isControlDown()) {
                return;
            }

            if(!ProcessHelper.canReceiveInput()) {
                e.consume();
                return;
            }

            String character = e.getCharacter();
            if (character.isEmpty() || character.equals("\r") ||
                    character.equals("\n") || character.equals("\b")) {
                e.consume();
                return;
            }

            console.setTextType(ConsoleArea.INPUT);
            input.append(character);
            console.appendText(character);
            e.consume();
        });
    }
}