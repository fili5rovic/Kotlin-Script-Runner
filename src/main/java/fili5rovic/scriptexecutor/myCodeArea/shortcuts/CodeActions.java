package fili5rovic.scriptexecutor.myCodeArea.shortcuts;

import fili5rovic.scriptexecutor.myCodeArea.MyCodeArea;

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

    public static void commentLine(MyCodeArea codeArea) {
        int startPar = codeArea.getCaretSelectionBind().getStartParagraphIndex();
        int endPar = codeArea.getCaretSelectionBind().getEndParagraphIndex();

        int finalCaretLine = -1;
        int finalCaretCol = -1;

        for (int i = startPar; i <= endPar; i++) {
            String line = codeArea.getText(i);
            int caretColumn = codeArea.getCaretColumn();

            if (line.startsWith("//")) {
                codeArea.deleteText(i, 0, i, 2);
            } else {
                codeArea.insertText(i, 0, "//");
                if (i + 1 < codeArea.getParagraphsCount()) {
                    finalCaretLine = i + 1;
                    finalCaretCol = caretColumn + 2;
                } else {
                    finalCaretLine = i;
                    finalCaretCol = Math.min(caretColumn + 2, codeArea.getText(i).length());
                }
            }
        }
        if (finalCaretLine != -1) {
            String targetLineText = codeArea.getText(finalCaretLine);
            int maxCol = targetLineText.length();
            int safeCaretCol = Math.min(finalCaretCol, maxCol);
            codeArea.moveTo(finalCaretLine, safeCaretCol);
        }
    }
}
