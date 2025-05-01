import java.util.*;


public abstract class GraphSearchTemplate {
    protected Graph graph;
    protected Map<String, String> parents = new HashMap<>();
    protected Set<String> visited = new HashSet<>();

    public GraphSearchTemplate(Graph graph) {
        this.graph = graph;
    }

    protected abstract void addNode(String node);
    protected abstract String getNextNode();
    protected abstract boolean hasNodes();

    public Path search(String src, String dst) {
        if (!graph.containsNode(src) || !graph.containsNode(dst)) {
            return null;
        }

        addNode(src);
        parents.put(src, null); // Set up root
        String current = "";
        while (hasNodes()) {
            current = getNextNode();
            if (current == null) {
                return null;
            }

            if (!visited.contains(current)) {
                visited.add(current); // Mark as visited when exploring (popped)

                if (current.equals(dst)) {
                    Path path = reconstructPath(dst);
                    path.setFound(true); // Mark path as found
                    return path;
                }

                List<String> neighbors = graph.getNeighbors(current);
                for (String neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        parents.put(neighbor, current);
                        addNode(neighbor);
                    }
                }
            }
        }
        return reconstructPath(current); // No path found
    }

    private Path reconstructPath(String dst) {
        List<String> path = new ArrayList<>();
        String node = dst;
        while(node != null) {
            path.add(node);
            node = parents.get(node);
        }
        Collections.reverse(path);
        return new Path(path);
    }
}
