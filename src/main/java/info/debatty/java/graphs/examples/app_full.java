package info.debatty.java.graphs.examples;

import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;
import info.debatty.java.datasets.gaussian.Center;
import info.debatty.java.datasets.gaussian.Dataset;

import java.util.*;
import java.util.concurrent.ExecutionException;


/**
 * Created by fabio on 23/02/16.
 *
 * App to measure the accuracy perf with steady state behaviour,
 * sliding window of 5k nodes
 *  1000 nodes
 *  500 are deleted
 *
 *
 */
public class app_full {
    public static int K = 4;
    public static int count =100;
    public static int iterations=100;
    public static int run=1; // still hardcoded
    public static int depth=3;
    public static int number_deletion=6000;
    public static int quality_sampling=10;
    public static int max_value=100000;
    public static boolean random=true;
    public static boolean adding_nodes=false;
    public static int type_of_source=2;
    public static int add_source=3;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        if (args.length!=11) {
            System.out.println("Input wrong! \nCorrect usage: K_of_the_graph number_of_nodes number_deleted_nodes #iterations depth_of_deletion_update quality_sampling max_value type_source() random_jump_boolean adding_node_boolean adding_source()" +
                    "type of source: 1 if uniform, 2 if 3 well separated clusters, 3 if 3 overlapping clusters "+
            "\n type of adding source: 1 if uniform, 2 randomly from the three clusers, 3 if from just one of the cluster and changes after n/6 points (2 rounds for each source)");

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
            for (int a = 0; a < iterations; a++) {
                ArrayList<Double[]> data = new ArrayList<Double[]>();
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
                Dataset gaussian_mixture = new Dataset();
                Dataset gaussian_mixture_2 = new Dataset();
                Dataset gaussian_mixture_3 = new Dataset();
                if ((type_of_source==1)||(type_of_source==2)) {
                    Random r = new Random();
                    for (int i = 0; i < count; i++) {
                        // The value of our nodes will be an int
                        double value = r.nextDouble();
                        double value2=r.nextDouble();
                        Double[] temp = new Double[]{r.nextDouble(),r.nextDouble()};
                        nodes.add(new Node<Double[]>(String.valueOf(i), temp));
                        nodes_og.add(new Node<Double[]>(String.valueOf(i), temp));
                    }
                    gaussian_mixture.addCenter(
                            new Center(
                                    2,                          // weight
                                    new double[]{10.0, 10.0},   // center
                                    new double[]{2.0, 2.0}));   // deviation

                    gaussian_mixture_2.addCenter(
                            new Center(
                                    2,
                                    new double[]{100.0, 100.0},
                                    new double[]{2.0, 2.0}));

                    gaussian_mixture_3.addCenter(
                            new Center(
                                    2,
                                    new double[]{200.0, 200.0},
                                    new double[]{2.0, 2.0}));

                }




                if (type_of_source==3) {
                    gaussian_mixture.addCenter(
                            new Center(
                                    2,                          // weight
                                    new double[]{10.0, 10.0},   // center
                                    new double[]{3.0, 3.0}));   // deviation

                    gaussian_mixture_2.addCenter(
                            new Center(
                                    2,
                                    new double[]{5.0, 5.0},
                                    new double[]{5.0, 5.0}));

                    gaussian_mixture_3.addCenter(
                            new Center(
                                    2,
                                    new double[]{0.0, 0.0},
                                    new double[]{5.0, 5.0}));
                }
                Iterator<Double[]> iterator = gaussian_mixture.iterator();
                Iterator<Double[]> iterator_2 = gaussian_mixture_2.iterator();
                Iterator<Double[]> iterator_3 = gaussian_mixture_3.iterator();
                if ((type_of_source==2)||(type_of_source==3)) {
                    //Iterator<Double[]> iterator = gaussian_mixture.iterator();
                    for (int i = 0; i < count/3; i++) {
                        data.add(iterator.next());
                    }

                    //Iterator<Double[]> iterator_2 = gaussian_mixture_2.iterator();
                    for (int i = 0; i < count/3+1; i++) {
                        data.add(iterator_2.next());
                    }

                    //Iterator<Double[]> iterator_3 = gaussian_mixture_3.iterator();
                    for (int i = 0; i < count/3+1; i++) {
                        data.add(iterator_3.next());

                    }
                    /*
                    for (int i=0; i<data.size(); i++) {
                        int id_random=0;
                        Random rand = new Random();
                        Hashtable <Integer,Integer> already_in = new Hashtable<Integer, Integer>();
                        while (nodes.size() < data.size()) {
                            id_random=rand.nextInt(data.size() - 1);
                            if (!already_in.contains(id_random)) {
                                already_in.put(id_random,1);
                                nodes.add(new Node<Double[]>(String.valueOf(id_random),data.get(i)));
                                nodes_og.add(new Node<Double[]>(String.valueOf(id_random),data.get(i)));
                            }
                        }
                    }
                    */

                    Collections.shuffle(data);
                    //put the nodes not in sequential order
                    Random r = new Random();

                    for (int i = 0; i < count; i++) {
                        // The value of our nodes will be an int

                        Double[] temp = new Double[]{r.nextDouble(),r.nextDouble()};
                        nodes.add(new Node<Double[]>(String.valueOf(i), data.get(i)));
                        nodes_og.add(new Node<Double[]>(String.valueOf(i), data.get(i)));


                    }





                }

                //create the two arrays of nodes


                // this is for the online graph
                Brute builder_1 = new Brute<Double>();
                builder_1.setK(K);
                builder_1.setSimilarity(new SimilarityInterface<Double[]>() {

                    public double similarity(Double[] value1, Double[] value2)
                    {
                        double agg = 0;
                        for (int i = 0; i < value1.length; i++) {
                            agg += (value1[i] - value2[i]) * (value1[i] - value2[i]);
                        }
                        return (double) 1.0 / (1 + Math.sqrt(agg));
                    }

                });

                Graph<Double[]> graph_og = builder_1.computeGraph(nodes_og);
                OnlineGraph<Double[]> online_graph = new OnlineGraph<Double[]>(graph_og);
                int last_node_id=count;
                int adding_slot= number_deletion/6; //in case of switch 3, adding_slot points are added from one cluster, then the other and so on.che
                int data_source = 1;
                //start to delete nodes & add new ones
                for (int i = 0; i < number_deletion; i++) {
                    ArrayList<Node> nodes_temp = new ArrayList<Node>();
                    Node<Double[]> node2del = null;
                    int node_comparisons=0;

                    for (Node<Double[]> n : nodes) {
                        //System.out.println(""+n.id);
                        if ((Integer.parseInt(n.id)) == i) node2del = n;
                        if ((Integer.parseInt(n.id)) > i) nodes_temp.add(n);
                    }
                    if (adding_nodes==true) {
                        Double[] temp = new Double[]{0.0,0.0};

                        //create a new node:
                        if (add_source==1) {
                            Random r = new Random();
                            double value = r.nextDouble();
                            double value2=r.nextDouble();
                            temp = new Double[]{r.nextDouble(),r.nextDouble()};
                            }
                        if (add_source==2) {
                            Random r = new Random();
                            int which_gauss = r.nextInt(3);

                            if (which_gauss==0) {temp=iterator.next();}
                            if (which_gauss==1) {temp=iterator_2.next();}
                            if (which_gauss==2) {temp=iterator_3.next();}


                            }

                        if (add_source==3) {
                            if (last_node_id%adding_slot==0) {

                                data_source++;
                                if (data_source==4) data_source=1;
                            }
                            if (data_source==1) {temp=iterator.next();}
                            if (data_source==2) {temp=iterator_2.next();}
                            if (data_source==3) {temp=iterator_3.next();}

                            }
                        nodes.add(new Node<Double[]>(String.valueOf(last_node_id), temp));
                        nodes_temp.add(new Node<Double[]>(String.valueOf(last_node_id), temp));



                        //System.out.println("nodes:"+nodes);
                        //System.out.println("nodes_temp:"+nodes_temp);
                        //System.out.println("just added to the bruteforce graph the node:"+last_node_id+":"+temp[0]+","+temp[1]);
                        node_comparisons += online_graph.addNode(new Node<Double[]>(String.valueOf(last_node_id), temp));
                        last_node_id++;

                        //last_node_id++;



                    }
                    node_comparisons += graph_og.removeAndUpdate_3_depth(node2del, depth, random);
                    //comparisons.add(node_comparisons);
                    int wrong_edge = 0;

                    if ((i+1)%quality_sampling==0) {

                        //create the brute graph
                        Brute builder = new Brute<Double[]>();
                        builder.setK(K);
                        builder.setSimilarity(new SimilarityInterface<Double[]>() {

                            public double similarity(Double[] value1, Double[] value2)
                            {
                                double agg = 0;
                                for (int i = 0; i < value1.length; i++) {
                                    agg += (value1[i] - value2[i]) * (value1[i] - value2[i]);
                                }
                                return (double) 1.0 / (1 + Math.sqrt(agg));
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