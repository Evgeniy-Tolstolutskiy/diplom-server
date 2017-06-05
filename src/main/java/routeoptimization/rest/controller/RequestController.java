package routeoptimization.rest.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import routeoptimization.Point;
import routeoptimization.routecomputation.RouteComputer;
import routeoptimization.routecomputation.RouteComputerFactory;
import routeoptimization.parser.PointParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Евгений on 27.05.2017.
 */

@RestController
public class RequestController {

    @Autowired
    private RouteComputerFactory factory;

    @RequestMapping("/computeRoute")
    public String computeRoute(@RequestParam(value="start") String start, @RequestParam(value="end") String end,
                                    @RequestParam(value="transport") String transport, @RequestParam(value="city") String city) {
        RouteComputer routeComputer = factory.createComputer(transport);
        List<Point> points = routeComputer.computeRoute(PointParser.parsePoint(start), PointParser.parsePoint(end), city);
        return new Gson().toJson(points);
    }
}
