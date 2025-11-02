package fili5rovic.scriptexecutor.myCodeArea.templates;

public class Template {
    private final String trigger;
    private final String replacement;
    private final int caretOffset;
    private final String description;

    public Template(String trigger, String replacement, int caretOffset, String description) {
        this.trigger = trigger;
        this.replacement = replacement;
        this.caretOffset = caretOffset;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}