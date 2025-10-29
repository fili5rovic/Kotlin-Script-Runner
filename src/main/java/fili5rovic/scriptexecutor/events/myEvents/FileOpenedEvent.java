package fili5rovic.scriptexecutor.events.myEvents;

import java.io.File;

public class FileOpenedEvent extends MyEvent {

    private final File file;
    private final String content;

    public FileOpenedEvent(File file, String content) {
        this.file = file;
        this.content = content;
    }

    public File getFile() {
        return file;
    }

    public String getContent() {
        return content;
    }


}
