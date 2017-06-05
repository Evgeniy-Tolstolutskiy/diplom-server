package routeoptimization.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Евгений on 01.06.2017.
 */
public class Graph {
    private List<String[]> nodes;
    private List<String[]> relations;
    private int[][] distances;
    private int[][] next;

    public Graph(List<String[]> nodes, List<String[]> relations, int[][] distances) {
        this.nodes = nodes;
        this.relations = relations;
        this.distances = distances;
        next = new int[nodes.size()][nodes.size()];
        this.distances = floydWarshall(nodes.size(), this.distances);
    }

    public List<String[]> getNodes() {
        return nodes;
    }

    public List<String[]> getRelations() {
        return relations;
    }

    public int[][] getDistances() {
        return distances;
    }

    private int[][] floydWarshall(int nodesCount, int[][] distances) {
        for (int i = 0; i < nodesCount; i++) {
            for (int j = 0; j < nodesCount; j++) {
                if (distances[i][j] != 0 && distances[i][j] != Integer.MAX_VALUE) {
                    next[i][j] = j;
                }
            }
        }

        for (int k = 0; k < nodesCount; k++) {
            for (int i = 0; i < nodesCount; i++) {
                for (int j = 0; j < nodesCount; j++) {
                    int oldValue = distances[i][j];
                    int newValue = sumWithInfinityCheck(distances[i][k], distances[k][j]);
                    if (oldValue > newValue) {
                        distances[i][j] = newValue;
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
        return distances;
    }

    public List<Integer> getPath(int start, int end) {
        List<Integer> result = new ArrayList<>();
        result.add(start);
        while (start != end) {
            start = next[start][end];
            result.add(start);
        }
        return result;
    }

    private int sumWithInfinityCheck(int a, int b) {
        if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return a + b;
    }
}















