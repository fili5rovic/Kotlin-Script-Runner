package fili5rovic.scriptexecutor.myCodeArea.shortcuts;

import fili5rovic.scriptexecutor.myCodeArea.MyCodeArea;
import fili5rovic.scriptexecutor.util.KotlinFormatter;
import javafx.scene.control.IndexRange;
import org.fxmisc.richtext.CodeArea;

public class CodeActions {

    public static void indentForward(MyCodeArea codeArea) {
        if (!codeArea.hasSelection())
            return;

        int startParagraph = codeArea.getCaretSelectionBind().getStartParagraphIndex();
        int endParagraph = codeArea.getCaretSelectionBind().getEndParagraphIndex();
        int startPosition = codeArea.getCaretSelectionBind().getStartPosition();
        int endPosition = codeArea.getCaretSelectionBind().getEndPosition();

        StringBuilder indentedBlock = new StringBuilder();
        int newCharsCount = 0;

        for (int i = startParagraph; i <= endParagraph; i++) {
            indentedBlock.append('\t');
            indentedBlock.append(codeArea.getText(i));
            newCharsCount++;

            if (i < endParagraph) {
                indentedBlock.append('\n');
            }
        }

        codeArea.replaceText(
                startParagraph, 0,
                endParagraph, codeArea.getText(endParagraph).length(),
                indentedBlock.toString()
        );

        codeArea.selectRange(startPosition + 1, endPosition + newCharsCount);

    }

    public static void indentBackward(MyCodeArea codeArea) {
        int startPar = codeArea.getCaretSelectionBind().getStartParagraphIndex();
        int endPar = codeArea.getCaretSelectionBind().getEndParagraphIndex();

        String selectedText = codeArea.getText(startPar, 0, endPar, codeArea.getText(endPar).length());

        boolean hadTab = selectedText.startsWith("\t");
        String newText = selectedText.replaceAll("\n\t", "\n");
        if (hadTab)
            newText = newText.substring(1);

        int newCharNum = newText.length() - selectedText.length();
        int selectedTextStart = codeArea.getCaretSelectionBind().getStartPosition();
        int selectedTextEnd = codeArea.getCaretSelectionBind().getEndPosition() + newCharNum;

        if (hadTab && selectedTextStart > 0)
            selectedTextStart -= 1;

        codeArea.deleteText(startPar, 0, endPar, codeArea.getText(endPar).length());

        codeArea.insertText(startPar, 0, newText);

        codeArea.selectRange(selectedTextStart, selectedTextEnd);
    }

    public static void deleteLine(MyCodeArea codeArea) {
        if (codeArea.hasSelection()) {
            int startPar = codeArea.getCaretSelectionBind().getStartParagraphIndex();
            int endPar = codeArea.getCaretSelectionBind().getEndParagraphIndex();

            codeArea.deleteText(startPar, 0, endPar, codeArea.getText(endPar).length());
            codeArea.deleteNextChar();
        } else {
            int curr = codeArea.getCaretPosition();
            int lineStart = codeArea.getText().lastIndexOf("\n", curr - 1) + 1;
            int lineEnd = codeArea.getText().indexOf("\n", curr) + 1;

            if (lineEnd == 0) {
                lineEnd = codeArea.getLength();
            }

            codeArea.deleteText(lineStart, lineEnd);
        }
    }

    public static void moveLineUp(MyCodeArea codeArea) {
        int startPar = codeArea.getCaretSelectionBind().getStartParagraphIndex();
        int endPar = codeArea.getCaretSelectionBind().getEndParagraphIndex();

        if (startPar == 0)
            return;

        int endColumn = codeArea.getText(endPar).length();

        int selectedStartColumn = codeArea.getCaretSelectionBind().getStartColumnPosition();
        int selectedEndColumn = codeArea.getCaretSelectionBind().getEndColumnPosition();

        String text = codeArea.getText(startPar, 0, endPar, endColumn);
        // delete selected text
        codeArea.deleteText(startPar, 0, endPar, endColumn);
        codeArea.deletePreviousChar();
        // insert deleted text one line above
        codeArea.insertText(startPar - 1, 0, text + "\n");

        int newStartPosition = codeArea.getAbsolutePosition(startPar - 1, selectedStartColumn);
        int newEndPosition = codeArea.getAbsolutePosition(endPar - 1, selectedEndColumn);
        codeArea.selectRange(newStartPosition, newEndPosition);
    }

    public static void moveLineDown(MyCodeArea codeArea) {
        int startPar = codeArea.getCaretSelectionBind().getStartParagraphIndex();
        int endPar = codeArea.getCaretSelectionBind().getEndParagraphIndex();

        if (endPar == codeArea.getParagraphsCount() - 1)
            return;

        int endColumn = codeArea.getText(endPar).length();

        int selectedStartColumn = codeArea.getCaretSelectionBind().getStartColumnPosition();
        int selectedEndColumn = codeArea.getCaretSelectionBind().getEndColumnPosition();

        String text = codeArea.getText(startPar, 0, endPar, endColumn);
        codeArea.deleteText(startPar, 0, endPar, endColumn);
        codeArea.deletePreviousChar();

        try {
            codeArea.insertText(startPar + 1, 0, text + "\n");
        } catch (Exception e) {
            codeArea.appendText("\n" + text);
        }

        int newStartPosition = codeArea.getAbsolutePosition(startPar + 1, selectedStartColumn);
        int newEndPosition = codeArea.getAbsolutePosition(endPar + 1, selectedEndColumn);
        codeArea.selectRange(newStartPosition, newEndPosition);
    }

    public static void duplicateLineAbove(MyCodeArea codeArea) {
        if (!codeArea.hasSelection()) {
            int curr = codeArea.getCurrentParagraph();
            String text = codeArea.getText(curr);

            int index = codeArea.getAbsolutePosition(curr, 0);
            codeArea.insertText(index, text + "\n");

            if (curr == 0)
                curr = 1;

            codeArea.moveTo(curr, codeArea.getCaretColumn());
        }
    }

    public static void duplicateLineBelow(MyCodeArea codeArea) {
        if (!codeArea.hasSelection()) {
            int curr = codeArea.getCurrentParagraph();
            String text = codeArea.getText(curr);

            if (curr == codeArea.getParagraphsCount() - 1) {
                codeArea.appendText("\n" + text);
            } else {
                codeArea.insertText(codeArea.getAbsolutePosition(curr + 1, 0), text + "\n");
            }

            codeArea.moveTo(curr + 1, codeArea.getCaretColumn());
        }
    }

    public static void toggleComment(MyCodeArea codeArea) {
        int startPar = codeArea.getCaretSelectionBind().getStartParagraphIndex();
        int endPar = codeArea.getCaretSelectionBind().getEndParagraphIndex();

        boolean allCommented = true;
        for (int i = startPar; i <= endPar; i++) {
            String line = codeArea.getText(i);
            if (line.trim().isEmpty()) {
                continue;
            }
            if (!line.startsWith("//")) {
                allCommented = false;
                break;
            }
        }

        int caretParagraph = codeArea.getCurrentParagraph();
        int caretColumn = codeArea.getCaretColumn();
        IndexRange selection = codeArea.getSelection();
        boolean hasSelection = selection.getLength() > 0;

        if (allCommented) {
            uncommentLines(codeArea, startPar, endPar);
            if (caretParagraph >= startPar && caretParagraph <= endPar) {
                int newColumn = Math.max(0, caretColumn - 3);
                codeArea.moveTo(caretParagraph, newColumn);
            }
            if (hasSelection) {
                int linesAffected = endPar - startPar + 1;
                int newEnd = Math.max(selection.getStart(),
                        selection.getEnd() - (3 * linesAffected));
                codeArea.selectRange(selection.getStart(), newEnd);
            }
        } else {
            commentLines(codeArea, startPar, endPar);
            if (caretParagraph >= startPar && caretParagraph <= endPar) {
                int newColumn = caretColumn + 3;
                String line = codeArea.getText(caretParagraph);
                codeArea.moveTo(caretParagraph, Math.min(newColumn, line.length()));
            }
            if (hasSelection) {
                int linesAffected = endPar - startPar + 1;
                int newEnd = selection.getEnd() + (3 * linesAffected);
                codeArea.selectRange(selection.getStart(), newEnd);
            }
        }
    }

    private static void commentLines(MyCodeArea codeArea, int startPar, int endPar) {
        for (int i = startPar; i <= endPar; i++) {
            codeArea.insertText(i, 0, "// ");
        }
    }

    private static void uncommentLines(MyCodeArea codeArea, int startPar, int endPar) {
        for (int i = startPar; i <= endPar; i++) {
            String line = codeArea.getText(i);

            if (line.startsWith("// ")) {
                codeArea.deleteText(i, 0, i, 3);
            } else if (line.startsWith("//")) {
                codeArea.deleteText(i, 0, i, 2);
            }
        }
    }

    public static void formatCode(CodeArea codeArea) {
        if(codeArea.getText().trim().isEmpty()) {
            return;
        }
        String originalText = codeArea.getText();
        try {
            String formattedText = KotlinFormatter.format(originalText);
            if (formattedText != null && !formattedText.equals(originalText)) {
                int caretPosition = codeArea.getCaretPosition();
                codeArea.replaceText(formattedText);
                codeArea.moveTo(caretPosition);
            }
        } catch (Exception e) {
            System.err.println("Code formatting failed: " + e.getMessage());
        }
    }
}
