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
 depth=1
 in the average the differences are 2.72
 the modified edges are 8.59
 the correct edges are 3997.28
 the Q is 0.31999999999999995
 *
 the wrong edges are: [2, 2, 1, 1, 3, 1, 1, 3, 3, 1, 3, 3, 2, 2, 2, 4, 3, 1, 1, 1, 3, 1, 2, 3, 0, 2, 3, 1, 1, 7, 2, 0, 2, 0, 10, 4, 2, 0, 9, 2, 3, 2, 2, 1, 2, 5, 1, 1, 2, 4, 1, 2, 5, 2, 1, 1, 9, 4, 2, 3, 2, 6, 2, 4, 2, 3, 3, 4, 2, 2, 2, 3, 3, 1, 2, 4, 3, 3, 2, 5, 2, 1, 2, 3, 2, 3, 2, 2, 2, 2, 0, 2, 3, 2, 3, 5, 2, 4, 2, 5]
 in the average the differences are 2.54
 the modified edges are 7.41
 the correct edges are 3997.46
 the Q is 0.365


 the wrong edges are: [2, 3, 3, 2, 3, 2, 3, 3, 4, 12, 2, 4, 2, 1, 0, 1, 3, 3, 3, 3, 3, 2, 2, 1, 3, 4, 2, 5, 3, 3, 7, 2, 3, 3, 2, 2, 3, 0, 7, 3, 2, 6, 3, 5, 1, 1, 2, 3, 3, 2, 4, 2, 3, 6, 2, 3, 1, 3, 1, 1, 3, 9, 4, 6, 2, 1, 1, 2, 4, 4, 1, 4, 2, 1, 4, 2, 7, 8, 5, 3, 3, 2, 0, 3, 2, 3, 4, 0, 1, 2, 2, 3, 2, 3, 1, 3, 4, 2, 3, 2]
 in the average the differences are 2.91
 the modified edges are 8.41
 the correct edges are 3997.09
 the Q is 0.27249999999999996
 *
 *
 *
 depth = 2
 the wrong edges are: [1, 0, 0, 2, 0, 5, 0, 1, 5, 2, 1, 0, 1, 1, 0, 1, 2, 1, 0, 0, 2, 0, 1, 0, 2, 1, 1, 1, 3, 4, 1, 1, 1, 2, 2, 1, 1, 2, 0, 1, 0, 0, 4, 1, 1, 0, 1, 4, 0, 0, 6, 2, 0, 1, 3, 0, 0, 0, 0, 0, 4, 4, 1, 1, 0, 0, 0, 1, 4, 2, 1, 1, 0, 1, 2, 0, 4, 1, 0, 5, 1, 1, 2, 1, 2, 0, 1, 1, 0, 0, 1, 3, 3, 1, 0, 1, 0, 2, 3, 2]
 in the average the differences are 1.3
 the modified edges are 5.26
 the correct edges are 3998.7
 the Q is 0.675

 the wrong edges are: [0, 1, 2, 2, 1, 2, 2, 1, 2, 4, 4, 2, 1, 0, 1, 0, 0, 2, 1, 0, 0, 0, 1, 1, 1, 3, 3, 0, 0, 1, 1, 2, 2, 0, 0, 0, 0, 1, 2, 1, 1, 1, 0, 1, 1, 1, 1, 4, 0, 0, 2, 1, 6, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 2, 2, 13, 2, 2, 1, 3, 0, 2, 1, 0, 2, 0, 1, 5, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 3, 1, 0, 1, 2, 4, 2, 0, 1]
 in the average the differences are 1.25
 the modified edges are 4.47
 the correct edges are 3998.75
 the Q is 0.6875

 the wrong edges are: [5, 3, 0, 0, 5, 2, 1, 0, 0, 0, 1, 0, 1, 8, 1, 1, 1, 4, 0, 1, 0, 0, 1, 1, 4, 2, 6, 0, 0, 0, 0, 1, 0, 0, 2, 1, 4, 5, 2, 1, 3, 1, 0, 0, 1, 2, 2, 2, 0, 2, 1, 0, 2, 2, 1, 4, 1, 0, 2, 0, 0, 2, 0, 0, 0, 2, 0, 1, 1, 0, 2, 0, 1, 0, 3, 1, 1, 0, 4, 0, 3, 0, 1, 1, 1, 0, 1, 0, 0, 2, 2, 1, 2, 0, 5, 1, 3, 0, 2, 1]
 in the average the differences are 1.34
 the modified edges are 5.42
 the correct edges are 3998.66
 the Q is 0.665

 depth=3

 the wrong edges are: [0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 4, 0, 3, 0, 1, 1, 1, 0, 0, 2, 0, 0, 0, 2, 0, 1, 0, 0, 2, 0, 5, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 1, 14, 0, 4, 0, 0, 1, 0, 0, 0, 0, 1, 0, 4, 0, 0, 0, 1, 3, 0, 0, 0, 0, 0, 8, 0, 1, 0, 0, 1, 1]
 in the average the differences are 0.77
 the modified edges are 5.1
 the correct edges are 3999.23
 the Q is 0.8075


 in the average the differences are 0.78
 the modified edges are 5.03
 the correct edges are 3999.22
 the Q is 0.8049999999999999

 in the average the differences are 0.68
 the modified edges are 5.33
 the correct edges are 3999.32
 the Q is 0.83

 depth 4
 the wrong edges are: [0, 0, 0, 1, 0, 5, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 3, 10, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 4, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0]
 in the average the differences are 0.53
 the modified edges are 4.61
 the correct edges are 3999.47
 the Q is 0.8674999999999999

 in the average the differences are 0.5
 the modified edges are 4.49
 the correct edges are 3999.5
 the Q is 0.875

 the wrong edges are: [0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 2, 1, 0, 0, 0, 0, 0, 0, 10, 0, 1, 3, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 7, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1]
 in the average the differences are 0.55
 the modified edges are 5.06
 the correct edges are 3999.45
 the Q is 0.8625

depth 5
 the wrong edges are: [0, 0, 0, 0, 0, 2, 1, 0, 0, 3, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 5, 3, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 1, 1]
 in the average the differences are 0.51
 the modified edges are 5.59
 the correct edges are 4999.49
 the Q is 0.898

 the wrong edges are: [1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 2, 0, 0, 0, 5, 3, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
 in the average the differences are 0.34
 the modified edges are 5.72
 the correct edges are 4999.66
 the Q is 0.9319999999999999

 the wrong edges are: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 5, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 11, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0]
 in the average the differences are 0.42
 the modified edges are 5.8
 the correct edges are 4999.58
 the Q is 0.916

 depth 6
 in the average the differences are 0.32
 the modified edges are 5.46
 the correct edges are 4999.68
 the Q is 0.9359999999999999

 in the average the differences are 0.24
 the modified edges are 5.1
 the correct edges are 4999.76
 the Q is 0.952
 *
 *
 * in the average the differences are 0.33
 the modified edges are 5.21
 the correct edges are 4999.67
 the Q is 0.9339999999999999

 depth 7
 *
 * Q= 0.902
 *
 * in the average the differences are 0.35
 the modified edges are 5.5
 the correct edges are 4999.65
 the Q is 0.93

 0.948
 *
 *
 *
 */
public class ap11_fabio {
    public static int K = 4;
    public static int count = 1000;
    public static int iterations=1000;
    public static int run=15;
    public static int depth=3;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayList<Integer> errors = new ArrayList<Integer>();
        ArrayList<Integer> modified_edges = new ArrayList<Integer>();
        for (int b = 0; b < run; b++) {
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
            int node_modified_edges = online_graph_1.removeAndUpdate_2_depth(N0,depth);
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
        System.out.print("the Q is " + Double.toString(1-avg/K)+ "\n");
            if (b%3==0) depth++;}

    }
}