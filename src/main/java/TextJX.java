import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextJX implements ObjJX {

    private Canvas dynamicCanvas;
    private GraphicsContext gfx;
    private double yOffset;

    private TextJX(){}

    public static TextJX SimpleText(String text){
        TextJX textJX = new TextJX();
        textJX.dynamicCanvas = new DynamicCanvas(0, 0);
        textJX.gfx = textJX.dynamicCanvas.getGraphicsContext2D();
        textJX.gfx.setStroke(Color.BLACK);

        FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(textJX.gfx.getFont());
        textJX.dynamicCanvas.setWidth(fm.computeStringWidth(text));
        textJX.dynamicCanvas.setHeight(fm.getAscent() - fm.getDescent() + textJX.gfx.getLineWidth());
        textJX.yOffset = fm.getAscent() - fm.getDescent();
        textJX.gfx.strokeText(text, 0, textJX.yOffset);
        return textJX;
    }

    public Canvas getDynamicCanvas() {
        return dynamicCanvas;
    }

    public Node getNode() {
        return dynamicCanvas;
    }

    @Override
    public Point offsetPoint(Point point) {
        point.setY(point.getY() + yOffset);
        return point;
    }
}
