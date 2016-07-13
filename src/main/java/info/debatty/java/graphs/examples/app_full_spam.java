package info.debatty.java.graphs.examples;


import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;;
import info.debatty.java.graphs.SimilarityInterface;
import info.debatty.java.stringsimilarity.JaroWinkler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**

 *
 *
 *
 *
 */
public class app_full_spam {
    public static int K = 2;
    public static int count =2000;
    public static int iterations=1;
    public static int run=1; // still hardcoded
    public static int depth=3;
    public static int number_deletion=2000;
    public static int quality_sampling=200;
    public static int max_value=100000;
    public static boolean random=true;
    public static boolean adding_nodes=true;
    public static int type_of_source=2;
    public static int add_source=4;
    public static int wave = 1000;
    public static int threshold_stream=count;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Spam experiment");
        if (args.length!=11) {
            System.out.println("Input wrong! \nCorrect usage: K_of_the_graph number_of_nodes number_deleted_nodes #iterations depth_of_deletion_update quality_sampling max_value type_source() random_jump_boolean adding_node_boolean adding_source()" +
                    "type of source: - disabled, just spam "+
            "\n type of adding source: - disabled, just spam! spam spam spam");

        }

        if (args.length>0) {
            K = Integer.parseInt(args[0]);
            count = Integer.parseInt(args[1]);
            number_deletion = Integer.parseInt(args[2]);
            iterations = Integer.parseInt(args[3]);
            depth = Integer.parseInt(args[4]);
            quality_sampling = Integer.parseInt(args[5]);
            max_value = Integer.parseInt(args[6]);
            type_of_source = Integer.parseInt(args[7]);

            random = Boolean.parseBoolean(args[8]);
            adding_nodes = Boolean.parseBoolean(args[9]);
            add_source = Integer.parseInt(args[10]);

        }
        if (args.length>11) {wave=Integer.parseInt(args[11]);};
        if (adding_nodes==false) if (number_deletion>count-K-1) number_deletion=count-K-1;
        for (int b = 1; b <= run; b++) {
            ArrayList<Integer> errors = new ArrayList<Integer>();
            ArrayList<Integer> modified_edges = new ArrayList<Integer>();
            ArrayList<Integer> comparisons = new ArrayList<Integer>();
            ArrayList<ArrayList<Integer>> errors_trend_global = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> comparisons_trend_global = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Double>> errors_trend_global_pc = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> errors_trend_global_emt = new ArrayList<ArrayList<Double>>(); //theoretical modified edges
            ArrayList<ArrayList<Double>> errors_trend_global_nodes_pc = new ArrayList<ArrayList<Double>>(); //number of nodes involved in the error wrt to the total amount
            ArrayList<ArrayList<Double>> errors_trend_global_nodes_emt = new ArrayList<ArrayList<Double>>(); //number of nodes involved in the error wrt to the theoretical total amount
            // inf(nodes deleted * k;nodes_still there)
            String path = "spam-subject-200K_unique.txt";
            ArrayList<String> data = new ArrayList<String>();
            ArrayList<String> data_new = new ArrayList<String>();
            try {
                File file = new File(path);
                FileReader fr = new FileReader(file);
                BufferedReader dati = new BufferedReader(new FileReader(new File(path)));
                String nextLine;
                // Leggo una riga per volta e memorizzo il suo contenuto in un oggetto riga
                int i=0;
                while ((nextLine = dati.readLine()) != null){
                    i++;
                    if (i<=threshold_stream) data.add(nextLine);
                    else data_new.add(nextLine);
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
            for (int a = 0; a < iterations; a++) {

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
                int taken_from_others = 0;
                for (int i = 0; i < data.size(); i++) {
                    nodes.add(new Node<String>(String.valueOf(i), data.get(i)));
                    nodes_og.add(new Node<String>(String.valueOf(i), data.get(i)));
                }



                //create the two arrays of nodes


                // this is for the online graph
                Brute builder_1 = new Brute<String>();
                builder_1.setK(K);
                builder_1.setSimilarity(new SimilarityInterface<String>() {

                    public double similarity(
                            final String value1,
                            final String value2) {
                        JaroWinkler jw = new JaroWinkler();
                        return jw.similarity(value1, value2);
                    }
                });

                Graph<String> graph_og = builder_1.computeGraph(nodes_og);
                OnlineGraph<String> online_graph = new OnlineGraph<String>(graph_og);
                int last_node_id=threshold_stream;
                if (adding_nodes==false) number_deletion=threshold_stream-K-1;
                for (int i = 0; i < number_deletion; i++) {
                    ArrayList<Node> nodes_temp = new ArrayList<Node>();
                    Node<String> node2del = null;
                    int node_comparisons=0;
                    for (Node<String> n : nodes) {
                        //System.out.println(""+n.id);
                        if ((Integer.parseInt(n.id)) == i) node2del = n;
                        if ((Integer.parseInt(n.id)) > i) nodes_temp.add(n);
                    }

                    node_comparisons += graph_og.removeAndUpdate_3_depth(node2del, depth, random);

                    if (adding_nodes==true) {
                        if (taken_from_others==data_new.size()-1) {break;}
                        String temp = data_new.get(taken_from_others);
                        taken_from_others++;


                        nodes.add(new Node<String>(String.valueOf(last_node_id), temp));
                        nodes_temp.add(new Node<String>(String.valueOf(last_node_id), temp));



                        //System.out.println("nodes:"+nodes);
                        //System.out.println("nodes_temp:"+nodes_temp);

                        node_comparisons += online_graph.addNode(new Node<String>(String.valueOf(last_node_id), temp));
                        last_node_id++;

                        //last_node_id++;



                    }

                    //comparisons.add(node_comparisons);
                    int wrong_edge = 0;

                    if ((i+1)%quality_sampling==0) {

                        //create the brute graph
                        Brute builder = new Brute<Double[]>();
                        builder.setK(K);
                        builder.setSimilarity(new SimilarityInterface<String>() {

                            public double similarity(
                                    final String value1,
                                    final String value2) {
                                JaroWinkler jw = new JaroWinkler();
                                return jw.similarity(value1, value2);
                            }
                        });
                        Graph<Double[]> graph_brute = builder.computeGraph(nodes_temp);


                        int total_edges=0;
                        //System.out.println("\n Killed: "+(i+1)+" nodes");
                        if (adding_nodes==false) {total_edges=(count-(i+1))*K;}
                        else {total_edges=count*K;}
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
                        System.out.println(wrong_edges_pc);
                        double wrong_edges_emt = (double) wrong_edge / modified_edges_theo * 100;
                        double wrong_nodes_pc = (double) nodes_involved / remained_nodes * 100;
                        double wrong_nodes_emt = (double) nodes_involved / modified_nodes_theo * 100;
                        // System.out.println("\n relative error: "+wrong_edges_pc);
                        errors_trend_single_pc.add(wrong_edges_pc);
                        errors_trend_single_emt.add(wrong_edges_emt);
                        errors_trend_single_nodes_pc.add(wrong_nodes_pc);
                        errors_trend_single_nodes_emt.add(wrong_nodes_emt);
                        q_trend_single.add(q);
                        comparisons_trend_single.add(node_comparisons);
                    } }
                //System.out.print("the wrong edges are: " + errors_trend_single + "\n");
                //System.out.print("the relative wrong edges are: " + errors_trend_single_pc + "\n");
                errors_trend_global.add(errors_trend_single);
                errors_trend_global_pc.add(errors_trend_single_pc);
                errors_trend_global_emt.add(errors_trend_single_emt);
                errors_trend_global_nodes_pc.add(errors_trend_single_pc);
                errors_trend_global_nodes_emt.add(errors_trend_single_emt);
                comparisons_trend_global.add(comparisons_trend_single);
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
                    "\n depth of deletion= "+depth+
                    "\n sampling= "+quality_sampling+
                    "\n random= "+random+
                    "\n adding node= "+adding_nodes);
            if (type_of_source==1) System.out.println("Uniform source");
            if (type_of_source==2) System.out.println("Source from 3 well separated clusters");
            if (type_of_source==3) System.out.println("Source from 3 overlapping clusters");
            if (add_source==1) System.out.println("New nodes from a Uniform source");
            if (add_source==2) System.out.println("New nodes from a source from the 3 clusters randomly");
            if (add_source==3) System.out.println("New nodes from waves of points from one cluster (changing at each 1/6 adding points");
            if (add_source==4) System.out.println("New nodes from complete new source");


            ArrayList<Double> avg_errors_global = new ArrayList<Double>();
            ArrayList<Double> avg_errors_global_pc = new ArrayList<Double>();
            ArrayList<Double> avg_errors_global_emt = new ArrayList<Double>();
            ArrayList<Double> avg_errors_global_nodes_pc = new ArrayList<Double>();
            ArrayList<Double> avg_errors_global_nodes_emt = new ArrayList<Double>();
            ArrayList<Double> avg_comparison_global = new ArrayList<Double>();
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
            for (int a=0;a<lunghezza_liste;a++) {
                double c=0;
                for (int i=0;i<iterations;i++)
                {
                    c+=comparisons_trend_global.get(i).get(a);


                }
                avg_comparison_global.add(c/iterations);

            }
            System.out.println("\n comparisons: "+ avg_comparison_global);
        }
    }

}