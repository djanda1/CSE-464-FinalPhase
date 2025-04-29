//David Janda CSE464 Project Phase 1
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.imageio.*;
public class GraphManager {

    private Graph graph;    //Graph that will be used when parsing dot object

    public void parseGraph(String filepath) throws IOException {
        File input = new File(filepath);
        graph = new Graph();
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            String line;
            boolean inFile = false;

            while ((line = br.readLine()) != null) {    //while lines still exist in dot file

                //skip empty lines or comments
                if (line.isEmpty() || line.startsWith("#") || line.startsWith("//") || line.startsWith("/*")) {
                    continue;
                }

                if (line.startsWith("digraph")) {
                    inFile = true;      //we are not inside the diagraph object
                    continue;
                }

                if (line.contains("}")) {
                    inFile = false;
                    continue;
                }

                if (inFile) {
                    //remove semicolon
                    line = line.replace(";", "");
                    Edge newEdge;
                    //parse edges and add to graph object
                    if (line.contains("->")) {
                        line.replaceAll("\\s+", "");
                        String[] parts = line.split(" -> ");      //remove whitespace and ->
                        String src = parts[0];
                        src.trim();
                        String dest = parts[1];
                        dest.trim();
                        /*if (!graph.containsNode(src)) {      //if graph doesn't contain nodes add them
                            graph.addNode(src);
                        }
                         */
                        if (!graph.containsNode(dest)) {
                            graph.addNode(dest);
                        }
                        newEdge = new Edge(src, dest);
                        if (!graph.containsEdge(newEdge)) {
                            graph.addEdge(newEdge);
                        }

                    }
                }


            }
            br.close();     //close reader
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return graph.toString();
    }

    public void outputGraph(String filepath) throws IOException {
        //open file and write toString for graph
        File input = new File(filepath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(input));
        bw.write(graph.toString());
        bw.close();     //close write
    }

    public Graph getGraph() {
        return graph;
    }

    public void addNode(String label) {
        graph.addNode(label);
    }

    public void addNodes(String[] label) {
        for (String node : label) {
            graph.addNode(node);
        }
    }

    public void addEdge(String srcLabel, String destLabel) {
        Edge newEdge = new Edge(srcLabel, destLabel);
        graph.addNode(srcLabel);
        graph.addNode(destLabel);
        if(!graph.containsEdge(newEdge)) {
            graph.addEdge(newEdge);
        }
    }

    public void outputDOTGraph(String path) {
        StringBuilder sb = new StringBuilder("diagraph {\n");
        List<Edge> edges = graph.getEdges();
        for(int i = 0; i < edges.size(); i++) {
            sb.append(edges.get(i).toString() + ";\n");
        }
        sb.append("}\n");
    }

    // output to png file
    public void outputGraphics(String path, String format) throws IOException {
        // create image
        int width = 400, height = 300;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);

        // set layout
        Map<String, Point> nodePositions = new HashMap<>();
        int radius = 100;
        int centerX = width / 2, centerY = height / 2;
        int nodeCount = graph.nodeCount();
        Set<String> nodes = graph.getNodes();
        Iterator<String> nodeIterator = nodes.iterator();
        String node = nodeIterator.next();

        // draw nodes
        for (int i = 0; i < nodeCount; i++) {
            double angle = 2 * Math.PI * i / nodeCount;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            nodePositions.put(node, new Point(x,y));
            g.fillOval(x - 10, y - 10, 20, 20);
            g.drawString(node, x - 5, y - 15);
            node = nodeIterator.next();
        }

        // connect edges
        List<Edge> edges = graph.getEdges();
        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            String src = edge.getSource();
            String dest = edge.getDestination();
            Point p1 = nodePositions.get(src);
            Point p2 = nodePositions.get(dest);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        g.dispose();
        if("png".equalsIgnoreCase(format)) {        // make png file if that's the format
            ImageIO.write(image, "png", new File(path));
        }

    }

    //Remove node function
    public void removeNode(String label) {
        if(graph.removeNode(label)) {
            System.out.println(label + " successfully removed");
        }
        else
            throw new RuntimeException(label + " does not exist or did not get removed correctly.");
    }

    //Remove node(s)
    public void removeNodes(String[] label) {
        for(int i = 0; i < label.length; i++) {
            removeNode(label[i]);
        }
    }

    //Remove Edge
    public void removeEdge(String srcLabel, String destLabel) {
        if(graph.removeSpecificEdge(srcLabel, destLabel))
            System.out.println(srcLabel + " -> " + destLabel + " edge successfully removed");
        else
            throw new RuntimeException(srcLabel + " -> " + destLabel + " edge does not exist or did not get removed correctly.");
    }

    public Path GraphSearch(String src, String dest, String algo) {
        if(algo.equalsIgnoreCase("BFS")) {
            Path path = graph.BFSGraphSearch(src, dest);
            if(path == null)
                throw new RuntimeException(src + " node doesn't exist or no path found.");
            else
                System.out.println(path.toString());
            return path;
        }
        else if(algo.equalsIgnoreCase("DFS")) {
            Path path = graph.DFSGraphSearch(src, dest);
            if(path == null)
                throw new RuntimeException(src + " node doesn't exist or no path found.");
            else
                System.out.println(path.toString());
            return path;
        }
        return null;
    }
}

