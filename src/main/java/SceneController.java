import javafx.scene.layout.Pane;

public class SceneController {

    private static Pane rootView;

    static {
        rootView = new Pane();
    }

    public static Pane getRootView(){
        return rootView;
    }

    public static void setBackgroundColor(String color){
        rootView.setStyle("-fx-background-color: " + color + ";");
    }

    public static void setBackgroundColor(int r, int g, int b){
        String rgb = ColorUtils.rgbToHex(r, g, b);
        rootView.setStyle("-fx-background-color: " + rgb + ";");
    }
}
