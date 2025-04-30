import java.util.LinkedList;
import java.util.Queue;

public class BFSGraphSearch extends GraphSearchTemplate {
    private Queue<String> queue = new LinkedList<>();

    public BFSGraphSearch(Graph graph) {
        super(graph);
    }

    @Override
    protected void addNode(String node) {
        queue.offer(node);
    }

    @Override
    protected String getNextNode() {
        return queue.poll();
    }

    @Override
    protected boolean hasNodes() {
        return !queue.isEmpty();
    }
}
