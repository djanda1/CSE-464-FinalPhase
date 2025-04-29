import java.util.*;
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        GraphManager gm = new GraphManager();
        try {
            gm.parseGraph("\\Users\\dbob1\\IdeaProjects\\CSE-464-2025-djanda2\\CSE464ProjectPhase1\\src\\main\\java\\input.dot");
        } catch (java.lang.Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(gm.toString());
        gm.GraphSearch("b", "e", "bfs");
        gm.GraphSearch("b", "e", "dfs");
    }
}