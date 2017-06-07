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
 * Created by Евгений on 07.06.2017.
 */
@Component("busRouteComputer")
public class RouteComputerBusImpl extends RouteComputerAbstract implements RouteComputer {

    @Autowired
    private DbConnector dbConnector;

    public List<Point> computeRoute(Point start, Point end, String city) {
        List<Graph> busGraphs = dbConnector.getBusGraphs(city);

        int graphIndex = 0;

        start = getNearestPoint(start, busGraphs.get(graphIndex).getNodes());
        end = getNearestPoint(end, busGraphs.get(graphIndex).getNodes());

        if (dbConnector.getBusGraphIndex(graphIndex, PointIndexConverter.convertPointToIndex(start, busGraphs.get(graphIndex).getNodes())) >
                dbConnector.getBusGraphIndex(graphIndex, PointIndexConverter.convertPointToIndex(end, busGraphs.get(graphIndex).getNodes()))) {
            graphIndex = 1;
            start = getNearestPoint(start, busGraphs.get(graphIndex).getNodes());
            end = getNearestPoint(end, busGraphs.get(graphIndex).getNodes());
        }

        List<Integer> pathIndexes = busGraphs.get(graphIndex).getPath(PointIndexConverter.convertPointToIndex(start, busGraphs.get(graphIndex).getNodes()), PointIndexConverter.convertPointToIndex(end, busGraphs.get(graphIndex).getNodes()));
        List<Point> result = new ArrayList<>();
        for (int index : pathIndexes) {
            result.add(new Point(Double.valueOf(busGraphs.get(graphIndex).getNodes().get(index)[1]), Double.valueOf(busGraphs.get(graphIndex).getNodes().get(index)[2])));
        }
        return result;
    }
}
