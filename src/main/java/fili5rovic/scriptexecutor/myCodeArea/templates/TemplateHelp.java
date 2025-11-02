package fili5rovic.scriptexecutor.myCodeArea.templates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class TemplateHelp {

    private static Stage popup;
    private static boolean isInitialized = false;

    public static void showTemplateHelp(Stage owner) {
        if (popup != null && popup.isShowing()) {
            popup.close();
            return;
        }

        if (!isInitialized) {
            initializePopup(owner);
            isInitialized = true;
        }

        if(popup != null) {
            popup.show();
            popup.requestFocus();
        }
    }

    private static void initializePopup(Stage owner) {
        popup = new Stage();
        popup.initStyle(StageStyle.UNDECORATED);
        popup.initModality(Modality.NONE);
        popup.initOwner(owner);
        popup.setTitle("Template Shortcuts");
        popup.setAlwaysOnTop(true);

        VBox content = new VBox(10);
        content.setPadding(new Insets(15));
        content.setAlignment(Pos.TOP_LEFT);
        content.getStyleClass().add("template-help-content");

        Label header = new Label("Available Templates (CTRL+SPACE after trigger)");
        header.getStyleClass().add("template-help-header");
        content.getChildren().add(header);

        Separator separator = new Separator();
        content.getChildren().add(separator);

        for (Template template : Templates.getTemplates()) {
            String codePreview = template.getReplacement()
                    .replace("\n", " ")
                    .replace("\t", "");

            if (codePreview.length() > 50) {
                codePreview = codePreview.substring(0, 47) + "...";
            }

            addTemplate(content,
                    template.getTrigger(),
                    codePreview,
                    template.getDescription());
        }

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("template-help-scroll");

        Scene scene = new Scene(scrollPane, 500, 400);
        scene.getStylesheets().add(
                Objects.requireNonNull(TemplateHelp.class.getResource("/fili5rovic/scriptexecutor/css/templatesPopup/templates.css")).toExternalForm()
        );
        popup.setScene(scene);

        popup.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                popup.close();
                owner.requestFocus();
            }
        });

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                popup.close();
            }
        });
    }

    private static void addTemplate(VBox container, String trigger, String code, String description) {
        VBox templateBox = new VBox(3);
        templateBox.setPadding(new Insets(5, 0, 5, 0));
        templateBox.getStyleClass().add("template-box");

        Label triggerLabel = new Label(trigger);
        triggerLabel.getStyleClass().add("template-trigger");

        Label codeLabel = new Label("  → " + code);
        codeLabel.getStyleClass().add("template-code");

        if (description != null && !description.isEmpty()) {
            Label descLabel = new Label("  " + description);
            descLabel.getStyleClass().add("template-description");
            templateBox.getChildren().addAll(triggerLabel, codeLabel, descLabel);
        } else {
            templateBox.getChildren().addAll(triggerLabel, codeLabel);
        }

        container.getChildren().add(templateBox);
    }

    public static void close() {
        if (popup != null && popup.isShowing()) {
            popup.close();
        }
    }
}