import javafx.scene.Node;
import javafx.scene.layout.Pane;

public final class SceneController {

    private static final Pane rootView;

    static {
        rootView = new Pane();
    }

    protected static Pane getRootView() {
        return rootView;
    }

    protected static void addView(Node view){
        rootView.getChildren().add(view);
    }

    public static void setBackgroundColor(String color){
        rootView.setStyle("-fx-background-color: " + color + ";");
    }

    public static void setBackgroundColor(int r, int g, int b){
        String rgb = ColorUtils.rgbToHex(r, g, b);
        rootView.setStyle("-fx-background-color: " + rgb + ";");
    }
}
