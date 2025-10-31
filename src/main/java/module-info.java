module fili5rovic.scriptexecutor {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.fxmisc.richtext;
    requires java.desktop;
    requires com.kitfox.svg;
    requires javafx.swing;
    requires org.fxmisc.flowless;


    opens fili5rovic.scriptexecutor to javafx.fxml;
    exports fili5rovic.scriptexecutor;
    exports fili5rovic.scriptexecutor.controller;
    exports fili5rovic.scriptexecutor.manager;
    exports fili5rovic.scriptexecutor.events.myEvents;
    exports fili5rovic.scriptexecutor.myCodeArea;
    exports fili5rovic.scriptexecutor.console;
    opens fili5rovic.scriptexecutor.controller to javafx.fxml;
    exports fili5rovic.scriptexecutor.manager.codeManager;
}