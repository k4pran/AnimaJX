import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextJX implements ObjJX {

    private VBox wrapper;
    private Text text;

    private TextJX(){}

    public static TextJX SimpleText(String text){
        TextJX textJX = new TextJX();
        textJX.text = new Text(0, 0, text);
        textJX.text.setFont(new Font(15));
        textJX.text.setFill(Color.BLUE);
        textJX.wrapper = new VBox(textJX.text);
        return textJX;
    }

    public VBox getText() {
        return wrapper;
    }

    public Node getNode() {
        return wrapper;
    }
}
