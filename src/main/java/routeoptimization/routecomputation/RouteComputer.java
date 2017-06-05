package routeoptimization.routecomputation;

import routeoptimization.Point;

import java.util.List;

/**
 * Created by Евгений on 27.05.2017.
 */
public interface RouteComputer {
    List<Point> computeRoute(Point start, Point end, String city);
}
