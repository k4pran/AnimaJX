import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.commons.lang.ArrayUtils;
import sun.security.provider.SHA;

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

    private static double CANVAS_PADDING = 4;

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

        shapeJX.points = new ArrayList<>();
        shapeJX.points.add(new Point(CANVAS_PADDING / 2, CANVAS_PADDING / 2));
        shapeJX.points.add(new Point(width + CANVAS_PADDING / 2, CANVAS_PADDING / 2));
        shapeJX.points.add(new Point(width + CANVAS_PADDING / 2, height + CANVAS_PADDING / 2));
        shapeJX.points.add(new Point(CANVAS_PADDING / 2, height + CANVAS_PADDING / 2));

        shapeJX.dynamicCanvas = new DynamicCanvas(width + CANVAS_PADDING,
                height + CANVAS_PADDING); // + 2 to account for line width
        shapeJX.gfx = shapeJX.dynamicCanvas.getGraphicsContext2D();

        shapeJX.setOffsetX(-1);
        shapeJX.setOffsetY(-1);
        shapeJX.toDefaultVals();
        shapeJX.draw();
        return shapeJX;
    }

    public static ShapeJX fromRoundedTriangle(Point a, Point b, Point c, double roundedRatioPart, double straightRatioPart){
        ShapeJX shapeJX = fromTriangle(a, b, c);
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

        Point minPoint = shapeJX.getMinPoint();
        Point maxPoint = shapeJX.getMaxPoint();

        shapeJX.dynamicCanvas = new DynamicCanvas(maxPoint.getX() - minPoint.getX() + 2,
                                                  maxPoint.getY() - minPoint.getY() + 2);
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

    public static ShapeJX fromTriangle(Point a, Point b, Point c){
        ShapeJX shapeJX = new ShapeJX();
        shapeJX.drawMethod = DrawMethod.POLYGON;

        ArrayList<Double> xVals = new ArrayList<Double>(){{
            add(a.getX());
            add(b.getX());
            add(c.getX());
        }};
        ArrayList<Double> yVals = new ArrayList<Double>(){{
            add(a.getY());
            add(b.getY());
            add(c.getY());
        }};

        shapeJX.setOffsetX(1);
        shapeJX.setOffsetY(0);

        Point minPoint = shapeJX.getMinPoint();
        Point maxPoint = shapeJX.getMaxPoint();

        shapeJX.points = new ArrayList<>();
        shapeJX.points.add(new Point(a.getX() - minPoint.getX() + CANVAS_PADDING / 2, a.getY() - minPoint.getY() + CANVAS_PADDING / 2));
        shapeJX.points.add(new Point(b.getX() - minPoint.getX() + CANVAS_PADDING / 2, b.getY() - minPoint.getY() + CANVAS_PADDING / 2));
        shapeJX.points.add(new Point(c.getX() - minPoint.getX() + CANVAS_PADDING / 2, c.getY() - minPoint.getY() + CANVAS_PADDING / 2));

        shapeJX.dynamicCanvas = new DynamicCanvas(maxPoint.getX() - minPoint.getX() + CANVAS_PADDING,
                maxPoint.getY() - minPoint.getY() + CANVAS_PADDING ); // + 2 to account for line width
        shapeJX.gfx = shapeJX.dynamicCanvas.getGraphicsContext2D();

        shapeJX.getDynamicCanvas().setTranslateX(minPoint.getX() - 2);
        shapeJX.getDynamicCanvas().setTranslateY(minPoint.getY());
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

                Point maxPoint = getMaxPoint();
                Point minPoint = getMinPoint();

                dynamicCanvas.setWidth(maxPoint.getX() - minPoint.getX() + CANVAS_PADDING);
                dynamicCanvas.setHeight(maxPoint.getY() - minPoint.getY() + CANVAS_PADDING);

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

        Point maxPoint = getMaxPoint();
        Point minPoint = getMinPoint();

        this.rotation = 0;
        this.setCenter();
        this.width = maxPoint.getX() - minPoint.getX();
        this.height = maxPoint.getY() - minPoint.getY();
    }

    public void pointScale(double scaleFactor) {
        pointScaleX(scaleFactor);
        pointScaleY(scaleFactor);
    }

    public void pointScaleX(double scaleFactor) {
        double constOffset = points.get(0).getX() - points.get(0).getX() * scaleFactor;
        for (int i = 0; i < points.size(); i++) {
            points.get(i).setX(points.get(i).getX() * scaleFactor + constOffset);
        }
        dynamicCanvas.setWidth(dynamicCanvas.getWidth() * scaleFactor + ShapeJX.CANVAS_PADDING);
        draw();
    }

    public void pointScaleY(double scaleFactor) {
        double constOffset = points.get(0).getY() - points.get(0).getY() * scaleFactor;
        for (int i = 0; i < points.size(); i++) {
            points.get(i).setY(points.get(i).getY() * scaleFactor + constOffset);
        }
        dynamicCanvas.setHeight(dynamicCanvas.getHeight() * scaleFactor + ShapeJX.CANVAS_PADDING);
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
        dynamicCanvas.setLayoutX(x + getOffsetX());
        dynamicCanvas.setLayoutY(y + getOffsetY());
    }

    private void setCenter() {
        Point minPoint = getMinPoint();
        Point maxPoint = getMaxPoint();
        centerPoint = new Point(maxPoint.getX() - ((maxPoint.getX() - minPoint.getX()) / 2) - offsetX,
                                maxPoint.getY() - ((maxPoint.getY() - minPoint.getY()) / 2) - offsetY);
    }

    public Point getMaxPoint() {
        double maxX = GenUtils.getMaxValue(Arrays.asList(getXVals()));
        double maxY = GenUtils.getMaxValue(Arrays.asList(getYVals()));
        return new Point(maxX, maxY);
    }

    public Point getMinPoint() {
        double minX = GenUtils.getMinValue(Arrays.asList(getXVals()));
        double minY = GenUtils.getMinValue(Arrays.asList(getYVals()));
        return new Point(minX, minY);
    }

    public GraphicsContext getGfx() {
        return gfx;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public void setWidth(double width) {
        double scaleFactor = width / this.width;
        pointScaleX(scaleFactor);
        this.width = width;
    }

    public void setHeight(double height) {
        double scaleFactor = height / this.height;
        pointScaleY(scaleFactor);
        this.height = height;
        draw();
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
