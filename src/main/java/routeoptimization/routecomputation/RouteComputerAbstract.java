package routeoptimization.routecomputation;

import routeoptimization.Point;

import java.util.List;

/**
 * Created by Евгений on 01.06.2017.
 */
abstract class RouteComputerAbstract {
    Point getNearestPoint(Point point, List<String[]> nodes) {
        Point result = null;
        double distance = Double.MAX_VALUE;
        for (String[] node : nodes) {
            double newDistance = Math.sqrt(Math.pow((Double.valueOf(node[2]) - point.getY()), 2) + Math.pow((Double.valueOf(node[1]) - point.getX()), 2));
            if (newDistance < distance) {
                result = new Point(Double.valueOf(node[1]), Double.valueOf(node[2]));
                distance = newDistance;
            }
        }

        return result;
    }
}
