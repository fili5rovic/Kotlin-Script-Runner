package fili5rovic.scriptexecutor.console.contextMenu;

import fili5rovic.scriptexecutor.console.ConsoleArea;
import fili5rovic.scriptexecutor.util.SVGUtil;
import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;

public class ConsoleContextMenu extends ContextMenu {
    private final ConsoleArea consoleArea;
    public ConsoleContextMenu(ConsoleArea consoleArea) {
        super();
        this.consoleArea = consoleArea;
        this.getStyleClass().add("console-context-menu");
        init();
    }

    private void init() {
        if(this.consoleArea == null)
            return;

        getItems().clear();

        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setGraphic(SVGUtil.getUI("copy", 16));
        copyItem.setOnAction(e -> consoleArea.copy());
        copyItem.setDisable(true);

        MenuItem copyAllItem = new MenuItem("Copy All");
        copyAllItem.setGraphic(SVGUtil.getUI("copy", 16));
        copyAllItem.setOnAction(e -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(consoleArea.getText());
            Clipboard.getSystemClipboard().setContent(content);
        });

        MenuItem selectAllItem = new MenuItem("Select All");
        selectAllItem.setGraphic(SVGUtil.getUI("saveAll",16));
        selectAllItem.setOnAction(e -> consoleArea.selectAll());



        MenuItem wrapItem = new MenuItem("Wrap Text");
        wrapItem.setGraphic(SVGUtil.getUI("wrap",16));
        wrapItem.setOnAction(e -> {
            consoleArea.setWrapText(!consoleArea.isWrapText());

            Platform.runLater(() -> {
                String text = consoleArea.getText();
                StyleSpans<Collection<String>> styles = consoleArea.getStyleSpans(0, text.length());
                int caretPos = consoleArea.getCaretPosition();

                consoleArea.clear();

                Platform.runLater(() -> {
                    consoleArea.replaceText(text);
                    consoleArea.setStyleSpans(0, styles);
                    consoleArea.moveTo(caretPos);
                });
            });

            if(consoleArea.isWrapText()) {
                wrapItem.setGraphic(SVGUtil.getUI("unwrap",16));
            } else {
                wrapItem.setGraphic(SVGUtil.getUI("wrap",16));
            }
        });

        MenuItem clearItem = new MenuItem("Clear");
        clearItem.setGraphic(SVGUtil.getUI("delete",16));
        clearItem.setOnAction(e -> {
            consoleArea.clear();
            consoleArea.setTextType(ConsoleArea.OUTPUT);
        });

        setOnShowing(e -> {
            boolean hasSelection = consoleArea.getSelectedText() != null
                    && !consoleArea.getSelectedText().isEmpty();
            copyItem.setDisable(!hasSelection);
        });

        getItems().addAll(
                copyItem,
                copyAllItem,
                new SeparatorMenuItem(),
                selectAllItem,
                new SeparatorMenuItem(),
                wrapItem,
                clearItem
        );
    }



}
