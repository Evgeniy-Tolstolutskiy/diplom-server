package routeoptimization.routecomputation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import routeoptimization.Point;
import routeoptimization.converter.PointIndexConverter;
import routeoptimization.db.DbConnector;
import routeoptimization.graph.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Евгений on 27.05.2017.
 */
@Component("subwayRouteComputer")
public class RouteComputerSubwayImpl extends RouteComputerAbstract implements RouteComputer {

    @Autowired
    private DbConnector dbConnector;

    public List<Point> computeRoute(Point start, Point end, String city) {
        Graph graph = dbConnector.getSubwayGraph(city);
        start = getNearestPoint(start, graph.getNodes());
        end = getNearestPoint(end, graph.getNodes());
        List<Integer> pathIndexes = graph.getPath(PointIndexConverter.convertPointToIndex(start, graph.getNodes()), PointIndexConverter.convertPointToIndex(end, graph.getNodes()));
        List<Point> result = new ArrayList<>();
        for (int index : pathIndexes) {
            result.add(new Point(Double.valueOf(graph.getNodes().get(index)[1]), Double.valueOf(graph.getNodes().get(index)[2])));
        }
        return result;
    }


}
