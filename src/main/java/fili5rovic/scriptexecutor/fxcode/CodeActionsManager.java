package fili5rovic.scriptexecutor.fxcode;

import org.fxmisc.richtext.CodeArea;

import java.util.Objects;

public class CodeActionsManager {

    private final CodeArea codeArea;

    public CodeActionsManager(CodeArea codeArea) {
        this.codeArea = codeArea;
    }

    public void setup() {
        codeArea.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()).toString().equals("ENTER")) {
                autoIndent();
            }
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
