import javafx.animation.Transition;
import javafx.util.Duration;

import java.util.ArrayList;

public class PointScaleTransition extends Transition {

    private ShapeJX shapeJX;
    private ArrayList<Point> points;
    private ArrayList<Point> scaledPoints;
    private double scaleFactor;
    private ScaleDirection scaleDirection;
    private double lastFrac;

    public PointScaleTransition(double targetFramerate, ShapeJX shapeJX, ArrayList<Point> points, double scaleFactor, double duration) {
        super(targetFramerate);
        this.shapeJX = shapeJX;
        this.points = points;
        this.scaleFactor = scaleFactor;
        this.scaleDirection = ScaleDirection.NORTH_WEST;
        this.lastFrac = 0;
        setCycleDuration(Duration.millis(duration));

        scaledPoints = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            scaledPoints.add(new Point(points.get(i).getX() * scaleFactor, points.get(i).getY() * scaleFactor));
        }
        shapeJX.getDynamicCanvas().setWidth(shapeJX.getDynamicCanvas().getWidth() * scaleFactor);
        shapeJX.getDynamicCanvas().setHeight(shapeJX.getDynamicCanvas().getHeight() * scaleFactor);
    }

    public PointScaleTransition(ShapeJX shapeJX, ArrayList<Point> points, double scaleFactor, double duration) {
        this.shapeJX = shapeJX;
        this.points = points;
        this.scaleFactor = scaleFactor;
        this.scaleDirection = ScaleDirection.CENTER;
        this.lastFrac = 0;
        setCycleDuration(Duration.millis(duration));

        scaledPoints = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            scaledPoints.add(new Point(points.get(i).getX() * scaleFactor, points.get(i).getY() * scaleFactor));
        }
        shapeJX.getDynamicCanvas().setWidth(shapeJX.getDynamicCanvas().getWidth() * scaleFactor);
        shapeJX.getDynamicCanvas().setHeight(shapeJX.getDynamicCanvas().getHeight() * scaleFactor);
    }

    public ScaleDirection getScaleDirection() {
        return scaleDirection;
    }

    public void setScaleDirection(ScaleDirection scaleDirection) {
        this.scaleDirection = scaleDirection;
    }

    @Override
    protected void interpolate(double frac) {

        switch(scaleDirection) {

            case SOUTH_EAST:
                for (int i = 0; i < points.size(); i++) {
                    points.get(i).setX(scaledPoints.get(i).getX() * frac);
                    points.get(i).setY(scaledPoints.get(i).getY() * frac);
                }
                break;

            case NORTH_WEST:
                for (int i = 0; i < points.size(); i++) {
                    points.get(i).setX(scaledPoints.get(i).getX() * -frac);
                    points.get(i).setY(scaledPoints.get(i).getY() * -frac);
                }
                break;

            case SOUTH_WEST:
                for (int i = 0; i < points.size(); i++) {
                    points.get(i).setX(scaledPoints.get(i).getX() * -frac);
                    points.get(i).setY(scaledPoints.get(i).getY() * frac);
                }
                break;

            case NORTH_EAST:
                for (int i = 0; i < points.size(); i++) {
                    points.get(i).setX(scaledPoints.get(i).getX() * frac);
                    points.get(i).setY(scaledPoints.get(i).getY() * -frac);
                }
                break;

            case CENTER:
                Point centerPoint = shapeJX.getCenterPoint();

                for (int i = 0; i < points.size(); i++) {
                    double v2x = points.get(i).getX() - centerPoint.getX();
                    double v2y = points.get(i).getY() - centerPoint.getY();
                    double v2xScaled = v2x * (1 + ((scaleFactor - 1) * (frac - lastFrac)));
                    double v2yScaled = v2y * (1 + ((scaleFactor - 1) * (frac - lastFrac)));
                    points.get(i).setX(v2xScaled + centerPoint.getX());
                    points.get(i).setY(v2yScaled + centerPoint.getY());
                }
        }
        lastFrac = frac;
        shapeJX.draw();
    }

    public enum ScaleDirection {
        SOUTH_EAST,
        NORTH_WEST,
        CENTER,
        SOUTH_WEST,
        NORTH_EAST
    }
}
