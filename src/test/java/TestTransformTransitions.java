import javafx.application.Application;
import org.junit.Ignore;
import org.junit.Test;

public class TestTransformTransitions {

    @Ignore
    public void squareShouldScale2X() {
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromSquare(50);
        shapeJX.getDynamicCanvas().setTranslateX(width / 2 - shapeJX.getDynamicCanvas().getWidth() / 2);
        shapeJX.getDynamicCanvas().setTranslateY(height / 2 - shapeJX.getDynamicCanvas().getHeight() / 2);
        TransformTransition transformTransition = TransformTransition.asSimpleScaling(0, 5000,
                2, 2, shapeJX);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddAnimatedObject(transformTransition);
        sequenceJX.play();
        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Test
    public void squareShouldPointScale2X() {
        double width = 600;
        double height = 600;

        ShapeJX shapeJX = ShapeJX.fromSquare(50);
        shapeJX.getDynamicCanvas().setWidth(600);
        shapeJX.getDynamicCanvas().setHeight(600);
        shapeJX.getDynamicCanvas().setTranslateX(0);
        shapeJX.getDynamicCanvas().setTranslateY(0);
        shapeJX.setOffsetX(275);
        shapeJX.setOffsetY(250);
        TransformTransition transformTransition = TransformTransition.asPointScaling(0, 5000,
                2.5, shapeJX);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddAnimatedObject(transformTransition);
        sequenceJX.play();
        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }
}
