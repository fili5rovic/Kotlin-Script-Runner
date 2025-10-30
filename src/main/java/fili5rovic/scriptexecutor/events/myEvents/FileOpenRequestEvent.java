package fili5rovic.scriptexecutor.events.myEvents;

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
