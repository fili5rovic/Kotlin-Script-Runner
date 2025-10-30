package fili5rovic.scriptexecutor.events.myEvents;

import fili5rovic.scriptexecutor.util.FileHelper;

import java.io.File;

public class FileOpenRequestEvent extends MyEvent{

    private final File file;

    public FileOpenRequestEvent(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
