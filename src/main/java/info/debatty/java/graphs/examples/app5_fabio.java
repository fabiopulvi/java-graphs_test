package info.debatty.java.graphs.examples;

import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;


/**
 * Created by fabio on 23/02/16.
 * The same as app2 but here:
 * - the node with id 0 disappears
 * - more than one nodes are updated
 */
public class app5_fabio {
    public static int K = 3;
    public static int count = 100;
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Generate some random nodes
        Random r = new Random();


        ArrayList<Node> nodes = new ArrayList<Node>(count);
        ArrayList<Node> nodes_never_existed = new ArrayList<Node>(count);
        for (int i = 0; i < count; i++) {
            // The value of our nodes will be an int
            int value = r.nextInt(100);
            if (i!=0) nodes_never_existed.add(new Node<Integer>(String.valueOf(i), value));
            nodes.add(new Node<Integer>(String.valueOf(i), value));

        }
        // nodes.add(new Node<Integer>(String.valueOf(11), 111));
        //nodes_never_existed.add(new Node<Integer>(String.valueOf(11), 111));
        //nodes.add(new Node<Integer>(String.valueOf(12), 122));
        // Instantiate and configure the brute-force graph building algorithm
        // The minimum is to define k (number of edges per node)
        // and a similarity metric between nodes
        Brute builder = new Brute<Integer>();
        builder.setK(K);
        builder.setSimilarity(new SimilarityInterface<Integer>() {

            public double similarity(Integer value1, Integer value2) {
                return 1.0 / (1.0 + Math.abs(value1 - value2));
            }
        });

        Brute builder2 = new Brute<Integer>();
        builder2.setK(K);
        builder2.setSimilarity(new SimilarityInterface<Integer>() {

            public double similarity(Integer value1, Integer value2) {
                return 1.0 / (1.0 + Math.abs(value1 - value2));
            }
        });

        Graph<Integer> graph2 = builder2.computeGraph(nodes_never_existed);


        // Optionaly, we can define a callback, to get some feedback...
       /* builder.setCallback(new CallbackInterface() {

            @Override
            public void call(HashMap<String, Object> data) {
                System.out.println(data);
            }

        });
*/
        Graph<Integer> graph = builder.computeGraph(nodes);

        // Display the computed neighbor lists
        /*
       for (Node n : nodes) {
            NeighborList nl = graph.get(n);
            System.out.print(n);
            System.out.println(nl);
        }*/
        //it becomes an online graph
        OnlineGraph<Integer> online_graph = new OnlineGraph<Integer>(graph);

        //the 12th node has to be deleted, the one with id 12 and coordinate 122
        //first save his neighbourlist to a temporary n1
        ;
        Node<Integer> N1 = null;
        NeighborList N1_list = null;
        //ArrayList<Node<Integer>> nodes2 = new ArrayList<Node<Integer>>();
        int flag=0;
        for (Node<Integer> nodetemp : online_graph.getNodes()) {

            if (Integer.parseInt(nodetemp.id)==0) {
                N1 = new Node<Integer>(nodetemp.id, nodetemp.value);
                N1_list = online_graph.get(nodetemp);
                break;
            }
        }
        // else
        //if (Integer.parseInt(nodetemp.id)<Integer.parseInt(N1.id)) {
        //   N1 = new Node<Integer>(nodetemp.id, nodetemp.value);
        //  N1_list= online_graph.get(nodetemp);

//            }


        System.out.println("The node to delete is N1:"+N1.id+" "+N1.value+" "+N1_list);
        online_graph.remove(N1);





        online_graph.remove(N1);
        online_graph.removeNodeFromNeighbourlist(N1);





// print the new online_graph with the updated node
/*
       for (Node<Integer> node_all : online_graph.getNodes()) {
            NeighborList nl = graph.get(node_all);
            System.out.print(node_all);
           System.out.println(nl);
        }
*/
// make a comparison with a bruteforce approach without the node N1 from the beginning

        for (Node n : nodes_never_existed) {
            NeighborList nl = graph2.get(n);

            NeighborList nl2 = online_graph.get(n);
            if (K-nl.countCommons(nl2)>0) {
                System.out.print("node: "+n);

                System.out.println("approximate method: "+nl2);
                System.out.println("    exhaustive method"+nl);

                System.out.print("the differences are: "+(Integer.toString(K-nl.countCommons(nl2)))+"\n");}


        }

/*
        System.out.println(graph.search(N1.value, K));
        System.out.println(graph.searchExhaustive(N1.value, K));
        System.out.println(online_graph.search(N1.value, K));
        System.out.println(online_graph.searchExhaustive(N1.value, K));


*/







        //Iterable <Node,NeighborList> nodes_all = online_graph.entrySet();
        //System.out.println("\n this is to nl of the node to be deleted: "+N2del_list);
        //graph.prune(0.30);
        // Display the computed neighbor lists
        /*
        for (Node n : nodes) {
            NeighborList nl = graph.get(n);
            System.out.print(n);
            System.out.println(nl);

        }
*/
        //  builder.test(nodes);
/*

        // Convert the graph to an online graph (to which we can add new nodes)
        OnlineGraph<Integer> online_graph = new OnlineGraph<Integer>(graph);

        // Now we can add a node to the graph (using a fast approximate algorithm)
        online_graph.addNode(
                new Node<Integer>(Integer.toString(11111), 1));

        nodes.add(new Node<Integer>(String.valueOf(11111), 1));



        Brute builder2 = new Brute<Integer>();
        builder2.setK(2);
        builder2.setSimilarity(new SimilarityInterface<Integer>() {

            public double similarity(Integer value1, Integer value2) {
                return 1.0 / (1.0 + Math.abs(value1 - value2));
            }
        });

        Graph<Integer> graph2 = builder2.computeGraph(nodes);

        System.out.print("\n");


      /*  for (Node n : nodes) {
            NeighborList nl = online_graph.get(n);
            System.out.print(n);
            System.out.println(nl);

        }
        System.out.println("\n now analyze differences");
        for (Node n : nodes) {
            NeighborList nl = graph2.get(n);

            NeighborList nl2 = online_graph.get(n);
            if (2-nl.countCommons(nl2)>0) {
            System.out.print("node: "+n);

            System.out.println("approximate method: "+nl2);
            System.out.println("    exhaustive method"+nl);

            System.out.print("differences are: "+(Integer.toString(2-nl.countCommons(nl2)))+"\n");}


        }
        System.out.println(graph.search(2, 4));
        System.out.println(graph.searchExhaustive(23, 2));

        */
    }
}





