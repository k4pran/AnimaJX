import javafx.application.Application;
import org.junit.Ignore;
import org.junit.Test;

public class TestMovementTransitions {

    @Ignore
    public void squareShouldMoveDiagonally() {
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromSquare(50);
        MoveTransition moveTransition = MoveTransition.asLinear(0, 5000,
                new Point(0, 0), new Point(600 - shapeJX.getDynamicCanvas().getWidth() / 2,
                                                 600 - shapeJX.getDynamicCanvas().getHeight()), shapeJX);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddAnimatedObject(moveTransition);
        sequenceJX.play();
        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Ignore
    public void squareShouldCurveToEndPointQuadratic() {
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromSquare(50);
        MoveTransition moveTransition = MoveTransition.asQuadBezier(0, 5000, 400, 10,
                new Point(0, 0), new Point(600 - shapeJX.getDynamicCanvas().getWidth() / 2,
                        600 - shapeJX.getDynamicCanvas().getHeight()), shapeJX);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddAnimatedObject(moveTransition);
        sequenceJX.play();
        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Test
    public void squareShouldCurveToEndPointCubic() {
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromSquare(50);
        MoveTransition moveTransition = MoveTransition.asCubicBezier(0, 5000,
                200, 10, 400, 100,
                new Point(0, 0),
                new Point(600 - shapeJX.getDynamicCanvas().getWidth() / 2, 600 - shapeJX.getDynamicCanvas().getHeight()), shapeJX);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddAnimatedObject(moveTransition);
        sequenceJX.play();
        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }
}
