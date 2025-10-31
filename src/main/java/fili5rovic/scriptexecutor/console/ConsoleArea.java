package fili5rovic.scriptexecutor.console;

import fili5rovic.scriptexecutor.console.behaviour.BehaviourListener;
import fili5rovic.scriptexecutor.console.behaviour.ErrorLineUtil;
import fili5rovic.scriptexecutor.console.behaviour.FontHelper;
import fili5rovic.scriptexecutor.console.contextMenu.ConsoleContextMenu;
import fili5rovic.scriptexecutor.console.highlighter.ConsoleHighlighter;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.util.List;

public class ConsoleArea extends CodeArea {

    public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    public static final int ERROR = 2;

    private final Redirector redirector;

    private int textType = 3;

    private ErrorLineUtil lineUtil;

    private final VirtualizedScrollPane<CodeArea> scrollPane;

    public ConsoleArea() {
        ConsoleHighlighter.apply(this);
        BehaviourListener.apply(this);
        FontHelper fontHelper = new FontHelper(this);
        fontHelper.applyZoomListener();

        this.setContextMenu(new ConsoleContextMenu(this));

        this.redirector = new Redirector(this);
        this.lineUtil = new ErrorLineUtil(this);

        scrollPane = new VirtualizedScrollPane<>(this);
    }

    public VirtualizedScrollPane<CodeArea> getScrollPane() {
        return scrollPane;
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
