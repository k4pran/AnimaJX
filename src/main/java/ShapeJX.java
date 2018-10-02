import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class ShapeJX implements ObjJX {

    private Shape shape;

    private ShapeJX(){};

    public static ShapeJX fromSquare(double length){
        ShapeJX animShape = new ShapeJX();
        animShape.shape = new Rectangle(length, length);
        animShape.toDefaultVals();
        return animShape;
    }

    public static ShapeJX fromRect(double width, double height){
        ShapeJX animShape = new ShapeJX();
        animShape.shape = new Rectangle(width, height);
        animShape.toDefaultVals();
        return animShape;
    }

    public static ShapeJX fromRoundRect(double width, double height, double arcWidth, double arcHeight){
        ShapeJX animShape = new ShapeJX();
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(arcWidth);
        rect.setArcHeight(arcHeight);
        animShape.shape = rect;
        animShape.toDefaultVals();
        return animShape;
    }

    public static ShapeJX fromCircle(double radius){
        ShapeJX animShape = new ShapeJX();
        animShape.shape = new Circle(radius);
        animShape.toDefaultVals();
        return animShape;
    }

    public static ShapeJX fromPolygon(double... points){
        ShapeJX animShape = new ShapeJX();
        animShape.shape = new Polygon(points);
        animShape.toDefaultVals();
        return animShape;
    }

    public static ShapeJX fromArc(double centerX, double centerY, double radiusX, double radiusY,
                                  double startAngle, double length){
        ShapeJX animShape = new ShapeJX();
        animShape.shape = new Arc(centerX, centerY, radiusX, radiusY, startAngle, length);
        animShape.toDefaultVals();
        return animShape;
    }

    public static ShapeJX fromEllipse(double radiusX, double radiusY){
        ShapeJX animShape = new ShapeJX();
        animShape.shape = new Ellipse(radiusX, radiusY);
        animShape.toDefaultVals();
        return animShape;
    }

    public void fillWith(Color color){
        shape.setFill(color);
    }

    public void strokeWith(Color color){
        shape.setStroke(color);
    }

    public void setStrokeWidth(double val){
        shape.setStrokeWidth(val);
    }

    public void toDefaultVals(){
        shape.setFill(Color.TRANSPARENT);
        shape.setStroke(Color.BLACK);
        shape.setLayoutX(0);
        shape.setLayoutY(0);
    }

    public void setPosition(double x, double y){
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    public Node getNode() {
        return shape;
    }

    public Shape getShape(){
        return shape;
    }

    public double getPosX(){
        if (shape instanceof Circle) {
            return shape.getLayoutX();
        }
        else if (shape instanceof Rectangle) {
            return shape.getLayoutX() + shape.getBoundsInLocal().getWidth() / 2;
        }
        else {
            return shape.getLayoutX();
        }
    }

    public double getPosY(){
        if (shape instanceof Circle) {
            return shape.getLayoutY();
        }
        else if (shape instanceof Rectangle) {
            return shape.getLayoutY() + (shape.getBoundsInLocal().getWidth() / 2);
        }
        else {
            return shape.getLayoutY();
        }
    }
}
