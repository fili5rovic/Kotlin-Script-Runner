package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.controller.MainController;
import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.CaretChangeEvent;
import javafx.scene.control.Label;
import org.fxmisc.richtext.CodeArea;

public class LabelManager implements IManager {
    private final MainController controller;

    public LabelManager(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize() {
        EventBus.instance().register(CaretChangeEvent.class, this::onCaretChange);
    }

    private void onCaretChange(CaretChangeEvent event) {
        CodeArea codeArea = event.getCodeArea();
        int line = codeArea.getCurrentParagraph() + 1;
        int col = codeArea.getCaretColumn() + 1;


        Label lineStatus = controller.getLineStatus();
        String text = line + ":" + col;
        if(codeArea.getSelection().getLength() > 0) {
            int selectionLength = codeArea.getSelection().getLength();
            text += " (" + selectionLength + " chars)";
        }
        lineStatus.setText(text);
    }
}
