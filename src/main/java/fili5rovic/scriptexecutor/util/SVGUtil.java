package fili5rovic.scriptexecutor.util;


import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class SVGUtil {

    private static final String BASE_FOLDER = "fili5rovic/scriptexecutor/svg/";
    private static final String DEFAULT_ICON_PATH = "tip.svg";

    public static ImageView getUI(String name, double size) {
        return getSVG("ui/" + name + ".svg", size, size, 0);
    }

    public static ImageView getEmoji(String name, double size) {
        return getSVG("emoji/" + name + ".svg", size, size, 0);
    }

    public static ImageView getIcon(String name, double size) {
        return getSVG("icon/" + name + ".svg", size, size, 0);
    }

    private static ImageView getSVG(String relativePath, double width, double height, int translateY) {
        try {
            URL svgUrl = getResourceURL(relativePath);
            if (svgUrl == null) {
                System.err.println("SVG not found: " + relativePath + " — falling back to " + DEFAULT_ICON_PATH);
                svgUrl = getResourceURL(DEFAULT_ICON_PATH);
                if (svgUrl == null) {
                    System.err.println("Default SVG not found: " + DEFAULT_ICON_PATH);
                    return new ImageView();
                }
            }

            String content = new String(svgUrl.openStream().readAllBytes());
            SVGUniverse universe = new SVGUniverse();
            URI uri = universe.loadSVG(new java.io.StringReader(content), relativePath);

            SVGDiagram diagram = universe.getDiagram(uri);

            BufferedImage bufferedImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.translate(0, translateY);

            diagram.setIgnoringClipHeuristic(true);
            diagram.render(g2d);
            g2d.dispose();

            return new ImageView(SwingFXUtils.toFXImage(bufferedImage, null));

        } catch (SVGException e) {
            System.err.println("Failed to render SVG: " + e.getMessage());
            return new ImageView();
        } catch (IOException e) {
            System.err.println("Failed to read SVG file:" + e.getMessage());
            return new ImageView();
        }
    }

    private static URL getResourceURL(String relativePath) {
        return SVGUtil.class.getResource("/" + BASE_FOLDER + relativePath);
    }

}