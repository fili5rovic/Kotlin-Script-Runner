package fili5rovic.scriptexecutor.console;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Redirector {

    private Process process;
    private final ConsoleArea console;

    private Thread currentThread = null;
    private boolean running = false;

    public Redirector(ConsoleArea console) {
        this.console = console;
    }

    public void registerProcess(Process process) {
        if(this.running && currentThread != null) {
            this.running = false;
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                System.err.println("Failed joining thread: " + e.getMessage());
            }
        }
        this.process = process;
    }

    public void sendInput(String input) {
        if(this.process == null || !this.process.isAlive())
            return;
        try {
            this.process.getOutputStream().write((input + "\n").getBytes());
            this.process.getOutputStream().flush();
        } catch (Exception e) {
            System.err.println("Error writing to input stream: " + e.getMessage());
        }
    }

    public void redirectStreams() {
        redirectOutput(process.getErrorStream(), ConsoleArea.ERROR);
        redirectOutput(process.getInputStream(), ConsoleArea.OUTPUT);
    }

    private void redirectOutput(InputStream inputStream, int type) {
        if(this.process == null || !this.process.isAlive())
            return;
        running = true;
        currentThread = new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8)
                );

                int ch;
                while (running && (ch = reader.read()) != -1) {
                    char c = (char) ch;
                    // carriage return should be ignored on windows
                    if (c == '\r') continue;
                    Platform.runLater(() -> {
                        if(ProcessHelper.isStopped())
                            return;
                        console.appendTextWithType(String.valueOf(c), type);
                    });
                }
            } catch (Exception e) {
                System.err.println("Error reading output stream: " + e.getMessage());
            }
        });
        currentThread.start();
    }


}
