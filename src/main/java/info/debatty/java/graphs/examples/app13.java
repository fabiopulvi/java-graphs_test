package info.debatty.java.graphs.examples;

import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;


/**
 * Created by fabio on 23/02/16.
 *
 * STRATEGY 3 with depth
 *  1000 nodes
 *  500 are deleted
 *
 * 
 */
public class app13 {
    public static int K = 4;
    public static int count =1000;
    public static int iterations=3;
    public static int run=2;
    public static int depth=3;
    public static int number_deletion=300;
    public static int quality_sampling=10;
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        for (int b = 1; b <= run; b++) {
            ArrayList<Integer> errors = new ArrayList<Integer>();
            ArrayList<Integer> modified_edges = new ArrayList<Integer>();
            ArrayList<Integer> comparisons = new ArrayList<Integer>();
            ArrayList<ArrayList<Integer>> errors_trend_global = new ArrayList<ArrayList<Integer>>();
            for (int a = 0; a < iterations; a++) {
                // Generate some random nodes and add them to the graphs
                Random r = new Random();
                //create the two arrays of nodes
                ArrayList<Node> nodes = new ArrayList<Node>(count);
                ArrayList<Node> nodes_og = new ArrayList<Node>(count);  //for online graph

                ArrayList<Integer> errors_trend_single = new ArrayList<Integer>();
                ArrayList<Integer> modified_edges_trend_single = new ArrayList<Integer>();
                ArrayList<Integer> comparisons_trend_single = new ArrayList<Integer>();
                ArrayList<Double> q_trend_single = new ArrayList<Double>();
                for (int i = 0; i < count; i++) {
                    // The value of our nodes will be an int
                    int value = r.nextInt(10000);
                    nodes.add(new Node<Integer>(String.valueOf(i), value));
                    nodes_og.add(new Node<Integer>(String.valueOf(i), value));
                }
                // this is for the online graph
                Brute builder_1 = new Brute<Integer>();
                builder_1.setK(K);
                builder_1.setSimilarity(new SimilarityInterface<Integer>() {

                    public double similarity(Integer value1, Integer value2) {
                        return 1.0 / (1.0 + Math.abs(value1 - value2));
                    }
                });

                Graph<Integer> graph_og = builder_1.computeGraph(nodes_og);
                OnlineGraph<Integer> online_graph = new OnlineGraph<Integer>(graph_og);

                //start to delete nodes
                for (int i = 0; i < number_deletion; i++) {
                    ArrayList<Node> nodes_temp = new ArrayList<Node>();
                    Node<Integer> node2del = null;
                    for (Node<Integer> n : nodes) {
                        //System.out.println("\n"+n.id);
                        if ((Integer.parseInt(n.id)) == i) node2del = n;
                        if ((Integer.parseInt(n.id)) > i) nodes_temp.add(n);
                    }

                    int node_comparisons = graph_og.removeAndUpdate_3_depth(node2del, depth);
                    comparisons.add(node_comparisons);
                    int wrong_edge = 0;

                    if (i%quality_sampling==0) {
                    //create the brute graph
                    Brute builder = new Brute<Integer>();
                    builder.setK(K);
                    builder.setSimilarity(new SimilarityInterface<Integer>() {

                        public double similarity(Integer value1, Integer value2) {
                            return 1.0 / (1.0 + Math.abs(value1 - value2));
                        }
                    });
                    Graph<Integer> graph_brute = builder.computeGraph(nodes_temp);




                    // Iterate the nodes to see the differences
                    for (Node n : graph_og.getNodes()) {
                        NeighborList nl1 = graph_og.get(n);
                        NeighborList nl2 = graph_brute.get(n);
                        int node_wrong_edges = K - nl1.countCommons(nl2);
                    //    if (node_wrong_edges > 0) {
                           // System.out.print("\n node: " + n);

                         //   System.out.println("\n updated graph: " + nl1);
                           // System.out.println("brute graph: " + nl2);

                            //System.out.print("the differences are: " + (Integer.toString(node_wrong_edges)) + "\n");
                        //}
                        wrong_edge += node_wrong_edges;


                    }

                    double q=  1- ((double) wrong_edge/K);
                    errors_trend_single.add(wrong_edge);
                    q_trend_single.add(q);
                } }
                  //  System.out.print("the wrong edges are: " + errors_trend_single + "\n");
                errors_trend_global.add(errors_trend_single);
                //System.out.print("the wrong q is: " + q_trend_single + "\n");
                    int sum = 0;
                    int sum_comparisons = 0;
                    for (int c : errors) sum += c;
                    for (int c : comparisons) sum_comparisons += c;
                    double avg = (double) sum / errors.size();
                    double avg_comparisons = (double) sum_comparisons / comparisons.size();
                    double correct_edges_all = (double) K * count;
                    //System.out.print("in the average the differences are " + avg + "\n");

                   // System.out.print("the correct edges are " + Double.toString(correct_edges_all - avg) + "\n");
                    //System.out.print("the Q is " + Double.toString(1 - avg / K) + "\n");
                    //System.out.print("the comparisons were in avg " + avg_comparisons+ "\n");





/*


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
                int node_comparisons = online_graph_1.removeAndUpdate_3_depth(N0, depth);
                comparisons.add(node_comparisons);
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
            System.out.print("the wrong edges are: " + errors + "\n");
            int sum = 0;
            int sum_comparisons = 0;
            for (int a : errors) sum += a;
            for (int a : comparisons) sum_comparisons += a;
            double avg = (double) sum / errors.size();
            double avg_comparisons = (double) sum_comparisons / comparisons.size();
            double correct_edges_all = (double) K * count;
            System.out.print("in the average the differences are " + avg + "\n");

            System.out.print("the correct edges are " + Double.toString(correct_edges_all - avg) + "\n");
            System.out.print("the Q is " + Double.toString(1 - avg / K) + "\n");
            System.out.print("the comparisons were in avg " + avg_comparisons+ "\n");
            if (b%3==0) depth++;}
*/
            }
            ArrayList<Double> avg_errors_global = new ArrayList<Double>();
            int lunghezza_liste=errors_trend_global.get(0).size();
            for (int a=0;a<lunghezza_liste;a++) {
                double c=0;
                for (int i=0;i<iterations;i++)
                {
                c+=errors_trend_global.get(i).get(a);


                }
                avg_errors_global.add(c/iterations);

        }
            System.out.println("\n error trend: "+ avg_errors_global);        }
    }

}