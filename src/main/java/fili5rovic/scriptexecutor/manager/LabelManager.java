package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.controller.MainController;
import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.CaretChangeEvent;
import javafx.scene.control.Label;

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
        int line = event.getLineNum();
        int column = event.getColNum();

        Label lineStatus = controller.getLineStatus();
        lineStatus.setText(line + ":" + column);
    }
}
