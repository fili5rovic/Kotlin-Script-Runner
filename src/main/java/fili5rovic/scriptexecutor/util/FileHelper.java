package fili5rovic.scriptexecutor.util;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper {

    public static String readFromFile(String path) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            if (!sb.isEmpty())
                sb.deleteCharAt(sb.length() - 1);
        } catch (IOException e) {
            System.err.println("Couldn't read file " + path);
        }
        return sb.toString();
    }

    public static void writeToFile(String path, String content) {
        try {
            Files.write(Path.of(path), content.getBytes());
        } catch (IOException e) {
            System.out.println("Couldn't write to file " + path);
        }
    }

    public static File openFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Kotlin Files", "*.kts"));
        return fileChooser.showOpenDialog(null);
    }

}

