package fili5rovic.scriptexecutor.events.myEvents;

public class CodeEditRequestEvent extends MyEvent{
    private final String actionName;

    public String getActionName() {
        return actionName;
    }

    public CodeEditRequestEvent(String actionName) {
        this.actionName = actionName;
    }
}
