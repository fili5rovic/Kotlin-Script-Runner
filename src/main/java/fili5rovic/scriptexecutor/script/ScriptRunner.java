package fili5rovic.scriptexecutor.script;

import fili5rovic.scriptexecutor.fxcode.MyConsoleArea;
import javafx.application.Platform;

import java.io.*;
import java.util.concurrent.CompletableFuture;

public class ScriptRunner {
    public static void runKotlinScript(String scriptPath, MyConsoleArea console) {
        CompletableFuture.runAsync(() -> {
            try {
                String kotlinCommand = getKotlinCommand();
                ProcessBuilder pb = new ProcessBuilder(kotlinCommand, "-script", scriptPath);
                pb.redirectErrorStream(true);

                Process process = pb.start();

                Thread outputThread = getOutputThread(console, process);

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(process.getOutputStream()));


                int exitCode = process.waitFor();
                outputThread.join();

                Platform.runLater(() -> console.appendText("\n[Exit code: " + exitCode + "]\n"));

            } catch (IOException | InterruptedException e) {
                Platform.runLater(() -> console.appendText("\n[Error: " + e.getMessage() + "]\n"));
            }
        });
    }

    private static Thread getOutputThread(MyConsoleArea console, Process process) {
        Thread outputThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String finalLine = line;
                    Platform.runLater(() -> console.appendText(finalLine + "\n"));
                }
            } catch (IOException e) {
                System.err.println("Error running script: " + e.getMessage());
            }
        });
        outputThread.start();
        return outputThread;
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