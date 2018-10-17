import javafx.application.Application;
import org.junit.Test;

public class TestRotations {

    @Test
    public void squareToTopLeftCorner() {
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromSquare(100);
        Rotator.standardRotation(shapeJX.getPoints(), 90, new Point(52, 53));
        shapeJX.draw();
        shapeJX.setPosition(100, 100);
        shapeJX.getDynamicCanvas().SHOW_OUTLINE();
        SceneController.addView(shapeJX.getNode());

        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }
}
