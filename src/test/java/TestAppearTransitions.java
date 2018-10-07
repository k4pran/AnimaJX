import javafx.application.Application;
import javafx.scene.paint.Color;
import org.junit.Ignore;
import org.junit.Test;

public class TestAppearTransitions {

    @Ignore
    public void squareShouldFadeIn(){
        double width = 600;
        double height = 600;

        ShapeJX square = ShapeJX.fromSquare(300);
        square.fillWith(Color.BLUE);
        square.setPosition((width / 2) - (square.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - square.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJX = AppearTransitionJX.asFadeIn(0, 5000, 0, 1, square);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddAnimatedObject(transitionJX);
        sequenceJX.play();

        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Ignore
    public void nestedSquaresShouldFadeIn(){
        double width = 600;
        double height = 600;

        ShapeJX squareA = ShapeJX.fromSquare(300);
        squareA.fillWith(Color.BLACK);
        squareA.setPosition((width / 2) - (squareA.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - squareA.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJXA = AppearTransitionJX.asFadeIn(0, 2000, 0, 1, squareA);

        ShapeJX squareB = ShapeJX.fromSquare(250);
        squareB.fillWith(Color.WHITE);
        squareB.setPosition((width / 2) - (squareB.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - squareB.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJXB = AppearTransitionJX.asFadeIn(1800, 1900, 0, 1, squareB);

        ShapeJX squareC = ShapeJX.fromSquare(200);
        squareC.fillWith(Color.BLACK);
        squareC.setPosition((width / 2) - (squareC.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - squareC.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJXC = AppearTransitionJX.asFadeIn(3000, 1800, 0, 1, squareC);

        ShapeJX squareD = ShapeJX.fromSquare(150);
        squareD.fillWith(Color.WHITE);
        squareD.setPosition((width / 2) - (squareD.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - squareD.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJXD = AppearTransitionJX.asFadeIn(4300, 1700, 0, 1, squareD);

        ShapeJX squareE = ShapeJX.fromSquare(100);
        squareE.fillWith(Color.BLACK);
        squareE.setPosition((width / 2) - (squareE.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - squareE.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJXE = AppearTransitionJX.asFadeIn(6000, 1600, 0, 1, squareE);

        ShapeJX squareF = ShapeJX.fromSquare(50);
        squareF.fillWith(Color.WHITE);
        squareF.setPosition((width / 2) - (squareF.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - squareF.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJXF = AppearTransitionJX.asFadeIn(7000, 1300, 0, 1, squareF);

        ShapeJX squareG = ShapeJX.fromSquare(40);
        squareG.fillWith(Color.BLACK);
        squareG.setPosition((width / 2) - (squareG.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - squareG.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJXG = AppearTransitionJX.asFadeIn(7800, 1150, 0, 1, squareG);

        ShapeJX squareH = ShapeJX.fromSquare(30);
        squareH.fillWith(Color.WHITE);
        squareH.setPosition((width / 2) - (squareH.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - squareH.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJXH = AppearTransitionJX.asFadeIn(10000, 1000, 0, 1, squareH);

        ShapeJX squareI = ShapeJX.fromSquare(20);
        squareI.fillWith(Color.BLACK);
        squareI.setPosition((width / 2) - (squareI.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - squareI.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJXI = AppearTransitionJX.asFadeIn(10500, 600, 0, 1, squareI);

        ShapeJX squareJ = ShapeJX.fromSquare(10);
        squareJ.fillWith(Color.WHITE);
        squareJ.setPosition((width / 2) - (squareJ.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - squareJ.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJXJ = AppearTransitionJX.asFadeIn(10700, 400, 0, 1, squareJ);

        ShapeJX squareK = ShapeJX.fromSquare(5);
        squareK.fillWith(Color.BLACK);
        squareK.setPosition((width / 2) - (squareK.getDynamicCanvas().getBoundsInLocal().getWidth() / 2),
                (height / 2) - squareK.getDynamicCanvas().getBoundsInLocal().getHeight() / 2);

        TransitionJX transitionJXK = AppearTransitionJX.asFadeIn(10950, 150, 0, 1, squareK);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddAnimatedObject(transitionJXA);
        sequenceJX.AddAnimatedObject(transitionJXB);
        sequenceJX.AddAnimatedObject(transitionJXC);
        sequenceJX.AddAnimatedObject(transitionJXD);
        sequenceJX.AddAnimatedObject(transitionJXE);
        sequenceJX.AddAnimatedObject(transitionJXF);
        sequenceJX.AddAnimatedObject(transitionJXG);
        sequenceJX.AddAnimatedObject(transitionJXH);
        sequenceJX.AddAnimatedObject(transitionJXI);
        sequenceJX.AddAnimatedObject(transitionJXJ);
        sequenceJX.AddAnimatedObject(transitionJXK);

        sequenceJX.play();

        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }

    @Test
    public void squareShouldAppearFrom45Degrees(){
        double width = 600;
        double height = 600;

        ShapeJX square = ShapeJX.fromSquare(300);
        square.fillWith(Color.RED);

        TransitionJX transitionJX = AppearTransitionJX.appearFromAngle(500, 5000, 45,
                width / 2,  height / 2, square);

        SequenceJX sequenceJX = new SequenceJX();
        sequenceJX.AddAnimatedObject(transitionJX);
        sequenceJX.play();

        Application.launch(AnimationJX.class, "--width=" + Double.toString(width), "--height=" + Double.toString(height));
    }
}
