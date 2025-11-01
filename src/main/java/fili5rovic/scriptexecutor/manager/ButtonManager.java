package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.controller.MainController;
import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.CodeStopRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.ProcessFinishedEvent;
import fili5rovic.scriptexecutor.events.myEvents.RunCodeRequestEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class ButtonManager implements IManager {
    private final Button runBtn;
    private final Button stopBtn;
    private final Stage stage;

    public ButtonManager(Stage stage, MainController controller) {
        this.runBtn = controller.getRunBtn();
        this.stopBtn = controller.getStopBtn();
        this.stage = stage;
    }

    @Override
    public void initialize() {
        handleRunBtn();
        handleStopBtn();

        EventBus.instance().register(ProcessFinishedEvent.class, this::onProcessExit);
    }

    private void handleStopBtn() {
        stopBtn.setOnAction(e -> stopBtnAction());
        stopBtn.setDisable(true);

        stopBtn.setTooltip(new Tooltip("Stop Execution (Ctrl+X)"));

        KeyCombination accelerator = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);

        stage.getScene().addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (accelerator.match(event)) {
                stopBtn.fire();
                event.consume();
            }
        });
    }

    private void stopBtnAction() {
        EventBus.instance().publish(new CodeStopRequestEvent());
    }

    private void handleRunBtn() {
        runBtn.setOnAction(e -> runBtnAction());

        runBtn.setTooltip(new Tooltip("Run Code (Ctrl+R)"));

        KeyCombination accelerator = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);

        stage.getScene().addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (accelerator.match(event)) {
                runBtn.fire();
                event.consume();
            }
        });

    }

    private void onProcessExit(ProcessFinishedEvent event) {
        runBtn.setDisable(false);
        runBtn.setText("  Run  ");

        stopBtn.setDisable(true);
    }

    private void runBtnAction() {
        EventBus.instance().publish(new RunCodeRequestEvent());
        runBtn.setDisable(true);
        runBtn.setText("Running");

        stopBtn.setDisable(false);
    }
}
