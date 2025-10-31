package fili5rovic.scriptexecutor.myCodeArea.theme;

import fili5rovic.scriptexecutor.Main;
import fili5rovic.scriptexecutor.events.EventBus;
import fili5rovic.scriptexecutor.events.myEvents.ThemeChangeEvent;
import fili5rovic.scriptexecutor.myCodeArea.MyCodeArea;

import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class ThemeHelper {
    private final MyCodeArea codeArea;
    private String currentThemeCss = null;

    private static final Map<String, String> THEME_MAP = Map.of("Darcula Blue", "codearea-theme-darcula-blue.css", "Darcula Purple", "codearea-theme-darcula-purple.css", "Monokai", "codearea-theme-monokai-night.css", "GitHub", "codearea-theme-github-dark.css");

    public ThemeHelper(MyCodeArea codeArea) {
        this.codeArea = codeArea;
    }

    public void applyListener() {
        EventBus.instance().register(ThemeChangeEvent.class, event -> {
            String newTheme = event.getThemeName();
            applyTheme(newTheme);
        });
    }

    private void applyTheme(String themeName) {
        String cssFile = THEME_MAP.get(themeName);
        if (cssFile != null) {
            URL resource = Main.class.getResource("css/codeThemes/" + cssFile);
            String newThemePath = Objects.requireNonNull(resource).toExternalForm();

            if (currentThemeCss != null) {
                codeArea.getStylesheets().remove(currentThemeCss);
            }

            codeArea.getStylesheets().add(newThemePath);
            codeArea.applyCss();
            currentThemeCss = newThemePath;
        }
    }
}