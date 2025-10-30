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

    public static File openFolderChooser(Stage stage, File initialDirectory) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        if (initialDirectory != null)
            directoryChooser.setInitialDirectory(initialDirectory);

        return directoryChooser.showDialog(stage);
    }

    public static File saveFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Kotlin Files", "*.kts"));
        return fileChooser.showSaveDialog(null);
    }

    public static File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Kotlin file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Kotlin Files", "*.kts"));
        return fileChooser.showOpenDialog(null);
    }

}

