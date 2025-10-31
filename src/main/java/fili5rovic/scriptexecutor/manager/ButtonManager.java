package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.controller.MainController;
import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.ProcessFinishedEvent;
import fili5rovic.scriptexecutor.events.myEvents.RunCodeRequestEvent;
import javafx.scene.control.Button;

public class ButtonManager implements IManager {
    private final Button runBtn;

    public ButtonManager(MainController controller) {
        this.runBtn = controller.getRunBtn();
    }

    @Override
    public void initialize() {
        runBtn.setOnAction(e -> runBtnAction());
        EventBus.instance().register(ProcessFinishedEvent.class, this::onProcessExit);
    }

    private void onProcessExit(ProcessFinishedEvent event) {
        runBtn.setDisable(false);
        runBtn.setText("Run");
    }

    private void runBtnAction() {
        EventBus.instance().publish(new RunCodeRequestEvent());
        runBtn.setDisable(true);
        runBtn.setText("Running");
    }
}
