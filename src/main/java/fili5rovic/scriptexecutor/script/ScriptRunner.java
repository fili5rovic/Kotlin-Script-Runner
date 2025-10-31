package fili5rovic.scriptexecutor.script;

import fili5rovic.scriptexecutor.console.ConsoleArea;
import javafx.application.Platform;

import java.io.*;
import java.util.concurrent.CompletableFuture;

public class ScriptRunner {
    public static void runKotlinScript(String scriptPath, ConsoleArea console) {
        CompletableFuture.runAsync(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(getKotlinCommand(), "-script", scriptPath);

//                pb.environment().put("JAVA_TOOL_OPTIONS", "-Dfile.encoding=UTF-8");
//                pb.environment().put("LC_ALL", "en_US.UTF-8");

                Process process = pb.start();
                console.applyProcess(process);
            } catch (IOException e) {
                Platform.runLater(() -> console.appendText("\n[Error: " + e.getMessage() + "]\n"));
            }
        });
    }

    private static String getKotlinCommand() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return "kotlinc.bat";
        } else {
            return "kotlinc";
        }
    }
}