package fili5rovic.scriptexecutor.console.behaviour;

import javafx.scene.input.ScrollEvent;
import org.fxmisc.richtext.CodeArea;

public class FontHelper {
    private static final int MAX_FONT_SIZE = 100;
    private static final int MIN_FONT_SIZE = 8;

    private static final int DEFAULT_FONT_SIZE = 24;

    private final CodeArea codeArea;
    private int fontSize;

    public FontHelper(CodeArea codeArea) {
        this.codeArea = codeArea;
        this.fontSize = DEFAULT_FONT_SIZE;
        applyZoomListener();
        applyZoom();
    }

    public void applyZoomListener() {
        codeArea.addEventFilter(ScrollEvent.SCROLL, e -> {
            if (e.isControlDown()) {
                e.consume();

                if (e.getDeltaY() > 0) {
                    increaseFontSize();
                } else if (e.getDeltaY() < 0) {
                    decreaseFontSize();
                }
            }
        });
    }

    private void increaseFontSize() {
        if (fontSize < MAX_FONT_SIZE) {
            fontSize += 2;
            applyZoom();
        }
    }

    private void decreaseFontSize() {
        if (fontSize > MIN_FONT_SIZE) {
            fontSize -= 2;
            applyZoom();
        }
    }

    private void applyZoom() {
        codeArea.setStyle("-fx-font-size: " + fontSize + "px;");
    }

}
