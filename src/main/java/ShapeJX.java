import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ShapeJX implements ObjJX {

    private DynamicCanvas dynamicCanvas;
    private GraphicsContext gfx;
    private DrawMethod drawMethod;

    private ArrayList<Point> points;

    private double width;
    private double height;
    private double rotation;
    private double cornerArc;

    private ShapeJX(){};

    public static ShapeJX fromSquare(double length){
        return fromRect(length, length);
    }

    public static ShapeJX fromRect(double width, double height){
        ShapeJX animShape = new ShapeJX();
        animShape.drawMethod = DrawMethod.POLYGON;

        animShape.dynamicCanvas = new DynamicCanvas(width, height);
        animShape.gfx = animShape.dynamicCanvas.getGraphicsContext2D();

        animShape.points = new ArrayList<>();
        animShape.points.add(new Point(1, 1));
        animShape.points.add(new Point(width + 1, 1));
        animShape.points.add(new Point(width + 1, height + 1));
        animShape.points.add(new Point(1, height + 1));

        animShape.dynamicCanvas = new DynamicCanvas(width + 2,
                height + 2); // + 2 to account for line width
        animShape.gfx = animShape.dynamicCanvas.getGraphicsContext2D();

        animShape.toDefaultVals();
        animShape.draw();
        return animShape;
    }

    public static ShapeJX fromRoundedTriangle(double x1, double y1, double x2, double y2, double x3, double y3,
                                              double roundedRatioPart, double straightRatioPart){
        ShapeJX shapeJX = fromTriangle(x1, y1, x2, y2, x3, y3);
        shapeJX.drawMethod = DrawMethod.ROUNDED_POLYGON;
        shapeJX.cornerArc = roundedRatioPart / straightRatioPart;

        shapeJX.toDefaultVals();
        shapeJX.draw();
        return shapeJX;
    }

    public static ShapeJX fromPolygon(ArrayList<Point> points){
        ShapeJX shapeJX = new ShapeJX();
        shapeJX.drawMethod = DrawMethod.POLYGON;
        shapeJX.points = points;

        ArrayList<Double> xVals = new ArrayList<>();
        ArrayList<Double> yVals = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            xVals.add(points.get(i).getX());
            yVals.add(points.get(i).getY());
        }


        double minX = GenUtils.getMinValue(xVals);
        double minY = GenUtils.getMinValue(yVals);
        double maxX = GenUtils.getMaxValue(xVals);
        double maxY = GenUtils.getMaxValue(yVals);

        shapeJX.dynamicCanvas = new DynamicCanvas(maxX - minX + 2, maxY - minY + 2);
        shapeJX.gfx = shapeJX.dynamicCanvas.getGraphicsContext2D();

        shapeJX.toDefaultVals();
        shapeJX.draw();
        return shapeJX;
    }


    public static ShapeJX fromCircle(double radius){
        ShapeJX animShape = new ShapeJX();
//
//        DynamicCanvas canvas = new DynamicCanvas(radius * 2, radius * 2);
//        GraphicsContext gfx = canvas.getGraphicsContext2D();
//        gfx.setStroke(Color.BLACK);
//        gfx.strokeOval(0, 0, radius, radius);
//
//        animShape.dynamicCanvas = canvas;
//        animShape.toDefaultVals();
        return animShape;
    }

    public static ShapeJX fromTriangle(double x1, double y1, double x2, double y2, double x3, double y3){
        ShapeJX animShape = new ShapeJX();
        animShape.drawMethod = DrawMethod.POLYGON;

        ArrayList<Double> xVals = new ArrayList<Double>(){{
            add(x1);
            add(x2);
            add(x3);
        }};
        ArrayList<Double> yVals = new ArrayList<Double>(){{
            add(y1);
            add(y2);
            add(y3);
        }};

        double minX = GenUtils.getMinValue(xVals);
        double minY = GenUtils.getMinValue(yVals);
        double maxX = GenUtils.getMaxValue(xVals);
        double maxY = GenUtils.getMaxValue(yVals);

        animShape.points = new ArrayList<>();
        animShape.points.add(new Point(x1 - minX + 1, y1 - minY + 1));
        animShape.points.add(new Point(x2 - minX + 1, y2 - minY + 1));
        animShape.points.add(new Point(x3 - minX + 1, y3 - minY + 1));

        animShape.dynamicCanvas = new DynamicCanvas(maxX - minX + 2,
                                                    maxY - minY + 2); // + 2 to account for line width
        animShape.gfx = animShape.dynamicCanvas.getGraphicsContext2D();

        animShape.toDefaultVals();
        animShape.draw();
        return animShape;
    }

    private void draw() {
        switch (drawMethod) {
            case POLYGON:
                gfx.clearRect(0, 0, dynamicCanvas.getWidth(), dynamicCanvas.getHeight());
                gfx.fillPolygon(getXVals(), getYVals(), points.size());
                gfx.strokePolygon(getXVals(), getYVals(), points.size());
                break;

            case ROUNDED_POLYGON:
                gfx.clearRect(0, 0, dynamicCanvas.getWidth(), dynamicCanvas.getHeight());
                drawRoundedCorners();
                gfx.stroke();
                break;

        }
    }

    public void drawRoundedCorners() {
        ArrayList<Point> interpolated = new ArrayList<>();
        int plus1Index = 1;
        // Linear interpolation
        for (int i = 0; i < points.size(); i++) {
            interpolated.add(GeomUtils.interpolateRoundedCorners(points.get(i), points.get(plus1Index % points.size()), cornerArc));
            interpolated.add(GeomUtils.interpolateRoundedCorners(points.get(i), points.get(plus1Index % points.size()), 1 - cornerArc));
            plus1Index++;
        }

        // Draw straight lines
        for (int i = 1; i < interpolated.size(); i += 2) {
            gfx.moveTo(interpolated.get(i - 1).getX(), interpolated.get(i - 1).getY());
            gfx.lineTo(interpolated.get(i).getX(), interpolated.get(i).getY());
        }

        // Draw curved corners
        int moveToInd = interpolated.size() - 1;
        for (int i = 0; i < points.size(); i++) {
            gfx.moveTo(interpolated.get(moveToInd % interpolated.size()).getX(),
                    interpolated.get(moveToInd % interpolated.size()).getY());
            gfx.quadraticCurveTo(points.get(i).getX(), points.get(i).getY(),
                    interpolated.get(i * 2).getX(), interpolated.get(i * 2).getY());
            moveToInd += 2;
        }
    }

    private double[] getXVals() {
        double[] xVals = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xVals[i] = points.get(i).getX();
        }
        return xVals;
    }

    private double[] getYVals() {
        double[] yVals = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            yVals[i] = points.get(i).getY();
        }
        return yVals;
    }

    public GraphicsContext getGfx() {
        return gfx;
    }

    public void toDefaultVals(){
        gfx.setFill(Color.TRANSPARENT);
        gfx.setStroke(Color.BLACK);
        dynamicCanvas.setLayoutX(0);
        dynamicCanvas.setLayoutY(0);
    }

    public void pointScale(double scaleFactor) {
        for (int i = 0; i < points.size(); i++) {
            points.get(i).setX(points.get(i).getX() * scaleFactor);
            points.get(i).setY(points.get(i).getY() * scaleFactor);
        }
        dynamicCanvas.setWidth(dynamicCanvas.getWidth() * scaleFactor);
        dynamicCanvas.setHeight(dynamicCanvas.getHeight() * scaleFactor);
        draw();
    }

    public void fillWith(Color color) {
        gfx.setFill(color);
        draw();
    }

    public void strokeWith(Color color) {
        gfx.setStroke(color);
        draw();
    }

    public void disableFill() {
        gfx.setFill(Color.TRANSPARENT);
        draw();
    }

    public void disableStroke() {
        gfx.setStroke(Color.TRANSPARENT);
        draw();
    }

    public void setPosition(double x, double y){
        dynamicCanvas.setLayoutX(x);
        dynamicCanvas.setLayoutY(y);
    }

    public Node getNode() {
        return dynamicCanvas;
    }

    public DynamicCanvas getDynamicCanvas(){
        return dynamicCanvas;
    }


    enum DrawMethod {
        CIRCLE,
        POLYGON,
        ROUNDED_POLYGON
    }
}
