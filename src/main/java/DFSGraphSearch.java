import java.util.Stack;

public class DFSGraphSearch extends GraphSearchTemplate {
    private Stack<String> stack = new Stack<>();

    public DFSGraphSearch(Graph graph) {
        super(graph);
    }

    @Override
    protected boolean hasNodes() {
        return !stack.isEmpty();
    }

    @Override
    protected String getNextNode() {
        return stack.pop();
    }

    @Override
    public void addNode(String node) {
        stack.push(node);
    }
}
