import java.util.List;

public class Path {
    private List<String> nodes;
    private boolean found = false;

    public Path(List<String> nodes) {
        this.nodes = nodes;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public boolean isFound() {
        return found;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public String toString() {
        return String.join(" -> ", nodes);
    }
}
