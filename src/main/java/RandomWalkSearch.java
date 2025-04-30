import java.util.List;
import java.util.Random;

public class RandomWalkSearch extends GraphSearchTemplate {
    private Random random = new Random();
    private String currentNode;

    public RandomWalkSearch(Graph graph) {
        super(graph);
    }

    @Override
    protected void addNode(String node) {
        currentNode = node;
    }

    @Override
    protected String getNextNode() {
        List<String> neighbors = graph.getNeighbors(currentNode);
        if(neighbors.isEmpty()) {
            return graph.getEdges().get(random.nextInt(graph.edgeCount())).getDestination();
        }
        currentNode = neighbors.get(random.nextInt(neighbors.size())); // Pick a random neighbor
        return currentNode;
    }

    @Override
    protected boolean hasNodes() {
        return currentNode != null;
    }

}
