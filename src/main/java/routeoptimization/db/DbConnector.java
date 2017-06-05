package routeoptimization.db;

import org.neo4j.driver.v1.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import routeoptimization.graph.Graph;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * Created by Евгений on 30.05.2017.
 */

@Component
@PropertySource("classpath:resources.properties")
public class DbConnector {

    @Value("${db.url}")
    private String dbUri;

    @Value("${db.username}")
    private String dbLogin;

    @Value("${db.password}")
    private String dbPassword;

    private Driver driver;
    private Session session;
    private Graph subwayGraph;

    @PostConstruct
    private void postConstruct() {
        driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbLogin, dbPassword));
        session = driver.session();
    }

    public Graph getSubwayGraph(String city) {
        if (subwayGraph == null) {
            subwayGraph = getGraph(city.toUpperCase() + "_SUBWAY");
        }
        return subwayGraph;
    }

    private Graph getGraph (String prefix) {
        StatementResult result = session.run(String.format("MATCH (n) WHERE any(l IN labels(n) WHERE l=~'%s.*') " +
                "RETURN n.latitude as latitude, n.longitude as longitude, labels(n) as label", prefix));
        List<String[]> nodes = new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            nodes.add(new String[]{record.get("label").get(0).asString(), String.valueOf(record.get("latitude").asDouble()),
                    String.valueOf(record.get("longitude").asDouble())});
        }

        result = session.run(String.format("MATCH p=(a)-[r]->(b) WHERE type(r)=~'%s.*' RETURN r.time as time, labels(a) " +
                "as startLabel, labels(b) as endLabel", prefix));
        List<String[]> relations = new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            relations.add(new String[]{record.get("startLabel").get(0).asString(), record.get("endLabel").get(0).asString(),
                    String.valueOf(record.get("time").asInt())});
        }

        int[][] distances = new int[nodes.size()][nodes.size()];

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                distances[i][j] = findDistance(nodes.get(i)[0], nodes.get(j)[0], relations);
            }
        }
        session.close();
        return new Graph(nodes, relations, distances);
    }

    private int findDistance(String station1, String station2, List<String[]> relations) {
        if (station1.equals(station2)) {
            return 0;
        }

        for (String[] relation : relations) {
            if (relation[0].equals(station1) && relation[1].equals(station2) || relation[1].equals(station1) && relation[0].equals(station2)) {
                String time = relation[2];
                return Integer.valueOf(time);
            }
        }
        return Integer.MAX_VALUE;
    }
}
