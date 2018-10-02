import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public class AnimationJX extends Application {

    private String title;
    private static float width = 800;
    private static float height = 600;

    @Override
    public void init() throws Exception {
        super.init();
        UnpackParams();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setScene(new Scene(SceneController.getRootView()));
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);

        primaryStage.show();
    }

    private void UnpackParams(){
        Map<String, String> params = getParameters().getNamed();

        this.title = params.containsKey("title") ? params.get("title") : "Untitled";

        if (params.containsKey("width")){
            try {
                float widthVal = Float.parseFloat(params.get("width"));
                width = widthVal;
            }
            catch (NumberFormatException e){
                System.out.println("Invalid width - reverting to default value");
            }
        }

        if (params.containsKey("height")){
            try {
                float heightVal = Float.parseFloat(params.get("height"));
                height = heightVal;
            }
            catch (NumberFormatException e){
                System.out.println("Invalid height - reverting to default value");
            }
        }
    }

    public static float getWidth() {
        return width;
    }

    public static float getHeight() {
        return height;
    }
}
