package fili5rovic.scriptexecutor.myCodeArea;

import fili5rovic.scriptexecutor.Main;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySyntaxHighlighter {

    private static final String KEYWORD_PATTERN = "\\b(" + keywordsPattern() + ")\\b";
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

            spansBuilder.add(List.of("code-font", "default_text"), matcher.start() - lastKwEnd);
            spansBuilder.add(List.of("code-font", styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }

        spansBuilder.add(List.of("code-font", "default_text"), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private static String keywordsPattern() {
        try (InputStream is = Main.class.getResourceAsStream("/fili5rovic/scriptexecutor/keywords/kotlinKeywords.csv")) {
            if (is == null) {
                throw new IllegalStateException("Keywords file not found in resources");
            }
            String keywords = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return keywords.replaceAll(",", "|");
        } catch (IOException e) {
            System.err.println("Failed to read keywords file: " + e.getMessage());
        }
        return "";
    }
}