package fili5rovic.scriptexecutor.console;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Redirector {

    private Process process;
    private final ConsoleArea console;

    private final List<Thread> activeThreads = new ArrayList<>();
    private volatile boolean running = false;

    public Redirector(ConsoleArea console) {
        this.console = console;
    }

    public void registerProcess(Process process) {
        stopAllThreads();

        this.process = process;
    }

    public void stopAllThreads() {
        this.running = false;

        for (Thread thread : activeThreads) {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
                try {
                    thread.join(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Interrupted while waiting for thread cleanup");
                }
            }
        }
        activeThreads.clear();

        if (process != null && process.isAlive()) {
            try {
                process.getOutputStream().close();
            } catch (Exception ignored) {
            }
            try {
                process.getInputStream().close();
            } catch (Exception ignored) {
            }
            try {
                process.getErrorStream().close();
            } catch (Exception ignored) {

            }
        }
    }

    public void sendInput(String input) {
        if (this.process == null || !this.process.isAlive())
            return;
        try {
            this.process.getOutputStream().write((input + "\n").getBytes(StandardCharsets.UTF_8));
            this.process.getOutputStream().flush();
        } catch (Exception e) {
            System.err.println("Error writing to input stream: " + e.getMessage());
        }
    }

    public void redirectStreams() {
        running = true;
        redirectOutput(process.getErrorStream(), ConsoleArea.ERROR);
        redirectOutput(process.getInputStream(), ConsoleArea.OUTPUT);
    }

    private void redirectOutput(InputStream inputStream, int type) {
        if (this.process == null || !this.process.isAlive())
            return;

        Thread thread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                int ch;
                while (running && !Thread.currentThread().isInterrupted()
                        && (ch = reader.read()) != -1) {
                    char c = (char) ch;
                    // carriage return should be ignored on windows
                    if (c == '\r') continue;

                    Platform.runLater(() -> {
                        if (ProcessHelper.isStopped())
                            return;
                        console.appendTextWithType(String.valueOf(c), type);
                    });
                }
            } catch (Exception e) {
                if (running && !Thread.currentThread().isInterrupted()) {
                    System.err.println("Error reading output stream: " + e.getMessage());
                }
            }
        });

        activeThreads.add(thread);
        thread.start();
    }
}