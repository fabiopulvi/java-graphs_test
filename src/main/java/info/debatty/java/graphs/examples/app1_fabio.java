package info.debatty.java.graphs.examples;

import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by fabio on 23/02/16.
 */
public class app1_fabio {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Generate some random nodes
        Random r = new Random();
        int count = 10;

        ArrayList<Node> nodes = new ArrayList<Node>(count);
        for (int i = 0; i < count; i++) {
            // The value of our nodes will be an int
            nodes.add(new Node<Integer>(String.valueOf(i), r.nextInt(10)));
        }

        // Instantiate and configure the brute-force graph building algorithm
        // The minimum is to define k (number of edges per node)
        // and a similarity metric between nodes
        Brute builder = new Brute<Integer>();
        builder.setK(2);
        builder.setSimilarity(new SimilarityInterface<Integer>() {

            public double similarity(Integer value1, Integer value2) {
                return 1.0 / (1.0 + Math.abs(value1 - value2));
            }
        });



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
       /* for (Node n : nodes) {
            NeighborList nl = graph.get(n);
            System.out.print(n);
            System.out.println(nl);
        }
*/
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

        }*/
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
    }
}





