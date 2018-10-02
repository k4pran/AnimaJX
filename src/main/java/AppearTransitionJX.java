import javafx.animation.Animation;
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

    private Transition transition;
    private ObjJX objJX;
    private double start;

    private AppearTransitionJX(){}

    public static AppearTransitionJX asFadeIn(double start, double duration, double startVal, double endVal, ObjJX objJX){
        AppearTransitionJX appearTransitionJX = new AppearTransitionJX();
        FadeTransition fadeTransition = new FadeTransition(new Duration(duration), objJX.getNode());

        fadeTransition.setFromValue(startVal);
        fadeTransition.setToValue(endVal);

        appearTransitionJX.transition = fadeTransition;
        appearTransitionJX.objJX = objJX;
        appearTransitionJX.start = start;

        return appearTransitionJX;
    }

    public static AppearTransitionJX appearMoveTo(double start, double duration,
                                                  double fromAngle, double endX, double endY, ObjJX objJX){
        AppearTransitionJX appearTransitionJX = new AppearTransitionJX();

        double centerX = AnimationJX.getWidth() / 2;
        double centerY = AnimationJX.getHeight() / 2;

        fromAngle = (fromAngle - 90) * (Math.PI / 180);

        double a = Math.cos(fromAngle);
        double b = Math.sin(fromAngle);

        double distA = (0 - centerX) / a;
        double distB = (AnimationJX.getWidth() - centerX) / a;
        double distC = (0 - centerY) / b;
        double distD = (AnimationJX.getHeight() - centerY) / b;
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

        double startX = centerX + smallestNonNeg * a;
        double startY = centerY + smallestNonNeg * b;
        double offsetX = objJX.getNode().getBoundsInParent().getWidth() / 2;
        double offsetY = objJX.getNode().getBoundsInParent().getHeight() / 2;

        startX = startX > centerX ? startX + offsetX : startX - offsetX;
        startY = startY > centerY ? startY + offsetY : startY - offsetY;

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
        return appearTransitionJX;
    }

    public void autoReverse(boolean flag){
        transition.setAutoReverse(flag);
    }

    public void setCycles(int count){
        transition.setCycleCount(count);
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
