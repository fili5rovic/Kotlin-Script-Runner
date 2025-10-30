package fili5rovic.scriptexecutor.console;

import javafx.application.Platform;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcessHelper {

    public static void waitForProcessExit(ConsoleArea console, Process process) {
        AtomicInteger exitCode = new AtomicInteger();
        CompletableFuture.runAsync(()-> {
            try {
                exitCode.set(process.waitFor());
            } catch (InterruptedException e) {
                System.err.println("Process wait interrupted: " + e.getMessage());
            }
        }).thenRun(()-> Platform.runLater(() -> onProcessExit(console, exitCode.get())));
    }

    private static void onProcessExit(ConsoleArea console, int code) {
        console.setEditable(false);
        console.appendText("\nProcess finished with code: " + code + "\n");

        console.moveTo(console.getLength());
        console.requestFollowCaret();
    }
}
