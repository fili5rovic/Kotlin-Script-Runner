package fili5rovic.scriptexecutor.util;

import java.io.File;

public class OpenFileTracker {

    private static OpenFileTracker instance;
    private File file;
    private String lastSavedContent = "";

    private OpenFileTracker() {}

    public static OpenFileTracker instance() {
        if (instance == null)
            instance = new OpenFileTracker();
        return instance;
    }

    public void registerOpenedFile(File file) {
        this.file = file;
        if (file != null) {
            this.lastSavedContent = FileHelper.readFromFile(file.getAbsolutePath());
        } else {
            this.lastSavedContent = "";
        }
    }

    public boolean isModified(String currentContent) {
        return !lastSavedContent.equals(currentContent);
    }

    public void save(String newContent) {
        if (file == null) return;
        FileHelper.writeToFile(file.getAbsolutePath(), newContent);
        this.lastSavedContent = newContent;
    }

    public void saveAs(File newFile, String content) {
        this.file = newFile;
        FileHelper.writeToFile(newFile.getAbsolutePath(), content);
        this.lastSavedContent = content;
    }

    public File getFile() {
        return file;
    }

    public boolean hasUnsavedContent(String currentContent) {
        return !currentContent.equals(lastSavedContent) &&
                !currentContent.isEmpty();
    }
}