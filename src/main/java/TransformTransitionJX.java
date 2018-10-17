import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TransformTransitionJX implements TransitionJX {

    /******************************
     *         Properties         *
     ******************************/

    private Transition transition;
    private ObjJX objJX;
    private double start;

    /******************************
     *         Constructors       *
     ******************************/

    private TransformTransitionJX(){}

    public static TransformTransitionJX asSimpleScaling(double start, double duration,
                                                        double endX, double endY, ObjJX objJX) {
        TransformTransitionJX transformTransitionJX = new TransformTransitionJX();
        transformTransitionJX.start = start;
        transformTransitionJX.objJX = objJX;
        transformTransitionJX.start = start;

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(duration), objJX.getNode());
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(endX);
        scaleTransition.setToY(endY);

        transformTransitionJX.transition = scaleTransition;
        return transformTransitionJX;
    }

    public static TransformTransitionJX asPointScaling(double start, double duration, double scaleFactor, ShapeJX shapeJX) {
        TransformTransitionJX transformTransitionJX = new TransformTransitionJX();
        transformTransitionJX.start = start;
        transformTransitionJX.objJX = shapeJX;
        transformTransitionJX.start = start;

        PointScaleTransition pointScaleTransition = new PointScaleTransition(shapeJX, shapeJX.getPoints(),
                scaleFactor, duration);
        transformTransitionJX.transition = pointScaleTransition;

        return transformTransitionJX;

    }

    /******************************
     *         Accessors          *
     ******************************/

    public double getStart() {
        return start;
    }

    @Override
    public Transition getTransition() {
        return transition;
    }

    @Override
    public Node getNode() {
        return objJX.getNode();
    }
}
