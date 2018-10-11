import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SequenceJX {

    private SequentialTransition sequence;
    Timeline timeline = new Timeline();

    public SequenceJX(){
        this.sequence = new SequentialTransition();
        this.sequence.getChildren().add(timeline);
    }

    public void AddStaticObject(ObjJX objJX, double timeToAppear, double timeToDisappear){
        objJX.getNode().setVisible(false);
        SceneController.addView(objJX.getNode());

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(timeToAppear),
                new KeyValue(objJX.getNode().visibleProperty(), true)));

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(timeToDisappear),
                new KeyValue(objJX.getNode().visibleProperty(), false)));
    }

    public void AddAnimatedObject(TransitionJX transitionJX){
        SceneController.addView(transitionJX.getNode());
        sequence.getChildren().add(transitionJX.getTransition());
    }

    public void play(){
        sequence.play();
    }
}
