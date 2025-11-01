package fili5rovic.scriptexecutor.events.myEvents;

import org.fxmisc.richtext.CodeArea;

public class CaretChangeEvent extends MyEvent{
    private final CodeArea codeArea;

    public CaretChangeEvent(CodeArea codeArea) {
        this.codeArea = codeArea;
    }

    public CodeArea getCodeArea() {
        return codeArea;
    }



}
