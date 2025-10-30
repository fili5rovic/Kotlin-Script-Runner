package fili5rovic.scriptexecutor.myCodeArea;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySyntaxHighlighter {

    private static final String KEYWORD_PATTERN = "\\b(fun|val|var|class|object|interface|if|else|when|for|while|return|import|package|try|catch|finally|throw)\\b";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"|'([^'\\\\]|\\\\.)*'";
    private static final String COMMENT_PATTERN = "//[^\n]*|/\\*.*?\\*/";
    private static final String NUMBER_PATTERN = "\\b\\d+(\\.\\d+)?[fFdDlL]?\\b";
    private static final String FUNCTION_PATTERN = "\\b[a-zA-Z_][a-zA-Z0-9_]*(?=\\s*\\()";
    private static final String ANNOTATION_PATTERN = "@[a-zA-Z_][a-zA-Z0-9_]*";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<COMMENT>" + COMMENT_PATTERN + ")"
                    + "|(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<ANNOTATION>" + ANNOTATION_PATTERN + ")"
                    + "|(?<FUNCTION>" + FUNCTION_PATTERN + ")"
                    + "|(?<NUMBER>" + NUMBER_PATTERN + ")"
    );

    public static void setupHighlighting(CodeArea codeArea) {
        codeArea.textProperty().addListener((ignored1, ignored2, newText) -> {
            codeArea.setStyleSpans(0, computeHighlighting(newText));
        });
    }

    public static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while (matcher.find()) {
            String styleClass =
                    matcher.group("COMMENT") != null ? "comment" :
                            matcher.group("KEYWORD") != null ? "keyword" :
                                    matcher.group("STRING") != null ? "string" :
                                            matcher.group("ANNOTATION") != null ? "annotation" :
                                                    matcher.group("FUNCTION") != null ? "function" :
                                                            matcher.group("NUMBER") != null ? "number" :
                                                                    "default_text";

            spansBuilder.add(Collections.singleton("default_text"), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.singleton("default_text"), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}