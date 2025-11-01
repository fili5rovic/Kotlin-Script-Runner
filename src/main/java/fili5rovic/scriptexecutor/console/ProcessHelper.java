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

    public static boolean canReceiveInput() {
        return currentProcess != null && currentProcess.isAlive() && !stopped;
    }

    public static void beforeProcessStart(ConsoleArea console, String scriptPath) {
        BehaviourListener.clearInputBuffer();
        console.setTextType(ConsoleArea.OUTPUT);
        Platform.runLater(() -> console.appendText("Executing: " + scriptPath + "\n"));
    }

    public static void registerStopListener() {
        EventBus.instance().register(CodeStopRequestEvent.class, e -> {
            if (currentProcess != null && currentProcess.isAlive()) {
                stopped = true;
                BehaviourListener.clearInputBuffer();
                destroyProcessTree(currentProcess);
            }
        });
    }


    public static void waitForProcessExit(ConsoleArea console, Process process) {
        currentProcess = process;
        stopped = false;
        AtomicInteger exitCode = new AtomicInteger();
        CompletableFuture.runAsync(() -> {
            try {
                exitCode.set(process.waitFor());
            } catch (InterruptedException e) {
                System.err.println("Process wait interrupted: " + e.getMessage());
            }
        }).thenRun(() -> Platform.runLater(() -> onProcessExit(console, exitCode.get())));
    }

    private static void onProcessExit(ConsoleArea console, int code) {
        console.setEditable(false);
        console.setTextType(ConsoleArea.OUTPUT);
        if (stopped) {
            console.setTextType(ConsoleArea.ERROR);
            console.appendText("\nProcess was stopped by user.\n");
            console.setTextType(ConsoleArea.OUTPUT);
        } else {
            console.appendText("\nProcess finished with code: " + code + "\n");
        }

        EventBus.instance().publish(new ProcessFinishedEvent());

        console.moveTo(console.getLength());
        console.requestFollowCaret();
    }

    /**
     * I needed to use this approach because sometimes child processes remain alive after destroying the parent process.
     *
     * @param process the root process to be destroyed
     */
    private static void destroyProcessTree(Process process) {
        process.descendants().forEach(ProcessHandle::destroy);
        process.destroy();

        try {
            boolean exited = process.waitFor(1, java.util.concurrent.TimeUnit.SECONDS);
            if (!exited) {
                process.descendants().forEach(ProcessHandle::destroyForcibly);
                process.destroyForcibly();
            }
        } catch (InterruptedException e) {
            process.descendants().forEach(ProcessHandle::destroyForcibly);
            process.destroyForcibly();
            Thread.currentThread().interrupt();
        }
    }
}