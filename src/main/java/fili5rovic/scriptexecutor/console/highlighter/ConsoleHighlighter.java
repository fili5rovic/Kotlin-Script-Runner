package fili5rovic.scriptexecutor.console.highlighter;

import fili5rovic.scriptexecutor.console.ConsoleArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.List;

public class ConsoleHighlighter {

    public static void apply(ConsoleArea consoleArea) {
        listener(consoleArea);
    }

    private static void listener(ConsoleArea consoleArea) {
        consoleArea.textProperty().addListener((ignored, oldValue, newValue) -> {
            int size = newValue.length() - oldValue.length();
            if (size <= 0) return;
            String style = consoleArea.getStyleClassForTextType();
            consoleArea.setStyleSpans(oldValue.length(), computeHighlighting(size, style));
        });
    }


    public static StyleSpans<Collection<String>> computeHighlighting(int length, String style) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        spansBuilder.add(List.of("code-font", style), length);
        return spansBuilder.create();
    }


}
