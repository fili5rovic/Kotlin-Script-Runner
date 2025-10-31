package fili5rovic.scriptexecutor.events.myEvents;

public class CaretChangeEvent extends MyEvent{
    private final int lineNum;
    private final int colNum;

    public CaretChangeEvent(int lineNum, int colNum) {
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
