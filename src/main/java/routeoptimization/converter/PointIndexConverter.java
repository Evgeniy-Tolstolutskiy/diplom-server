package routeoptimization.converter;

import routeoptimization.Point;

import java.util.List;

/**
 * Created by Евгений on 01.06.2017.
 */
public class PointIndexConverter {
    public static int convertPointToIndex(Point point, List<String[]> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            Point pointToCompare = new Point(Double.valueOf(nodes.get(i)[1]), Double.valueOf(nodes.get(i)[2]));
            if (pointToCompare.equals(point)) {
                return i;
            }
        }
        return -1;
    }
}
