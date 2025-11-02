package fili5rovic.scriptexecutor.myCodeArea.templates;

public class Template {
    private final String trigger;
    private final String replacement;
    private final int caretOffset;

    public Template(String trigger, String replacement, int caretOffset) {
        this.trigger = trigger;
        this.replacement = replacement;
        this.caretOffset = caretOffset;
    }

    public String getTrigger() {
        return trigger;
    }

    public String getReplacement() {
        return replacement;
    }

    public int getCaretOffset() {
        return caretOffset;
    }
}
