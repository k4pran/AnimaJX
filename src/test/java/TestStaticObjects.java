import javafx.application.Application;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

public class TestStaticObjects {

    @Ignore
    public void squareShouldAppearThenDisappear(){
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromSquare(200);
        shapeJX.setPosition((width / 2) - (shapeJX.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - shapeJX.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddStaticObject(shapeJX, 0, 4000);
        sequenceJX.play();

        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Test
    public void starPolygonShouldAppearThenDisappear(){
        double width = 600;
        double height = 600;

        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(75,0));
        points.add(new Point(150,150));
        points.add(new Point(0,50));
        points.add(new Point(150,50));
        points.add(new Point(0,150));
        points.add(new Point(75,0));

        ShapeJX shapeJX = ShapeJX.fromPolygon(points);
        shapeJX.setPosition((width / 2) - (shapeJX.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - shapeJX.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddStaticObject(shapeJX, 0, 4000);
        sequenceJX.play();

        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Ignore
    public void squareShouldAppearInsideScaledSquare() {
        double width = 600;
        double height = 600;

        ShapeJX shapeJX1 = ShapeJX.fromSquare(200);
        shapeJX1.setPosition((width / 2) - (shapeJX1.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - shapeJX1.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        ShapeJX shapeJX2 = ShapeJX.fromSquare(200);
        shapeJX2.pointScale(2);
        shapeJX1.setPosition((width / 2) - (shapeJX2.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - shapeJX2.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);


        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddStaticObject(shapeJX1, 0, 4000);
        sequenceJX.AddStaticObject(shapeJX2, 0, 4000);
        sequenceJX.play();

        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Ignore
    public void roundedTriangleShouldAppearThenDisappear(){
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromRoundedTriangle(0, 0, 0, 100, 100, 100, 0.1, 0.9);
        shapeJX.setPosition((width / 2) - (shapeJX.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - shapeJX.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddStaticObject(shapeJX, 0, 4000);
        sequenceJX.play();

        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Ignore
    public void squareAndCircleShouldAppearThenDisappear(){
        double width = 600;
        double height = 600;

        ShapeJX square = ShapeJX.fromSquare(200);
        square.setPosition((width / 2) - (square.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - square.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        ShapeJX circle = ShapeJX.fromCircle(200);
        circle.setPosition((width / 2), (height / 2));

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddStaticObject(circle, 0, 2000);
        sequenceJX.AddStaticObject(square, 1000, 2000);
        sequenceJX.play();

        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }
}
