package info.debatty.java.graphs.examples;

import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;


/**
 * Created by fabio on 23/02/16.
 *
 * App to emule a steady state behaviour,
 * sliding window of 5k nodes
 *  1000 nodes
 *  500 are deleted
 *
 * 
 */
public class app15_interactive {
    public static int K = 4;
    public static int count =500;
    public static int iterations=3;
    public static int run=1; // still hardcoded
    public static int depth=3;
    public static int number_deletion=2000;
    public static int quality_sampling=10;
    public static int max_value=100000;
    public static boolean random=true;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        if (args.length!=8) {
            System.out.println("Input wrong! \nCorrect usage: K number_of_nodes number_deleted_nodes #iterations depth_of_deletion_update quality_sampling max_value random_jump_boolean");
            //return;
        }
        /*
        K = Integer.parseInt(args[0]);
        count = Integer.parseInt(args[1]);
        number_deletion = Integer.parseInt(args[2]);
        iterations = Integer.parseInt(args[3]);
        depth = Integer.parseInt(args[4]);
        quality_sampling = Integer.parseInt(args[5]);
        max_value = Integer.parseInt(args[6]);
        random = Boolean.parseBoolean(args[7]);
        */
        for (int b = 1; b <= run; b++) {
            ArrayList<Integer> errors = new ArrayList<Integer>();
            ArrayList<Integer> modified_edges = new ArrayList<Integer>();
            ArrayList<Integer> comparisons = new ArrayList<Integer>();
            ArrayList<ArrayList<Integer>> errors_trend_global = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Double>> errors_trend_global_pc = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> errors_trend_global_emt = new ArrayList<ArrayList<Double>>(); //theoretical modified edges
            ArrayList<ArrayList<Double>> errors_trend_global_nodes_pc = new ArrayList<ArrayList<Double>>(); //number of nodes involved in the error wrt to the total amount
            ArrayList<ArrayList<Double>> errors_trend_global_nodes_emt = new ArrayList<ArrayList<Double>>(); //number of nodes involved in the error wrt to the theoretical total amount
                                                                                                            // inf(nodes deleted * k;nodes_still there)
            for (int a = 0; a < iterations; a++) {
                // Generate some random nodes and add them to the graphs
                Random r = new Random();
                //create the two arrays of nodes
                ArrayList<Node> nodes = new ArrayList<Node>(count);
                ArrayList<Node> nodes_og = new ArrayList<Node>(count);  //for online graph

                ArrayList<Integer> errors_trend_single = new ArrayList<Integer>();
                ArrayList<Double> errors_trend_single_pc = new ArrayList<Double>();
                ArrayList<Double> errors_trend_single_emt = new ArrayList<Double>();
                ArrayList<Double> errors_trend_single_nodes_pc = new ArrayList<Double>();
                ArrayList<Double> errors_trend_single_nodes_emt = new ArrayList<Double>();
                ArrayList<Integer> modified_edges_trend_single = new ArrayList<Integer>();
                ArrayList<Integer> comparisons_trend_single = new ArrayList<Integer>();
                ArrayList<Double> q_trend_single = new ArrayList<Double>();
                for (int i = 0; i < count; i++) {
                    // The value of our nodes will be an int
                    int value = r.nextInt(max_value);
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
                int last_node_id=count;
                //start to delete nodes & add new ones
                for (int i = 0; i < number_deletion; i++) {
                    ArrayList<Node> nodes_temp = new ArrayList<Node>();
                    Node<Integer> node2del = null;
                    for (Node<Integer> n : nodes) {
                        //System.out.println("\n"+n.id);
                        if ((Integer.parseInt(n.id)) == i) node2del = n;
                        if ((Integer.parseInt(n.id)) > i) nodes_temp.add(n);
                    }
                    //create a new node:
                    int value = r.nextInt(max_value);
                    nodes.add(new Node<Integer>(String.valueOf(last_node_id), value));
                    nodes_temp.add(new Node<Integer>(String.valueOf(last_node_id), value));
                    //System.out.println("nodes:"+nodes);
                    //System.out.println("nodes_temp:"+nodes_temp);
                    //System.out.println("just added to the bruteforce graph the node:"+last_node_id+":"+value);
                    online_graph.addNode(new Node<Integer>(String.valueOf(last_node_id), value));
                    last_node_id++;

                    int node_comparisons = graph_og.removeAndUpdate_3_depth(node2del, depth, random);
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



                        //System.out.println("\n Killed: "+(i+1)+" nodes");
                        //int total_edges=(count-(i+1))*K;
                        int total_edges=count*K;
                        int modified_edges_theo = (i+1)*K;
                        if (modified_edges_theo>total_edges) modified_edges_theo = total_edges;
                        int remained_nodes=count-(i+1);
                        int modified_nodes_theo = (i+1)*K;
                        if (modified_nodes_theo>remained_nodes) modified_nodes_theo = remained_nodes;
                        int nodes_involved=0;
                        //System.out.println("\n There are "+total_edges+" total edges");
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
                            if (node_wrong_edges>0) nodes_involved++;


                    }

                        double q=  1- ((double) wrong_edge/K);
                        errors_trend_single.add(wrong_edge);
                        double wrong_edges_pc = (double) wrong_edge / total_edges * 100;
                        double wrong_edges_emt = (double) wrong_edge / modified_edges_theo * 100;
                        double wrong_nodes_pc = (double) nodes_involved / remained_nodes * 100;
                        double wrong_nodes_emt = (double) nodes_involved / modified_nodes_theo * 100;
                        // System.out.println("\n relative error: "+wrong_edges_pc);
                        errors_trend_single_pc.add(wrong_edges_pc);
                        errors_trend_single_emt.add(wrong_edges_emt);
                        errors_trend_single_nodes_pc.add(wrong_nodes_pc);
                        errors_trend_single_nodes_emt.add(wrong_nodes_emt);
                        q_trend_single.add(q);
                } }
                    System.out.print("the wrong edges are: " + errors_trend_single + "\n");
                    System.out.print("the relative wrong edges are: " + errors_trend_single_pc + "\n");
                errors_trend_global.add(errors_trend_single);
                errors_trend_global_pc.add(errors_trend_single_pc);
                errors_trend_global_emt.add(errors_trend_single_emt);
                errors_trend_global_nodes_pc.add(errors_trend_single_pc);
                errors_trend_global_nodes_emt.add(errors_trend_single_emt);
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
            System.out.println("Configuration \n" +
                    " K= "+K+
                    "\n number of nodes= "+count+
                    "\n number deletes nodes= "+number_deletion+
                    "\n iterations= "+iterations+
                    "\n depth of deletion= "+depth);

            ArrayList<Double> avg_errors_global = new ArrayList<Double>();
            ArrayList<Double> avg_errors_global_pc = new ArrayList<Double>();
            ArrayList<Double> avg_errors_global_emt = new ArrayList<Double>();
            ArrayList<Double> avg_errors_global_nodes_pc = new ArrayList<Double>();
            ArrayList<Double> avg_errors_global_nodes_emt = new ArrayList<Double>();
            int lunghezza_liste=errors_trend_global.get(0).size();
            for (int a=0;a<lunghezza_liste;a++) {
                double c=0;
                for (int i=0;i<iterations;i++)
                {
                c+=errors_trend_global.get(i).get(a);


                }
                avg_errors_global.add(c/iterations);

        }
            System.out.println("\n error trend: "+ avg_errors_global);
            for (int a=0;a<lunghezza_liste;a++) {
                double c=0;
                for (int i=0;i<iterations;i++)
                {
                    c+=errors_trend_global_pc.get(i).get(a);


                }
                avg_errors_global_pc.add(c/iterations);

            }
            System.out.println("\n error trend relative: "+ avg_errors_global_pc);
            for (int a=0;a<lunghezza_liste;a++) {
                double c=0;
                for (int i=0;i<iterations;i++)
                {
                    c+=errors_trend_global_emt.get(i).get(a);


                }
                avg_errors_global_emt.add(c/iterations);

            }
           // System.out.println("\n error trend relative over theoretical number of modified edges: "+ avg_errors_global_emt);
            for (int a=0;a<lunghezza_liste;a++) {
                double c=0;
                for (int i=0;i<iterations;i++)
                {
                    c+=errors_trend_global_nodes_pc.get(i).get(a);


                }
                avg_errors_global_nodes_pc.add(c/iterations);

            }
           // System.out.println("\n nodes errors relative to the amount of nodes: "+ avg_errors_global_nodes_pc);
            for (int a=0;a<lunghezza_liste;a++) {
                double c=0;
                for (int i=0;i<iterations;i++)
                {
                    c+=errors_trend_global_nodes_emt.get(i).get(a);


                }
                avg_errors_global_nodes_emt.add(c/iterations);

            }
           // System.out.println("\n error trend relative over theoretical number of modified nodes: "+ avg_errors_global_emt);
        }
    }

}