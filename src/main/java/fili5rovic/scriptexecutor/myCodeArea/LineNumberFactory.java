package fili5rovic.scriptexecutor.myCodeArea;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.fxmisc.richtext.CodeArea;

import java.util.function.IntFunction;

public class LineNumberFactory implements IntFunction<Node> {

    private static final int BASE_WIDTH = 30;
    private static final int PADDING_LEFT = 5;
    private static final int PADDING_RIGHT = 8;
    private static final int INDICATOR_SPACE_ADDON = 10;
    private static final int MIN_INDICATOR_SPACE = 20;

    private static final int INDICATOR_SIZE_DIVIDER = 2;

    private static final int MAX_FONT_SIZE = 100;
    private static final int MIN_FONT_SIZE = 8;
    private static final int DEFAULT_FONT_SIZE = 24;

    private final CodeArea codeArea;
    private int fontSize;

    public LineNumberFactory(CodeArea codeArea) {
        this.codeArea = codeArea;
        this.fontSize = DEFAULT_FONT_SIZE;

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

        applyZoom();
    }

    @Override
    public Node apply(int lineNumber) {
        HBox lineBox = new HBox();
        lineBox.setAlignment(Pos.CENTER_RIGHT);
        lineBox.setPadding(new Insets(0, PADDING_RIGHT, 0, PADDING_LEFT));

        int fontAdjustment = Math.max(0, (fontSize - 12) * 2);
        int indicatorSpace = Math.max(MIN_INDICATOR_SPACE, fontSize / INDICATOR_SIZE_DIVIDER + INDICATOR_SPACE_ADDON);
        int totalWidth = BASE_WIDTH + indicatorSpace + fontAdjustment;

        lineBox.setMinWidth(totalWidth);
        lineBox.setPrefWidth(totalWidth);
        lineBox.setMaxWidth(totalWidth);

        lineBox.getStyleClass().add("line-number-box");

        Label lineText = new Label(String.valueOf(lineNumber + 1));
        lineText.getStyleClass().add("line-number-text");

        lineText.setStyle("-fx-font-size: " + fontSize + "px;");

        lineBox.getChildren().add(lineText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        lineBox.getChildren().add(spacer);

        return lineBox;
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

        Platform.runLater(() -> {
            codeArea.setParagraphGraphicFactory(null);
            codeArea.setParagraphGraphicFactory(this);
        });
    }


}