package fili5rovic.scriptexecutor.myCodeArea.templates;

import fili5rovic.scriptexecutor.Main;
import org.fxmisc.richtext.CodeArea;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Templates {

    private static final List<Template> templates = loadTemplates();

    public static void applyTemplates(CodeArea codeArea) {
        int paragraph = codeArea.getCurrentParagraph();
        String currentLine = codeArea.getParagraph(paragraph).getText();
        String trimmedLine = currentLine.trim();

        for (Template template : templates) {
            if (trimmedLine.equals(template.getTrigger())) {
                int leading = currentLine.indexOf(trimmedLine);
                int end = leading + template.getTrigger().length();
                codeArea.replaceText(paragraph, leading, paragraph, end, template.getReplacement());
                codeArea.moveTo(paragraph, leading + template.getCaretOffset());
                return;
            }
        }
    }

    private static List<Template> loadTemplates() {
        List<Template> result = new ArrayList<>();

        InputStream is = Main.class.getResourceAsStream("/fili5rovic/scriptexecutor/templates/kotlin-templates.txt");
        if (is == null) {
            System.err.println("Error: kotlin-templates.txt file not found in resources");
            return result;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) continue;

                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String trigger = parts[0];
                    String replacement = parts[1].replace("\\n", "\n").replace("\\t", "\t");
                    int caretOffset = Integer.parseInt(parts[2]);
                    result.add(new Template(trigger, replacement, caretOffset));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading templates: " + e.getMessage());
        }
        return result;
    }

}