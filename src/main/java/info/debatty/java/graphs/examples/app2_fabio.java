package info.debatty.java.graphs.examples;

import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by fabio on 23/02/16.
 */
public class app2_fabio {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Generate some random nodes
        Random r = new Random();
        int count = 10;

        ArrayList<Node> nodes = new ArrayList<Node>(count);
        for (int i = 0; i < count; i++) {
            // The value of our nodes will be an int
            nodes.add(new Node<Integer>(String.valueOf(i), r.nextInt(10)));
        }
        nodes.add(new Node<Integer>(String.valueOf(11), 111));
        nodes.add(new Node<Integer>(String.valueOf(12), 122));
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
       for (Node n : nodes) {
            NeighborList nl = graph.get(n);
            System.out.print(n);
            System.out.println(nl);
        }

        OnlineGraph<Integer> online_graph = new OnlineGraph<Integer>(graph);

        //the 12th node has to be deleted, the one with id 12 and coordinate 122
        //first save his neighbourlist to a temporary n1
    ;
        Node<Integer> N1 = null;
        NeighborList N1_list = null;
        ArrayList<Node<Integer>> nodes2 = new ArrayList<Node<Integer>>();
        for (Node<Integer> nodetemp : online_graph.getNodes()) {
            if (Integer.parseInt(nodetemp.id)>11) {
                N1 = new Node<Integer>(nodetemp.id, nodetemp.value);
                N1_list= online_graph.get(nodetemp);
                break;
            }
        }

        System.out.println("The node to delete is:"+N1.id+" "+N1.value+" "+N1_list);


        //Let's find now the nodes which have this value among their entries
        for (Node<Integer> nodetemp : online_graph.getNodes()) {
            NeighborList nl2update=null;
            NeighborList nl_updated;
            ArrayList<Neighbor> nl_updated_array = new ArrayList<Neighbor>();

            NeighborList nl_temp = graph.get(nodetemp);
            ArrayList other_values = new ArrayList();
            for (Neighbor n : nl_temp) {
                if (n.node.id.equals(N1.id)) {
                    System.out.println("This node has N1 as neighbour: "+nodetemp.id);
                    System.out.println("Infact its nl is: "+nl_temp);
                    nl2update=nl_temp;
                   // System.out.println("Infact its nl is: "+nl2update);
                }
            }

            //For the moment, just change the neighbourlist with a trivial point, then we will
            //how to find the replacements
            if (nl2update!=null) {
                for (Neighbor n : nl2update) {
                    if (!n.node.id.equals(N1.id))
                        nl_updated_array.add(n);
                    else { nl_updated_array.add(new Neighbor(new Node(String.valueOf(13), 133), 0.33)); //superdupa toy node!

                    }
                }
                System.out.println("\n The updated nl is: " + nl_updated_array);
            }
        //preparing the new version of the modified node




        //let's delete the oldest version
        }







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





