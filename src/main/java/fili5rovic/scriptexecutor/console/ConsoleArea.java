package fili5rovic.scriptexecutor.console;

import fili5rovic.scriptexecutor.console.behaviour.BehaviourListener;
import fili5rovic.scriptexecutor.console.highlighter.ConsoleHighlighter;
import org.fxmisc.richtext.CodeArea;

import java.util.List;

public class ConsoleArea extends CodeArea {

    public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    public static final int ERROR = 2;

    private final Redirector redirector;

    private int textType = 3;

    public ConsoleArea() {
        ConsoleHighlighter.apply(this);
        BehaviourListener.apply(this);

        this.redirector = new Redirector(this);
    }

    public void applyProcess(Process process) {


        this.redirector.registerProcess(process);
        this.redirector.redirectStreams();

        ProcessHelper.waitForProcessExit(this, process);
    }

    // used for streams
    public void appendTextWithType(String text, int type) {
        int start = getLength();
        appendText(text);

        String styleClass = switch (type) {
            case INPUT -> "console_input";
            case ERROR -> "console_error";
            default -> "console_output";
        };
        setStyleClass(start, getLength(), styleClass);
        setStyle(start, getLength(), List.of("code-font", styleClass));

        moveTo(getLength());
        requestFollowCaret();
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public Redirector getRedirector() {
        return redirector;
    }

    // used for input
    public String getStyleClassForTextType() {
        return switch (textType) {
            case INPUT -> "console_input";
            case ERROR -> "console_error";
            default -> "console_output";
        };
    }

}
