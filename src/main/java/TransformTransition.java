import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TransformTransition implements TransitionJX {

    /******************************
     *         Properties         *
     ******************************/

    private Transition transition;
    private ObjJX objJX;
    private double start;

    /******************************
     *         Constructors       *
     ******************************/

    private TransformTransition(){}

    public static TransformTransition asSimpleScaling(double start, double duration,
                                                      double endX, double endY, ObjJX objJX) {
        TransformTransition transformTransition = new TransformTransition();
        transformTransition.start = start;
        transformTransition.objJX = objJX;
        transformTransition.start = start;

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(duration), objJX.getNode());
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(endX);
        scaleTransition.setToY(endY);

        transformTransition.transition = scaleTransition;
        return transformTransition;
    }

    public static TransformTransition asPointScaling(double start, double duration, double scaleFactor, ShapeJX shapeJX) {
        TransformTransition transformTransition = new TransformTransition();
        transformTransition.start = start;
        transformTransition.objJX = shapeJX;
        transformTransition.start = start;

        PointScaleTransition pointScaleTransition = new PointScaleTransition(shapeJX, shapeJX.getPoints(),
                scaleFactor, duration);
        transformTransition.transition = pointScaleTransition;

        return transformTransition;

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
