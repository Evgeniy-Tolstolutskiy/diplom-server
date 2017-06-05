package routeoptimization.routecomputation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import routeoptimization.TransportType;

/**
 * Created by Евгений on 27.05.2017.
 */
@Component
public class RouteComputerFactory {

    @Autowired
    @Qualifier("subwayRouteComputer")
    private RouteComputer subwayRouteComputer;

    public RouteComputer createComputer(String transportType) {
        if (transportType.toLowerCase().equals(TransportType.SUBWAY.toString())) {
            return subwayRouteComputer;
        }
        return null;
    }
}
