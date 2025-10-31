package fili5rovic.scriptexecutor.events.myEvents;

public class ThemeChangeEvent extends MyEvent{
    private final String themeName;

    public ThemeChangeEvent(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeName() {
        return themeName;
    }
}
