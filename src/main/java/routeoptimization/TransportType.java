package routeoptimization;

/**
 * Created by Евгений on 27.05.2017.
 */
public enum TransportType {
    SUBWAY {
        @Override
        public String toString() {
            return "subway";
        }
    },
    BUS {
        @Override
        public String toString() {
            return "bus";
        }
    }
}
