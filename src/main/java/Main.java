import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GraphManager gm = new GraphManager();
        String filename = "input.dot";
        try {
            gm.parseGraph(filename); // Use resource file
        } catch (java.lang.Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("---Welcome to David Janda's Graph Project for CSE 464---");
        System.out.println("Choose an option:");
        printMenu(filename);
        Scanner sc = new Scanner(System.in);
        String src = "";
        String dst = "";
        String name = "";
        int choice = Integer.parseInt(sc.nextLine());
        while(true) {
            switch (choice) {
                case 1:
                    System.out.println(gm.toString());
                    break;

                case 2:
                    String temp = filename;
                    System.out.println("Enter the path of your new file");
                    filename = sc.nextLine();
                    gm = new GraphManager();
                    try {
                        gm.parseGraph(filename);
                    } catch (java.lang.Exception e) {
                        System.out.println("Unable to Parse Graph or find new file name reverting back to the previous file");
                        filename = temp;
                        try {
                            gm.parseGraph(filename);
                        }
                        catch(java.lang.Exception ee){
                            System.out.println("System fail... exiting");
                            return;
                        }
                    }
                    break;

                case 3:
                    System.out.println("Enter name of node you would like to add");
                    name = sc.nextLine();
                    gm.addNode(name);
                    break;

                case 4:
                    System.out.println("Enter name of the edge source node you would like to add");
                    src = sc.nextLine();
                    System.out.println("Enter name of the edge destination node you would like to add");
                    dst = sc.nextLine();
                    gm.addEdge(src, dst);
                    break;

                case 5:
                    System.out.println("Enter name of the node you would like to remove");
                    name = sc.nextLine();
                    gm.removeNode(name);
                    break;

                case 6:
                    System.out.println("Enter name of the edge source node you would like to remove");
                    src = sc.nextLine();
                    System.out.println("Enter name of the edge destination node you would like to remove");
                    dst = sc.nextLine();
                    gm.removeEdge(src, dst);
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
            System.out.println("\nChoose another option");
            printMenu(filename);
            choice = Integer.parseInt(sc.nextLine());
        }
    }

    public static void printMenu(String filename) {
        System.out.println("\n1. Print graph with filepath/name: " + filename +
                "\n2. Change graph with new file" +
                "\n3. Add node" +
                "\n4. Add edge" +
                "\n5. Remove node" +
                "\n6. Remove edge" +
                "\n7. Exit");
    }

}