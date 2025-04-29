public class Main {
    public static void main(String[] args) {
        GraphManager gm = new GraphManager();
        try {
            gm.parseGraph("input.dot"); // Use resource file
        } catch (java.lang.Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(gm.toString());
        gm.GraphSearch("b", "e", "bfs");
        gm.GraphSearch("b", "e", "dfs");
    }
}