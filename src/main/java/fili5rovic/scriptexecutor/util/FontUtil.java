package fili5rovic.scriptexecutor.util;

import fili5rovic.scriptexecutor.Main;
import javafx.scene.text.Font;

import java.io.InputStream;

public class FontUtil {

    private static final String FONT_JETBRAINS_MONO = "/fili5rovic/scriptexecutor/fonts/JetBrainsMono-Medium.ttf";

    public static void loadFonts() {
        loadJetBrainsMono();
    }

    private static void loadJetBrainsMono() {
        InputStream jetbrains = Main.class.getResourceAsStream(FONT_JETBRAINS_MONO);

        if (jetbrains == null) {
            System.err.println("Font doesn't exist! Path:" + FONT_JETBRAINS_MONO);
            return;
        }
        Font.loadFont(jetbrains, 13);
    }
}