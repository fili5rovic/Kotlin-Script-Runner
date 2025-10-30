package fili5rovic.scriptexecutor.script;

import java.io.IOException;

public class ScriptRunner {
    public static void runKotlinScript(String scriptPath) {
        try {
            String kotlinCommand = getKotlinCommand();
            ProcessBuilder pb = new ProcessBuilder(kotlinCommand, "-script", scriptPath);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);

            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                System.err.println("Script failed with exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error while running script: " + e.getMessage());
            e.printStackTrace();
        }
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