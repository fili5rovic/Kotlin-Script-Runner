package fili5rovic.scriptexecutor.console.behaviour;

import fili5rovic.scriptexecutor.console.ConsoleArea;
import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.ErrorLineClickedEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorLineUtil {
    private final ConsoleArea consoleArea;

    private static final Pattern ERROR_LINE_PATTERN = Pattern.compile(".*\\.(kts):(\\d+):(\\d+):.*");

    public ErrorLineUtil(ConsoleArea consoleArea) {
        this.consoleArea = consoleArea;

        setupClickListener();
    }

    private void setupClickListener() {
        consoleArea.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                int clickPosition = consoleArea.getCaretPosition();

                if (isErrorText(clickPosition)) {
                    handleErrorClick(clickPosition);
                }
            }
        });
    }

    private boolean isErrorText(int position) {
        try {
            var styleClasses = consoleArea.getStyleOfChar(position);
            return styleClasses.contains("console_error");
        } catch (Exception e) {
            return false;
        }
    }

    private void handleErrorClick(int position) {
        String text = consoleArea.getText();

        int lineStart = position;
        while (lineStart > 0 && text.charAt(lineStart - 1) != '\n') {
            lineStart--;
        }

        int lineEnd = position;
        while (lineEnd < text.length() && text.charAt(lineEnd) != '\n') {
            lineEnd++;
        }

        String line = text.substring(lineStart, lineEnd);

        Matcher matcher = ERROR_LINE_PATTERN.matcher(line);
        if (matcher.matches()) {
            String lineNumber = matcher.group(2);
            String columnNumber = matcher.group(3);

            int lineNum = Integer.parseInt(lineNumber);
            int colNum = Integer.parseInt(columnNumber);

            EventBus.instance().publish(new ErrorLineClickedEvent(lineNum, colNum));
        }
    }
}
