import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;

public class Pixelizer {

    private static SnapshotParameters snapshotParameters;

    private ArrayList<Pixel> pixels;
    private WritableImage writableImage;
    private PixelReader pixelReader;

    private Pixelizer(){}

    static {
        snapshotParameters = new SnapshotParameters();
    }

    public static Pixelizer pixelize(ObjJX objJX) {
        Pixelizer pixelizer = new Pixelizer();
        pixelizer.pixels = new ArrayList<>();

        Node node = objJX.getNode();
        if (node instanceof DynamicCanvas) {
            DynamicCanvas dynamicCanvas = (DynamicCanvas) node;
            int width = (int) dynamicCanvas.getWidth();
            int height = (int) dynamicCanvas.getHeight();
            pixelizer.writableImage = new WritableImage(width, height);
            dynamicCanvas.snapshot(snapshotParameters, pixelizer.writableImage);

            pixelizer.pixelReader = pixelizer.writableImage.getPixelReader();

            int[] argbVals = new int[width * height];
            PixelFormat.Type formatType =  pixelizer.pixelReader.getPixelFormat().getType();
            if (formatType == PixelFormat.Type.INT_ARGB_PRE) {
                pixelizer.pixelReader.getPixels(
                        0,
                        0,
                        width,
                        height,
                        PixelFormat.getIntArgbPreInstance(),
                        argbVals,
                        0,
                        width
                );
            }
            else {
                pixelizer.pixelReader.getPixels(
                        0,
                        0,
                        width,
                        height,
                        PixelFormat.getIntArgbInstance(),
                        argbVals,
                        0,
                        width
                );
            }

            pixelizer.extractPixels(argbVals);
        }
        return pixelizer;
    }

    private void extractPixels(int[] argbVals) {
        for (int i = 0; i < argbVals.length; i++) {
            pixels.add(new Pixel(
                    (argbVals[i] >> 24) & 0xFF,
                    (argbVals[i] >> 16) & 0xFF,
                    (argbVals[i] >> 8) & 0xFF,
                    argbVals[i] & 0xFF
            ));
        }
    }

    public ArrayList<Pixel> getPixels() {
        return pixels;
    }

    public PixelReader getPixelReader() {
        return pixelReader;
    }
}
