import javafx.application.Application;
import javafx.scene.paint.Color;
import org.junit.Ignore;
import org.junit.Test;

public class TestDisappearTransition {

    @Test
    public void squareShouldFadeOut(){
        double width = 600;
        double height = 600;

        ShapeJX square = ShapeJX.fromSquare(300);
        square.fillWith(Color.BLUE);
        square.setPosition((width / 2) - (square.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - square.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJX = DisappearTransitionJX.asFadeOut(0, 5000, 1, 0, square);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddAnimatedObject(transitionJX);
        sequenceJX.play();
        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

}
