import javafx.application.Application;
import org.junit.Ignore;
import org.junit.Test;

public class TestStaticObjects {

    @Test
    public void SquareShouldAppearThenDisappear(){
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromSquare(200);
        shapeJX.setPosition((width / 2) - (shapeJX.getShape().getBoundsInLocal().getWidth() / 2),
                (height / 2) - shapeJX.getShape().getBoundsInLocal().getHeight() / 2);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddStaticObject(shapeJX, 0, 2000);
        sequenceJX.play();

        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Ignore
    public void SquareAndCircleShouldAppearThenDisappear(){
        double width = 600;
        double height = 600;

        ShapeJX square = ShapeJX.fromSquare(200);
        square.setPosition((width / 2) - (square.getShape().getBoundsInLocal().getWidth() / 2),
                (height / 2) - square.getShape().getBoundsInLocal().getHeight() / 2);

        ShapeJX circle = ShapeJX.fromCircle(200);
        circle.setPosition((width / 2), (height / 2));

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddStaticObject(circle, 0, 2000);
        sequenceJX.AddStaticObject(square, 1000, 2000);
        sequenceJX.play();

        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }
}
