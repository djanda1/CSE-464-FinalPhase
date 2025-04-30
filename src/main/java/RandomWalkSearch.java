import java.util.*;

public class RandomWalkSearch extends GraphSearchTemplate {
    private Random random = new Random();
    private String currentNode;
    private List<String> path = new ArrayList<>(); // Track the full path

    public RandomWalkSearch(Graph graph) {
        super(graph);
    }

    @Override
    protected void addNode(String node) {
        currentNode = node;
        path.add(node); // Store visited node
    }

    @Override
    protected String getNextNode() {
        List<String> neighbors = graph.getNeighbors(currentNode);
        if (neighbors.isEmpty()) {
            return null; // No valid move
        }
        currentNode = neighbors.get(random.nextInt(neighbors.size())); // Pick a random neighbor
        path.add(currentNode); // Append to path
        return currentNode;
    }

    @Override
    protected boolean hasNodes() {
        return currentNode != null;
    }

    @Override
    public Path search(String src, String dst) {
        Path result = super.search(src, dst);
        System.out.println("Random Walk Path: " + path); // Print full traversal sequence
        return result;
    }
}
