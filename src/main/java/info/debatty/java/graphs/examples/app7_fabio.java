package info.debatty.java.graphs.examples;

import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;


/**
 * Created by fabio on 23/02/16.
 *
 * Two graphs brute graphs are created
 * one with node 0
 * one without node 0
 * then node 0 is deleted from the first graphs
 * 
 */
public class app7_fabio {
    public static int K = 2;
    public static int count = 100;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Generate some random nodes and add them to the graphs
        Random r = new Random();
            //create the two arrays of nodes
        ArrayList<Node> nodes_1 = new ArrayList<Node>(count);
        ArrayList<Node> nodes_2 = new ArrayList<Node>(count);

        for (int i = 0; i < count; i++) {
            // The value of our nodes will be an int
            int value = r.nextInt(100);
            if (i!=0) nodes_2.add(new Node<Integer>(String.valueOf(i), value));
            nodes_1.add(new Node<Integer>(String.valueOf(i), value));
        }

        Brute builder_1 = new Brute<Integer>();
        builder_1.setK(K);
        builder_1.setSimilarity(new SimilarityInterface<Integer>() {

            public double similarity(Integer value1, Integer value2) {
                return 1.0 / (1.0 + Math.abs(value1 - value2));
            }
        });

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

        Graph<Integer> graph_2 = builder_1.computeGraph(nodes_2);
        OnlineGraph<Integer> online_graph_2= new OnlineGraph<Integer>(graph_2);
        //find the node0 info and delete it
        Node <Integer> N0 = null;
        NeighborList N0_list = null;
        for (Node <Integer> n : online_graph_1.getNodes()) {
            if (Integer.parseInt(n.id)==0) {
                N0 = new Node<Integer>(n.id, n.value);
                N0_list = online_graph_1.get(n);
            }
        }
        System.out.println("\n the node to delete is N0: "+N0);

        online_graph_1.remove(N0);
        //online_graph_1.removeNodeFromNeighbourlist(N0);
        online_graph_1.removeAndUpdate_flat(N0);



        // Iterate the nodes to see the differences
        for (Node n : online_graph_2.getNodes()) {
            NeighborList nl1 = online_graph_1.get(n);
            NeighborList nl2 = online_graph_2.get(n);
            if (K-nl1.countCommons(nl2)>0) {
                System.out.print("\n node: "+n);

                System.out.println("\n updated graph: "+nl1);
                System.out.println("brute graph: "+nl2);

                System.out.print("the differences are: "+(Integer.toString(K-nl1.countCommons(nl2)))+"\n");
            }


        }

    }
}