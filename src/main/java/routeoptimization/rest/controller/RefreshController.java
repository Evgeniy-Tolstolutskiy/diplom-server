package routeoptimization.rest.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import routeoptimization.db.DbConnector;

/**
 * Created by Евгений on 07.06.2017.
 */

@RestController
public class RefreshController {

    @Autowired
    private DbConnector dbConnector;

    @RequestMapping("/refreshSubway")
    public String refreshSubway() {
        dbConnector.refreshSubway();
        return new Gson().toJson("subway was refreshed");
    }

    @RequestMapping("/refreshBus")
    public String refreshBus() {
        dbConnector.refreshBus();
        return new Gson().toJson("bus was refreshed");
    }
}
