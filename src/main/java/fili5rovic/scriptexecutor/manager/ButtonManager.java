package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.RunCodeRequestEvent;
import javafx.scene.control.Button;

public class ButtonManager implements IManager {
    private final Button runBtn;

    public ButtonManager(Button runBtn) {
        this.runBtn = runBtn;
    }

    @Override
    public void initialize() {
        runBtn.setOnAction(e -> EventBus.instance().publish(new RunCodeRequestEvent()));
    }
}
