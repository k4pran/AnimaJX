import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DynamicCanvas extends Canvas {

    public DynamicCanvas(double width, double height) {
        super(width, height);
        SHOW_OUTLINE();
    }

    // DEBUGGING ONLY
    public void SHOW_OUTLINE() {
        GraphicsContext g = this.getGraphicsContext2D();
        g.setFill(new Color(0, 1, 0, 0.3));
        g.fillRect(0, 0, getWidth(), getHeight());

    }

    @Override
    public boolean isResizable() {
        return true;
    }
    @Override
    public double prefWidth(double height) {
        return getWidth();
    }
    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
