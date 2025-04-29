import java.util.*;
public class Edge {
    String source;
    String destination;

    Edge(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String toString() {
        return source + " -> " + destination;
    }

}
