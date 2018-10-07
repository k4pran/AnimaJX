public class GeomUtils {

    public static Point interpolateRoundedCorners(Point p1, Point p2, double roundFactor){
        return new Point((int) Math.round(p1.getX() * (1 - roundFactor) + p2.getX() * roundFactor),
                         (int) Math.round(p1.getY() * (1 - roundFactor) + p2.getY() * roundFactor));
    }
}
