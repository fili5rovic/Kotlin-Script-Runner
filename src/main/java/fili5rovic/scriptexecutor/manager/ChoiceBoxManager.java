package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.controller.MainController;
import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.ThemeChangeEvent;
import javafx.scene.control.ChoiceBox;

public class ChoiceBoxManager implements IManager {
    private final MainController controller;

    public ChoiceBoxManager(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize() {
        setupThemeChoiceBox();
    }

    private void setupThemeChoiceBox() {
        ChoiceBox<String> themeChoiceBox = controller.getThemeChoiceBox();
        handleDefaultOptions(themeChoiceBox);


        handleActions(themeChoiceBox);

        EventBus.instance().publish(new ThemeChangeEvent(themeChoiceBox.getValue()));
    }

    private static void handleActions(ChoiceBox<String> themeChoiceBox) {
        themeChoiceBox.setOnAction(e -> {
            String selectedTheme = themeChoiceBox.getValue();
            EventBus.instance().publish(new ThemeChangeEvent(selectedTheme));
        });
    }

    private void handleDefaultOptions(ChoiceBox<String> themeChoiceBox) {
        themeChoiceBox.getItems().addAll("Darcula Blue", "Darcula Purple", "Monokai", "GitHub");
        themeChoiceBox.getSelectionModel().selectFirst();
    }
}
