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
        if(!graph.containsNode(src) || !graph.containsNode(dst)) {
            return null;
        }

        addNode(src);
        parents.put(src, null);         // set up root
        String current = null;
        while(hasNodes()) {
            current = getNextNode();

            while (hasNodes()) {
                current = getNextNode();

                if (current == null) {
                    System.out.println("Dead End");
                    return null; // Prevents null comparison
                }

                if (current.equals(dst)) {
                    return reconstructPath(dst);
                }
            }


            List<String> neighbors = graph.getNeighbors(current);
            for (String neighbor : neighbors) {
                if (!parents.containsKey(neighbor)) {
                    parents.put(neighbor, current);
                    addNode(neighbor);
                }
            }

        }
        return reconstructPath(current);
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
