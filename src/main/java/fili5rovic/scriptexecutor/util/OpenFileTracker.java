package fili5rovic.scriptexecutor.util;

import java.io.File;

public class OpenFileTracker {

    private static OpenFileTracker instance;
    private File file;

    private OpenFileTracker() {}

    public static OpenFileTracker instance() {
        if (instance == null)
            instance = new OpenFileTracker();
        return instance;
    }

    public void registerOpenedFile(File file) {
        this.file = file;
    }

    public boolean isModified(String currentContent) {
        if (file == null) return false;
        String diskContent = FileHelper.readFromFile(file.getAbsolutePath());
        return !diskContent.equals(currentContent);
    }

    public void save(String newContent) {
        if (file == null) return;
        FileHelper.writeToFile(file.getAbsolutePath(), newContent);
    }

    public File getFile() {
        return file;
    }
}
