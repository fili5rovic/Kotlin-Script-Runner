package fili5rovic.scriptexecutor.console;

import fili5rovic.scriptexecutor.console.behaviour.BehaviourListener;
import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.CodeStopRequestEvent;
import fili5rovic.scriptexecutor.events.myEvents.ProcessFinishedEvent;
import javafx.application.Platform;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcessHelper {
    private static Process currentProcess;
    private static boolean stopped = false;


    public static boolean isStopped() {
        return stopped;
    }

    public static void beforeProcessStart(ConsoleArea console, String scriptPath) {
        BehaviourListener.clearInputBuffer();
        console.setTextType(ConsoleArea.OUTPUT);
        Platform.runLater(() -> console.appendText("Executing: " + scriptPath + "\n"));
    }

    public static void registerStopListener() {
        EventBus.instance().register(CodeStopRequestEvent.class, e -> {
            if(currentProcess != null && currentProcess.isAlive()) {
                currentProcess.destroy();
                stopped = true;
            }
        });
    }

    public static void waitForProcessExit(ConsoleArea console, Process process) {
        currentProcess = process;
        stopped = false;
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

        EventBus.instance().publish(new ProcessFinishedEvent());

        console.moveTo(console.getLength());
        console.requestFollowCaret();
    }
}
