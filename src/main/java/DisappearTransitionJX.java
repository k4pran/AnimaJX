import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;

public class DisappearTransitionJX implements TransitionJX {

    /******************************
     *         Properties         *
     ******************************/

    private Transition transition;
    private ObjJX objJX;
    private double start;

    /******************************
     *         Constructors       *
     ******************************/

    private DisappearTransitionJX(){}

    public static DisappearTransitionJX asFadeOut(double start, double duration, double startVal, double endVal, ObjJX objJX){
        DisappearTransitionJX disappearTransitionJX = new DisappearTransitionJX();
        FadeTransition fadeTransition = new FadeTransition(new Duration(duration), objJX.getNode());

        if (endVal > startVal) {
            double temp = endVal;
            endVal = startVal;
            startVal = temp;
        }
        fadeTransition.setFromValue(startVal);
        fadeTransition.setToValue(endVal);

        disappearTransitionJX.transition = fadeTransition;
        disappearTransitionJX.objJX = objJX;
        disappearTransitionJX.start = start;

        return disappearTransitionJX;
    }

    /******************************
     *         Overriders         *
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
