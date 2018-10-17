import java.util.ArrayList;

public class Rotator {

    public static void standardRotation(ArrayList<Point> points, double degrees, Point pivotPoint) {
        degrees = degrees * Math.PI / 180;
        for (Point point : points) {
            double x = point.getX();
            double y = point.getY();

            x = x - pivotPoint.getX();
            y = y - pivotPoint.getY();

            double xNew = (x * Math.cos(degrees)) - (y * Math.sin(degrees));
            double yNew = (x * Math.sin(degrees)) + (y * Math.cos(degrees));

            point.setX(xNew + pivotPoint.getX() + 18); // todo why do I need to offset these?
            point.setY(yNew + pivotPoint.getY() + 19);
        }
        System.out.println();
    }
}
