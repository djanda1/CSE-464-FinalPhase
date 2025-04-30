import javax.swing.*;
import java.io.File;
import java.util.*;

public class Graph {
    Set<String> nodes;
    List<Edge> edges;
    int nodeCount = 0;
    Graph() {
        nodes = new HashSet<>();
        edges = new ArrayList<>();
    }
    public void addNode(String node) {
        if(!nodes.contains(node)) {
            nodes.add(node);
            nodeCount++;
        }
    }

    public void addEdge(Edge edge) {
        if(!edges.contains(edge))
            edges.add(edge);
    }

    public boolean removeNode(String node) {        //removes node and returns boolean of success
        if(nodes.contains(node)) {
            nodes.remove(node);
            nodeCount--;
            boolean result = removeEdge(node);
            return result;
        }
        else
            return false;
    }

    public int edgeCount() {
        return edges.size();
    }

    public int getNodeCount() {
        return nodes.size();
    }

    public boolean removeEdge(String node) {
        boolean result = false;
        for(int i = 0; i < edges.size(); i++) {
            if(edges.get(i).getSource().replaceAll("\\s", "").equals(node) || edges.get(i).getDestination().equals(node)) {
                edges.remove(i);
                result = true;
                i--;
            }
        }
        return result;       //return result of removing edge
    }
    public List<Edge> getEdges() {
        return edges;
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public boolean containsNode(String node) {
        return nodes.contains(node);
    }

    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of nodes: " + this.getNodeCount() + "\n");
        sb.append("Number of edges: " + edges.size() + "\n");
        sb.append("Node labels: " + nodes + "\n");
        sb.append("Edges:\n");
        for(int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            sb.append(edge.toString() + "\n");
        }
        return sb.toString();
    }

    public boolean removeSpecificEdge(String node1, String node2) {
        boolean result = false;
        for(int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            if(edge.getSource().equals(node1) && edge.getDestination().equals(node2)) {
                edges.remove(i);
                result = true;
            }
        }
        return result;
    }

    public Path BFSGraphSearch(String src, String dst) {       //BFS search
        if (!nodes.contains(src) || !nodes.contains(dst)) {
            return null;
        }
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();       //stores path from parent

        queue.add(src);
        parent.put(src, null);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(dst)) {       //if we reached destination
                List<String> path = new ArrayList<>();
                String node = dst;
                while (node != null) {
                    path.add(node);
                    node = parent.get(node);
                }
                Collections.reverse(path);
                return new Path(path);
            }

            for(int i = 0; i < edges.size(); i++) {     //explore edges
                String neighbor = null;

                Edge edge = edges.get(i);
                if(edge.getSource().equalsIgnoreCase(current))         //if edges source is the current node set neighbor to edges "child"
                    neighbor = edge.getDestination();
                if(neighbor != null && !parent.containsKey(neighbor)) {
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }

    public Path DFSGraphSearch(String src, String dst) {        //DFS search
        if(!nodes.contains(src) || !nodes.contains(dst)) {      //if src or dst doesn't exist in the graph
            return null;
        }

        Stack<String> stack = new Stack<>();
        Map<String, String> parents = new HashMap<>();
        Set<String> visited = new HashSet<>();

        stack.push(src);
        parents.put(src, null);
        visited.add(src);

        while(!stack.isEmpty()) {
            String current = stack.pop();

            if(current.equals(dst)) {       //if Dest is found collect the path and return
                List<String> path = new ArrayList<>();
                String node = dst;
                while(node != null) {
                    path.add(node);
                    node = parents.get(node);
                }
                Collections.reverse(path);
                return new Path(path);
            }

            for(Edge edge : edges)
                if(edge.getSource().equals(current)) {
                    String neighbor = edge.getDestination();

                    // If neighbor has not been checked
                    if(!visited.contains(neighbor)) {
                        visited.add(current);
                        parents.put(neighbor, current);
                        stack.push(neighbor);       // push deeper into stack since it is DFS
                    }
                }
        }

        return null;
    }
}
