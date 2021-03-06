package info.debatty.java.graphs.examples;

import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;


/**
 * Created by fabio on 23/02/16.
 *STRATEGY 2
 * Two graphs brute graphs are created
 * one with node 0
 * one without node 0
 * then node 0 is deleted from the first graphs
 *
 *
 * delivered a result with a conf of k=4, 1000 nodes and values up to 10000
 *
 * in the average the differences are 2.6
 the modified edges are 8.55
 the correct edges are 3997.4

 in the average the differences are 2.79
 the modified edges are 7.99
 the correct edges are 3997.21

 in the average the differences are 2.79
 the modified edges are 8.23
 the correct edges are 3997.21

 *
 * 
 */
public class app9_fabio {
    public static int K = 4;
    public static int count = 1000;
    public static int iterations=100;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayList<Integer> errors = new ArrayList<Integer>();
        ArrayList<Integer> modified_edges = new ArrayList<Integer>();
        for (int a = 0; a < iterations; a++) {
            // Generate some random nodes and add them to the graphs
            Random r = new Random();
            //create the two arrays of nodes
            ArrayList<Node> nodes_1 = new ArrayList<Node>(count);
            ArrayList<Node> nodes_2 = new ArrayList<Node>(count);

            for (int i = 0; i < count; i++) {
                // The value of our nodes will be an int
                int value = r.nextInt(10000);
                if (i != 0) nodes_2.add(new Node<Integer>(String.valueOf(i), value));
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
            OnlineGraph<Integer> online_graph_1 = new OnlineGraph<Integer>(graph_1);


            //create the online graph 2

            Brute builder_2 = new Brute<Integer>();
            builder_2.setK(K);
            builder_2.setSimilarity(new SimilarityInterface<Integer>() {

                public double similarity(Integer value1, Integer value2) {
                    return 1.0 / (1.0 + Math.abs(value1 - value2));
                }
            });

            Graph<Integer> graph_2 = builder_1.computeGraph(nodes_2);
            OnlineGraph<Integer> online_graph_2 = new OnlineGraph<Integer>(graph_2);
            //find the node0 info and delete it
            Node<Integer> N0 = null;
            NeighborList N0_list = null;
            for (Node<Integer> n : online_graph_1.getNodes()) {
                if (Integer.parseInt(n.id) == 0) {
                    N0 = new Node<Integer>(n.id, n.value);
                    N0_list = online_graph_1.get(n);
                }
            }
          //  System.out.println("\n the node to delete is N0: " + N0);


            //online_graph_1.removeNodeFromNeighbourlist(N0);
            int node_modified_edges = online_graph_1.removeAndUpdate_2(N0);
            modified_edges.add(node_modified_edges);
            int wrong_edge = 0;

            // Iterate the nodes to see the differences
            for (Node n : online_graph_2.getNodes()) {
                NeighborList nl1 = online_graph_1.get(n);
                NeighborList nl2 = online_graph_2.get(n);
                int node_wrong_edges = K - nl1.countCommons(nl2);
                if (node_wrong_edges > 0) {
                    //System.out.print("\n node: " + n);

                   // System.out.println("\n updated graph: " + nl1);
                  //  System.out.println("brute graph: " + nl2);

                //    System.out.print("the differences are: " + (Integer.toString(node_wrong_edges)) + "\n");
                }
                wrong_edge += node_wrong_edges;


            }

            //System.out.print("the modified nodes are: " + (Integer.toString(modified_nodes)) + " and the wrong edges are: " + wrong_edge + "\n");
            errors.add(wrong_edge);

        }
        System.out.print("the wrong edges are: " + errors+ "\n");
        int sum=0;
        int sum_modified=0;
        for (int a: errors) sum+=a;
        for (int a: modified_edges) sum_modified+=a;
        double avg= (double) sum / errors.size();
        double avg_modified= (double) sum_modified / modified_edges.size();
        double correct_edges_all= (double) K*count;
        System.out.print("in the average the differences are " + avg+ "\n");
        System.out.print("the modified edges are " + avg_modified+ "\n");
        System.out.print("the correct edges are " + Double.toString(correct_edges_all-avg)+ "\n");

    }
}