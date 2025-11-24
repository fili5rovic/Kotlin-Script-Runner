package fili5rovic.scriptexecutor.util;

import javafx.animation.ScaleTransition;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.util.Duration;

public class AnimUtil {

    public static void animateOrientationChange(SplitPane splitPane) {
        ScaleTransition shrink = new ScaleTransition(Duration.millis(200), splitPane);
        shrink.setToX(0.1);
        shrink.setToY(0.1);

        shrink.setOnFinished(ev -> {
            Orientation old = splitPane.getOrientation();
            splitPane.setOrientation(
                    old == Orientation.HORIZONTAL ? Orientation.VERTICAL : Orientation.HORIZONTAL
            );
            splitPane.setDividerPositions(0.5);

            ScaleTransition expand = new ScaleTransition(Duration.millis(200), splitPane);
            expand.setToX(1);
            expand.setToY(1);
            expand.play();
        });

        shrink.play();
    }
}
