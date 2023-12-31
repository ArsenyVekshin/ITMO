package ArsenyVeksin.lab4.backend.utils;

public class AreaCheck {
    public static boolean isHit(double x, double y, double r) {
        return isCircleHit(x, y, r) || isRectangleHit(x, y, r) || isTriangleHit(x, y, r);
    }

    private static boolean isTriangleHit(double x, double y, double r) {
        return (x >= 0 && y >= 0) && (y + x <= r);
    }

    private static boolean isRectangleHit(double x, double y, double r) {
        return (x >= 0 && y <= 0) && (x <= r && y >= -1 * r);
    }

    private static boolean isCircleHit(double x, double y, double r) {
        return (x <= 0 && y <= 0) && (x*x + y*y <= r*r);
    }
}
