import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class MoveTransition implements TransitionJX {

    private Transition transition;
    private ObjJX objJX;
    private double start;

    private MoveTransition (){}

    public static MoveTransition asLinear(double start, double duration, Point from, Point to, ObjJX objJX) {
        MoveTransition moveTransition = new MoveTransition();
        moveTransition.objJX = objJX;
        moveTransition.start = start;

        Path path = new Path();
        path.getElements().add(new MoveTo(from.getX(), from.getY()));
        path.getElements().add(new LineTo(to.getX(), to.getY()));

        moveTransition.transition = new PathTransition(Duration.millis(duration), path, objJX.getNode());
        return moveTransition;
    }

    public static MoveTransition asQuadBezier(double start, double duration, double ctrlPointX, double ctrlPointY,
                                              Point from, Point to, ObjJX objJX) {
        MoveTransition moveTransition = new MoveTransition();
        moveTransition.objJX = objJX;
        moveTransition.start = start;

        Path path = new Path();
        path.getElements().add(new MoveTo(from.getX(), from.getY()));
        path.getElements().add(new QuadCurveTo(ctrlPointX, ctrlPointY, to.getX(), to.getY()));

        moveTransition.transition = new PathTransition(Duration.millis(duration), path, objJX.getNode());
        return moveTransition;
    }

    public static MoveTransition asCubicBezier(double start, double duration, double ctrlPointX1, double ctrlPointY1,
                                               double ctrlPointX2, double ctrlPointY2, Point from, Point to,
                                               ObjJX objJX) {
        MoveTransition moveTransition = new MoveTransition();
        moveTransition.objJX = objJX;
        moveTransition.start = start;

        Path path = new Path();
        path.getElements().add(new MoveTo(from.getX(), from.getY()));
        path.getElements().add(new CubicCurveTo(ctrlPointX1, ctrlPointY1, ctrlPointX2, ctrlPointY2, to.getX(), to.getY()));

        moveTransition.transition = new PathTransition(Duration.millis(duration), path, objJX.getNode());
        return moveTransition;
    }

    @Override
    public Node getNode() {
        return objJX.getNode();
    }

    @Override
    public Transition getTransition() {
        return transition;
    }
}
