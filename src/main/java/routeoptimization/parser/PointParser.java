package routeoptimization.parser;

import routeoptimization.Point;

/**
 * Created by Евгений on 27.05.2017.
 */
public class PointParser {
    public static Point parsePoint(String point) {
        String[] latLng = point.split(",");
        return new Point(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1]));
    }
}
