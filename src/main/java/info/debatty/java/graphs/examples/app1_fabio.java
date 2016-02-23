package info.debatty.java.graphs.examples;

import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by fabio on 23/02/16.
 */
public class app1_fabio {


    public static void main(String[] args) {

        // Generate some random nodes
        Random r = new Random();
        int count = 10;

        ArrayList<Node> nodes = new ArrayList<Node>(count);
        for (int i = 0; i < count; i++) {
            // The value of our nodes will be an int
            nodes.add(new Node<Integer>(String.valueOf(i), r.nextInt(100)));
        }

        // Instantiate and configure the brute-force graph building algorithm
        // The minimum is to define k (number of edges per node)
        // and a similarity metric between nodes
        Brute builder = new Brute<Integer>();
        builder.setK(1);
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
        for (Node n : nodes) {
            NeighborList nl = graph.get(n);
            System.out.print(n);
            System.out.println(nl);
        }

        graph.prune(0.30);
        // Display the computed neighbor lists
        for (Node n : nodes) {
            NeighborList nl = graph.get(n);
            System.out.print(n);
            System.out.println(nl);
        }



        ArrayList<Graph<Integer>> connectedComponents = graph.connectedComponents();
        System.out.println(connectedComponents.size());
        System.out.println(connectedComponents.get(0));
    }
}





