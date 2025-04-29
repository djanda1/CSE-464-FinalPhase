import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class    GraphManagerTest {
    static GraphManager gm;

    @Before    // create graph manager with test input
    public void startup() {
        gm = new GraphManager();
        String graphCont = "digraph {\na -> b;\nb -> c;\nc -> d;\nd -> a;\na -> e\ne -> f\ne -> g\nf -> h\ng -> h\n}";
        try{
            Files.write(Paths.get("test.dot"), graphCont.getBytes());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        try {
            gm.parseGraph("test.dot");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Test Feature 1
    @Test
    public void testFeature1() {

        try {
            // asses
            //Test graph parse
            assertEquals(8, gm.getGraph().getNodeCount());
            assertEquals(9, gm.getGraph().edgeCount());

            //test toString
            assertTrue(gm.toString().contains("a -> b"));
            assertTrue(gm.toString().contains("b -> c"));
            assertTrue(gm.toString().contains("c -> d"));
            assertTrue(gm.toString().contains("d -> a"));
            assertTrue(gm.toString().contains("a -> e"));
            assertTrue(gm.toString().contains("e -> f"));
            assertTrue(gm.toString().contains("e -> g"));
            assertTrue(gm.toString().contains("f -> h"));
            assertTrue(gm.toString().contains("g -> h"));

            //out put graph
            // act
            gm.outputGraph("output.txt");
            String expected = gm.toString();

            // asses
            String actual = Files.readString(Paths.get("output.txt"));
            assertEquals(expected, actual);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Test feature 2
    @Test
    public void testFeature2() {
        // act
        gm.addNode("z");

        // assert
        assertEquals(9, gm.getGraph().getNodeCount());

        // act
        String[] add = {"r", "q"};
        gm.addNodes(add);

        // asses
        assertEquals(11, gm.getGraph().getNodeCount());
    }

    // Test feature 3
    @Test
    public void testFeature3() {
        gm.addEdge("k", "r");

        assertEquals(10, gm.getGraph().edgeCount());         //new nodes

        gm.addEdge("k", "a");       // existing nodes
        assertEquals(11, gm.getGraph().edgeCount());

        assertEquals(10, gm.getGraph().getNodeCount());     //test if nodes were added right
    }

    // Test feature 4
    @Test
    public void testFeature4() throws IOException {
        gm.outputDOTGraph("output.dot");

        StringBuilder sb = new StringBuilder("diagraph {\n");
        List<Edge> edges = gm.getGraph().getEdges();
        for(int i = 0; i < edges.size(); i++) {
            sb.append(edges.get(i).toString() + ";\n");
        }
        sb.append("}\n");

        String expected = sb.toString();

        try {
            String actual = Files.readString(Paths.get("output.dot"));
            assertEquals(expected, actual);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testFeature5() throws IOException {
        gm.outputGraphics("/Users/davidjanda", "png");
    }

    @Test
    public void testFeature6() throws IOException {     //testing removeNode function
        assertEquals(8, gm.getGraph().getNodeCount());
        gm.removeNode("a");
        assertEquals(7, gm.getGraph().getNodeCount());
    }

    @Test (expected = Exception.class)
    public void testFeatureException6() throws Exception {     //testing removing a node that doesn't exist
        gm.removeNode("r");
    }

    @Test
    public void testFeature7() throws IOException {
        String[] remove = {"a", "b"};
        gm.removeNodes(remove);
        assertEquals(6, gm.getGraph().getNodeCount());
    }

    @Test (expected = Exception.class)
    public void testFeatureException7() throws IOException {
        String[] remove = {"a", "k"};
        gm.removeNodes(remove);
    }

    @Test
    public void testFeatureRemoveEdge() throws IOException {
        gm.removeEdge(" a", "b");
        assertEquals(8, gm.getGraph().edgeCount());
    }

    @Test (expected = Exception.class)
    public void testFeatureExceptionRemoveEdge() throws IOException {
        gm.removeEdge("a", "c");
    }

    @Test
    public void testFeatureBFS() throws IOException {
        Path path = gm.GraphSearch("a", "d", "bfs");
        assertEquals("a -> b -> c -> d", path.toString());
    }

    @Test
    public void testFeatureDFS() throws IOException {
        Path path = gm.GraphSearch("b", "g", "dfs");
        assertEquals("b -> c -> d -> a -> e -> g", path.toString());
    }  

    @Test (expected = Exception.class)
    public void testFeatureExceptionDFS() throws IOException {
        Path path = gm.GraphSearch("b", "k", "dfs");
    }



}
