import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class AppearTransitionJX implements TransitionJX {

    /******************************
     *         Properties         *
     ******************************/

    private Transition transition;
    private ObjJX objJX;
    private double duration;
    private double start;

    /******************************
     *         Constructors       *
     ******************************/

    private AppearTransitionJX(){}

    public static AppearTransitionJX asFadeIn(double start, double duration, double startVal, double endVal, ObjJX objJX){
        AppearTransitionJX appearTransitionJX = new AppearTransitionJX();
        FadeTransition fadeTransition = new FadeTransition(new Duration(duration), objJX.getNode());

        if (startVal > endVal) {
            double temp = endVal;
            endVal = startVal;
            startVal = temp;
        }

        fadeTransition.setFromValue(startVal);
        fadeTransition.setToValue(endVal);

        appearTransitionJX.transition = fadeTransition;
        appearTransitionJX.objJX = objJX;
        appearTransitionJX.start = start;
        appearTransitionJX.duration = duration;

        return appearTransitionJX;
    }

    public static AppearTransitionJX appearFromAngle(double start, double duration,
                                                     double fromAngle, double endX, double endY, ObjJX objJX){
        AppearTransitionJX appearTransitionJX = new AppearTransitionJX();

        fromAngle = (fromAngle - 90) * (Math.PI / 180);

        double a = Math.cos(fromAngle);
        double b = Math.sin(fromAngle);

        double distA = (0 - endX) / a;
        double distB = (AnimationJX.getWidth() - endX) / a;
        double distC = (0 - endY) / b;
        double distD = (AnimationJX.getHeight() - endY) / b;
        ArrayList<Double> distances = new ArrayList<>(Arrays.asList(distA, distB, distC, distD));

        double smallestNonNeg = distances.get(0);
        for (int i = 0; i < distances.size(); i++){
            if (smallestNonNeg < 0) {
                smallestNonNeg = distances.get(i);
            }
            if (distances.get(i) < smallestNonNeg && distances.get(i) >= 0) {
                smallestNonNeg = distances.get(i);
            }
        }

        double startX = endX + smallestNonNeg * a;
        double startY = endY + smallestNonNeg * b;

        Path path = new Path();
        path.getElements().add (new MoveTo (startX, startY));
        path.getElements().add (new LineTo(endX, endY));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(objJX.getNode());
        pathTransition.setPath(path);
        pathTransition.setDuration(new Duration(duration));

        appearTransitionJX.objJX = objJX;
        appearTransitionJX.transition = pathTransition;
        appearTransitionJX.start = start;
        appearTransitionJX.duration = duration;
        return appearTransitionJX;
    }

    /******************************
     *         General            *
     ******************************/

    public void autoReverse(boolean flag){
        transition.setAutoReverse(flag);
    }

    public void setCycles(int count){
        transition.setCycleCount(count);
    }

    /******************************
     *         Overrides          *
     ******************************/

    @Override
    public Node getNode() {
        return objJX.getNode();
    }

    @Override
    public Transition getTransition() {
        return transition;
    }

    @Override
    public double getStart() {
        return start;
    }
}
