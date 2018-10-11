import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class ShapeJX implements ObjJX {

    /******************************
     *         Properties         *
     ******************************/

    private DynamicCanvas dynamicCanvas;
    private GraphicsContext gfx;
    private DrawMethod drawMethod;

    private ArrayList<Point> points;

    private Point centerPoint;
    private double width;
    private double height;
    private double rotation;
    private double cornerArc;

    private double offsetX;
    private double offsetY;

    private ShapeJX(){}

    /******************************
     *         Constructors       *
     ******************************/

    public static ShapeJX fromSquare(double length){
        return fromRect(length, length);
    }

    public static ShapeJX fromRect(double width, double height){
        ShapeJX shapeJX = new ShapeJX();
        shapeJX.drawMethod = DrawMethod.POLYGON;

        shapeJX.dynamicCanvas = new DynamicCanvas(width, height);
        shapeJX.gfx = shapeJX.dynamicCanvas.getGraphicsContext2D();

        shapeJX.points = new ArrayList<>();
        shapeJX.points.add(new Point(1, 1));
        shapeJX.points.add(new Point(width + 1, 1));
        shapeJX.points.add(new Point(width + 1, height + 1));
        shapeJX.points.add(new Point(1, height + 1));

        shapeJX.dynamicCanvas = new DynamicCanvas(width + 2,
                height + 2); // + 2 to account for line width
        shapeJX.gfx = shapeJX.dynamicCanvas.getGraphicsContext2D();

        shapeJX.toDefaultVals();
        shapeJX.draw();
        return shapeJX;
    }

    public static ShapeJX fromRoundedTriangle(double x1, double y1, double x2, double y2, double x3, double y3, double roundedRatioPart, double straightRatioPart){
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
        ShapeJX shapeJX = new ShapeJX();

        DynamicCanvas canvas = new DynamicCanvas(radius * 2, radius * 2);
        GraphicsContext gfx = canvas.getGraphicsContext2D();
        gfx.setStroke(Color.BLACK);
        gfx.strokeOval(0, 0, radius, radius);

        shapeJX.dynamicCanvas = canvas;
        shapeJX.toDefaultVals();
        return shapeJX;
    }

    public static ShapeJX fromTriangle(double x1, double y1, double x2, double y2, double x3, double y3){
        ShapeJX shapeJX = new ShapeJX();
        shapeJX.drawMethod = DrawMethod.POLYGON;

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

        shapeJX.points = new ArrayList<>();
        shapeJX.points.add(new Point(x1 - minX + 1, y1 - minY + 1));
        shapeJX.points.add(new Point(x2 - minX + 1, y2 - minY + 1));
        shapeJX.points.add(new Point(x3 - minX + 1, y3 - minY + 1));

        shapeJX.dynamicCanvas = new DynamicCanvas(maxX - minX + 2,
                maxY - minY + 2); // + 2 to account for line width
        shapeJX.gfx = shapeJX.dynamicCanvas.getGraphicsContext2D();

        shapeJX.toDefaultVals();
        shapeJX.draw();
        return shapeJX;
    }

    /******************************
     *         General            *
     ******************************/

    public void draw() {
        switch (drawMethod) {
            case POLYGON:
                gfx.clearRect(0, 0, dynamicCanvas.getWidth(), dynamicCanvas.getHeight());
                gfx.fillPolygon(ArrayUtils.toPrimitive(getXVals()), ArrayUtils.toPrimitive(getYVals()), points.size());
                gfx.strokePolygon(ArrayUtils.toPrimitive(getXVals()), ArrayUtils.toPrimitive(getYVals()), points.size());
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

    public void toDefaultVals(){
        this.gfx.setFill(Color.TRANSPARENT);
        this.gfx.setStroke(Color.BLACK);
        this.dynamicCanvas.setLayoutX(0);
        this.dynamicCanvas.setLayoutY(0);

        double minX = GenUtils.getMinValue(Arrays.asList(getXVals()));
        double minY = GenUtils.getMinValue(Arrays.asList(getYVals()));
        double maxX = GenUtils.getMaxValue(Arrays.asList(getXVals()));
        double maxY = GenUtils.getMaxValue(Arrays.asList(getYVals()));

        this.rotation = 0;
        this.setCenter();
        this.width = maxX - minX;
        this.height = maxY - minY;
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

    /******************************
     *         Accessors          *
     ******************************/

    private Double[] getXVals() {
        Double[] xVals = new Double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xVals[i] = points.get(i).getX() + offsetX;
        }
        return xVals;
    }

    private Double[] getYVals() {
        Double[] yVals = new Double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            yVals[i] = points.get(i).getY() + offsetY;
        }
        return yVals;
    }

    public void setPosition(double x, double y){
        dynamicCanvas.setLayoutX(x);
        dynamicCanvas.setLayoutY(y);
    }

    private void setCenter() {
        double maxX = GenUtils.getMaxValue(Arrays.asList(getXVals()));
        double maxY = GenUtils.getMaxValue(Arrays.asList(getYVals()));
        double minX = GenUtils.getMinValue(Arrays.asList(getXVals()));
        double minY = GenUtils.getMinValue(Arrays.asList(getYVals()));

        centerPoint = new Point(maxX - ((maxX - minX) / 2) - offsetX, maxY - ((maxY - minY) / 2) - offsetY);
    }

    public GraphicsContext getGfx() {
        return gfx;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public DynamicCanvas getDynamicCanvas(){
        return dynamicCanvas;
    }

    /******************************
     *          Overrides         *
     ******************************/

    @Override
    public Node getNode() {
        return dynamicCanvas;
    }

    @Override
    public Point offsetPoint(Point point) {
        return point; // todo
    }

    /******************************
     *           Inner            *
     ******************************/

    enum DrawMethod {
        CIRCLE,
        POLYGON,
        ROUNDED_POLYGON
    }
}
