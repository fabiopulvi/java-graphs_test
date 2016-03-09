package info.debatty.java.graphs.examples;

import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;


/**
 * Created by fabio on 23/02/16.
 *
 * Two graphs are created, both online with ignns
 * one with node 0
 * one without node 0
 * then node 0 is deleted from the first graphs
 * 
 */
public class app7_fabio {
    public static int K = 1;
    public static int count = 10;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //create the online graph 1

        Brute builder_1 = new Brute<Integer>();
        builder_1.setK(K);
        builder_1.setSimilarity(new SimilarityInterface<Integer>() {

            public double similarity(Integer value1, Integer value2) {
                return 1.0 / (1.0 + Math.abs(value1 - value2));
            }
        });
        ArrayList<Node> nodes_1 = new ArrayList<Node>();
        Graph<Integer> graph_1 = builder_1.computeGraph(nodes_1);
        OnlineGraph<Integer> online_graph_1= new OnlineGraph<Integer>(graph_1);

        //create the online graph 2

        Brute builder_2 = new Brute<Integer>();
        builder_2.setK(K);
        builder_2.setSimilarity(new SimilarityInterface<Integer>() {

            public double similarity(Integer value1, Integer value2) {
                return 1.0 / (1.0 + Math.abs(value1 - value2));
            }
        });
        ArrayList<Node> nodes_2 = new ArrayList<Node>();
        Graph<Integer> graph_2 = builder_1.computeGraph(nodes_2);
        OnlineGraph<Integer> online_graph_2= new OnlineGraph<Integer>(graph_2);



        // Generate some random nodes and add them to the graphs
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            // The value of our nodes will be an in+String.valueOf(i)+" "+ value)t
            int value = r.nextInt(100);
            // graph2 does not have the node 0
           // if (i!=0) {online_graph_2.addNode(new Node<Integer>(String.valueOf(i), value));}
            //if (i==0) {System.out.println("\n The node that only graph 1 has, is: "+String.valueOf(i)+" "+ value);}
            online_graph_2.addNode(new Node<Integer>(String.valueOf(i), value));
            System.out.println("Just added to graph1 node: "+String.valueOf(i)+" "+ value);
            online_graph_1.addNode(new Node<Integer>(String.valueOf(i), value));

        }

        // Iterate the nodes to see the differences
        for (Node n : online_graph_2.getNodes()) {
            NeighborList nl1 = online_graph_1.get(n);
            NeighborList nl2 = online_graph_2.get(n);
        //    if (K-nl1.countCommons(nl2)>0) {
                System.out.print("\n node: "+n);

                System.out.println("\n approximate graph: "+nl1);
                System.out.println("approximate graph: "+nl2);

                System.out.print("the differences are: "+(Integer.toString(K-nl1.countCommons(nl2)))+"\n");
            //}


        }

    }
}