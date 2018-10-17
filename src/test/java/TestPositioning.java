import javafx.application.Application;
import org.junit.Ignore;
import org.junit.Test;

public class TestPositioning {

    @Test
    public void squareToTopLeftCorner() {
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromSquare(50);
        shapeJX.setPosition(0, 0);
        SceneController.addView(shapeJX.getNode());
        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Ignore
    public void squareToTopLeftCornerAfterResizing() {
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromSquare(300);
        shapeJX.setWidth(150);
        shapeJX.setHeight(150);
        shapeJX.setPosition(0, 0);
        SceneController.addView(shapeJX.getNode());
        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Ignore
    public void triangleToTopLeftCornerAfter() {
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromTriangle(new Point(0, 0), new Point(50, 0), new Point(0, 50));
        SceneController.addView(shapeJX.getNode());
        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Ignore
    public void triangleToTopLeftCornerAfterAfterResizing() {
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromTriangle(new Point(0, 0), new Point(50, 0), new Point(0, 50));
        shapeJX.setWidth(200);
        shapeJX.setHeight(200);
        SceneController.addView(shapeJX.getNode());
        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Ignore
    public void trianglesInTopCorners() {
        double width = 600;
        double height = 600;

        ShapeJX triangle1 = ShapeJX.fromTriangle(new Point(0, 0), new Point(50, 0), new Point(0, 50));
        triangle1.setWidth(200);
        triangle1.setHeight(200);

        ShapeJX triangle2 = ShapeJX.fromTriangle(new Point(0, 0), new Point(50, 0), new Point(50, 50));
        triangle2.setWidth(200);
        triangle2.setHeight(200);
        triangle2.setPosition(397, 0);

        SceneController.addView(triangle1.getNode());
        SceneController.addView(triangle2.getNode());
        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }
}
