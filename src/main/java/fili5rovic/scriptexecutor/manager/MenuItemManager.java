package fili5rovic.scriptexecutor.manager;

import fili5rovic.scriptexecutor.controller.MainController;
import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.CodeEditRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.FileOpenRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.NewFileRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.SaveFileRequestEvent;
import fili5rovic.scriptexecutor.myCodeArea.templates.TemplateHelp;
import fili5rovic.scriptexecutor.util.FileHelper;
import fili5rovic.scriptexecutor.util.SVGUtil;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URI;

public class MenuItemManager implements IManager {
    private final MainController controller;
    private final Stage stage;

    public MenuItemManager(Stage stage, MainController controller) {
        this.stage = stage;
        this.controller = controller;
        icons();
    }

    private void icons() {
        controller.getSaveFile().setGraphic(SVGUtil.getUI("saveAll", 16));
        controller.getOpenFile().setGraphic(SVGUtil.getUI("open",16));
        controller.getNewScript().setGraphic(SVGUtil.getUI("new",16));
        controller.getThisProject().setGraphic(SVGUtil.getUI("github",16));
        controller.getTemplates().setGraphic(SVGUtil.getUI("code",16));

        controller.getUndo().setGraphic(SVGUtil.getUI("undo", 16));
        controller.getRedo().setGraphic(SVGUtil.getUI("redo", 16));
        controller.getCut().setGraphic(SVGUtil.getUI("cut", 16));
        controller.getCopy().setGraphic(SVGUtil.getUI("copy", 16));
        controller.getPaste().setGraphic(SVGUtil.getUI("paste", 16));
        controller.getDelete().setGraphic(SVGUtil.getUI("delete", 16));
        controller.getSelectAll().setGraphic(SVGUtil.getUI("selectAll", 16));
        controller.getFormat().setGraphic(SVGUtil.getUI("format",16));
    }



    @Override
    public void initialize() {
        setupListeners();
    }

    private void setupListeners() {
        controller.getNewScript().setOnAction(this::handleNewScript);
        controller.getOpenFile().setOnAction(this::handleOpenFile);
        controller.getSaveFile().setOnAction(this::handleSaveFile);
        controller.getThisProject().setOnAction(this::handleThisProject);
        controller.getTemplates().setOnAction(this::handleTemplateHelp);

        controller.getUndo().setOnAction(e -> EventBus.instance().publish(new CodeEditRequestEvent("undo")));
        controller.getRedo().setOnAction(e -> EventBus.instance().publish(new CodeEditRequestEvent("redo")));
        controller.getCut().setOnAction(e -> EventBus.instance().publish(new CodeEditRequestEvent("cut")));
        controller.getCopy().setOnAction(e -> EventBus.instance().publish(new CodeEditRequestEvent("copy")));
        controller.getPaste().setOnAction(e -> EventBus.instance().publish(new CodeEditRequestEvent("paste")));
        controller.getDelete().setOnAction(e -> EventBus.instance().publish(new CodeEditRequestEvent("delete")));
        controller.getSelectAll().setOnAction(e -> EventBus.instance().publish(new CodeEditRequestEvent("selectAll")));
        controller.getFormat().setOnAction(e -> EventBus.instance().publish(new CodeEditRequestEvent("format")));
    }

    private void handleTemplateHelp(ActionEvent ignored) {
        TemplateHelp.showTemplateHelp(stage);
    }

    private void handleSaveFile(ActionEvent event) {
        EventBus.instance().publish(new SaveFileRequestEvent());
    }

    private void handleOpenFile(ActionEvent e) {
        File file = FileHelper.openFileChooser("Select Kotlin file");
        if (file == null)
            return;

        EventBus.instance().publish(new FileOpenRequestEvent(file));
    }

    private void handleNewScript(ActionEvent e) {
        EventBus.instance().publish(new NewFileRequestEvent());
    }

    private void handleThisProject(ActionEvent e) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/fili5rovic/Kotlin-Script-Runner"));
            } catch (Exception ex) {
                System.err.println("Couldn't open browser: " + ex.getMessage());
            }
        }
    }
}
