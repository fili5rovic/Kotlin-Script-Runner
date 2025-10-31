package fili5rovic.scriptexecutor.events.myEvents;

public class ErrorLineClickedEvent extends MyEvent{
    private final int lineNum,colNum;

    public ErrorLineClickedEvent(int lineNum, int colNum) {
        this.lineNum = lineNum;
        this.colNum = colNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public int getColNum() {
        return colNum;
    }


}
