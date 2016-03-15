/*
 * The MIT License
 *
 * Copyright 2015 Thibault Debatty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.debatty.java.graphs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * k-nn graph, represented as a mapping node => neighborlist.
 *
 * @author Thibault Debatty
 * @param <T> The type of nodes value
 */
public class Graph<T> implements GraphInterface<T>, Serializable {

    private static final double DEFAULT_EXPANSION = 1.2;
    private static final int DEFAULT_SPEEDUP = 4;

    protected HashMap<Node<T>, NeighborList> map;
    protected SimilarityInterface<T> similarity;
    protected int k = 10;

    @Override
    public SimilarityInterface<T> getSimilarity() {
        return similarity;
    }

    @Override
    public void setSimilarity(SimilarityInterface<T> similarity) {
        this.similarity = similarity;
    }

    @Override
    public int getK() {
        return k;
    }

    @Override
    public void setK(int k) {
        this.k = k;
    }

    public Graph(int k) {
        this.k = k;
        this.map = new HashMap<Node<T>, NeighborList>();
    }

    public Graph() {
        this.map = new HashMap<Node<T>, NeighborList>();
    }

    /**
     * Get the neighborlist of this node
     *
     * @param node
     * @return the neighborlist of this node
     */
    @Override
    public NeighborList get(Node node) {
        return map.get(node);
    }

    /**
     * Get the first node in the graph.
     *
     * @return The first node in the graph
     * @throws NoSuchElementException if the graph is empty...
     */
    public final Node<T> first() throws NoSuchElementException {
        return this.getNodes().iterator().next();
    }

    /**
     * Remove from the graph all edges with a similarity lower than threshold.
     *
     * @param threshold
     */
    @Override
    public void prune(double threshold) {
        for (NeighborList nl : map.values()) {

            // We cannot remove inside the loop
            // => do it in 2 steps:
            ArrayList<Neighbor> to_remove = new ArrayList<Neighbor>();
            for (Neighbor n : nl) {
                if (n.similarity < threshold) {
                    to_remove.add(n);
                }
            }

            nl.removeAll(to_remove);
        }
    }

    /**
     * Split the graph in connected components (usually you will first prune the
     * graph to remove "weak" edges).
     *
     * @return
     */
    @Override
    public ArrayList<Graph<T>> connectedComponents() {
        ArrayList<Graph<T>> subgraphs = new ArrayList<Graph<T>>();
        ArrayList<Node<T>> nodes_to_process = new ArrayList<Node<T>>(map.keySet());

        for (int i = 0; i < nodes_to_process.size(); i++) {
            Node n = nodes_to_process.get(i);
            if (n == null) {
                continue;
            }
            Graph<T> subgraph = new Graph<T>();
            subgraphs.add(subgraph);

            addAndFollow(subgraph, n, nodes_to_process);
        }

        return subgraphs;
    }

    private void addAndFollow(Graph<T> subgraph, Node<T> node, ArrayList<Node<T>> nodes_to_process) {
        nodes_to_process.remove(node);

        NeighborList neighborlist = this.get(node);
        subgraph.put(node, neighborlist);

        if (neighborlist == null) {
            return;
        }

        for (Neighbor neighbor : this.get(node)) {
            if (!subgraph.containsKey(neighbor.node)) {
                addAndFollow(subgraph, neighbor.node, nodes_to_process);
            }
        }
    }

    /**
     * Computes the strongly connected sub-graphs (where every node is reachable
     * from every other node) using Tarjan's algorithm, which has computation
     * cost O(n).
     *
     * @return
     */
    @Override
    public ArrayList<Graph<T>> stronglyConnectedComponents() {
        Stack<Node> stack = new Stack<Node>();
        Index index = new Index();
        HashMap<Node, NodeProperty> bookkeeping = new HashMap<Node, NodeProperty>(map.size());

        ArrayList<Graph<T>> connected_components = new ArrayList<Graph<T>>();

        for (Node n : map.keySet()) {

            if (bookkeeping.containsKey(n)) {
                // This node was already processed...
                continue;
            }

            ArrayList<Node> connected_component = this.strongConnect(n, stack, index, bookkeeping);

            if (connected_component == null) {
                continue;
            }

            // We found a connected component
            Graph<T> subgraph = new Graph<T>(connected_component.size());
            for (Node node : connected_component) {
                subgraph.put(node, this.get(node));
            }
            connected_components.add(subgraph);

        }

        return connected_components;
    }

    private ArrayList<Node> strongConnect(Node v, Stack<Node> stack, Index index, HashMap<Node, NodeProperty> bookkeeping) {
        bookkeeping.put(v, new NodeProperty(index.Value(), index.Value()));
        index.Inc();
        stack.add(v);

        for (Neighbor neighbor : this.get(v)) {
            Node w = neighbor.node;

            if (!this.containsKey(w) || this.get(w) == null) {
                continue;
            }

            if (!bookkeeping.containsKey(w)) {
                strongConnect(w, stack, index, bookkeeping);
                bookkeeping.get(v).lowlink = Math.min(
                        bookkeeping.get(v).lowlink,
                        bookkeeping.get(w).lowlink);

            } else if (bookkeeping.get(neighbor.node).onstack) {
                bookkeeping.get(v).lowlink = Math.min(
                        bookkeeping.get(v).lowlink,
                        bookkeeping.get(w).index);

            }
        }

        if (bookkeeping.get(v).lowlink == bookkeeping.get(v).index) {
            ArrayList<Node> connected_component = new ArrayList<Node>();

            Node w;
            do {
                w = stack.pop();
                bookkeeping.get(w).onstack = false;
                connected_component.add(w);
            } while (v != w);

            return connected_component;
        }

        return null;
    }

    @Override
    public NeighborList put(Node<T> node, NeighborList neighborlist) {
        return map.put(node, neighborlist);
    }

    @Override
    public boolean containsKey(Node node) {
        return map.containsKey(node);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Iterable<Map.Entry<Node<T>, NeighborList>> entrySet() {
        return map.entrySet();
    }

    private static class Index {

        private int value;

        public int Value() {
            return this.value;
        }

        public void Inc() {
            this.value++;
        }
    }

    private static class NodeProperty {

        public int index;
        public int lowlink;
        public boolean onstack;

        public NodeProperty(int index, int lowlink) {
            this.index = index;
            this.lowlink = lowlink;
            this.onstack = true;
        }
    };

    public Iterable<Node<T>> getNodes() {
        return map.keySet();
    }

    /**
     *
     * @param query
     * @param K
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public NeighborList searchExhaustive(T query, int K)
            throws InterruptedException, ExecutionException {

        // Read all nodes
        ArrayList<Node<T>> nodes = new ArrayList<Node<T>>();
        for (Node<T> node : getNodes()) {
            nodes.add(node);
        }

        int procs = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(procs);
        List<Future<NeighborList>> results = new ArrayList();

        for (int i = 0; i < procs; i++) {
            int start = nodes.size() / procs * i;
            int stop = Math.min(nodes.size() / procs * (i + 1), nodes.size());

            results.add(pool.submit(new SearchTask(nodes, query, start, stop)));
        }

        // Reduce
        NeighborList neighbors = new NeighborList(K);
        for (Future<NeighborList> future : results) {
            neighbors.addAll(future.get());
        }
        pool.shutdown();
        return neighbors;
    }

    /**
     * Approximate fast graph based search, as published in "Fast Online k-nn
     * Graph Building" by Debatty et al.
     * Default speedup is 4.
     *
     * @see <a href="http://arxiv.org/abs/1602.06819">Fast Online k-nn Graph
     * Building</a>
     * @param query
     * @param k search K neighbors
     * @return
     */
    @Override
    public final NeighborList search(final T query, final int k) {
        return search(query, k, DEFAULT_SPEEDUP);
    }

    /**
     * Approximate fast graph based search, as published in "Fast Online k-nn
     * Graph Building" by Debatty et al.
     *
     * @see <a href="http://arxiv.org/abs/1602.06819">Fast Online k-nn Graph
     * Building</a>
     * @param query
     * @param k search k neighbors
     * @param speedup speedup for searching (> 1, default 4)
     * @return
     */
    @Override
    public final NeighborList search(
            final T query, final int k, final double speedup) {

        return this.search(query, k, speedup, DEFAULT_EXPANSION);
    }

    /**
     * Approximate fast graph based search, as published in "Fast Online k-nn
     * Graph Building" by Debatty et al.
     *
     * @see <a href="http://arxiv.org/abs/1602.06819">Fast Online k-nn Graph
     * Building</a>
     * @param query query point
     * @param k number of neighbors to find (the K from K-nn search)
     * @param speedup (default: 4)
     * @param expansion (default: 1.2)
     *
     * @return
     */
    @Override
    public final NeighborList search(
            final T query,
            final int k,
            final double speedup,
            final double expansion) {

        if (speedup <= 1.0) {
            throw new InvalidParameterException("Speedup should be > 1.0");
        }

        int max_similarities = (int) (map.size() / speedup);

        // Looking for more nodes than this graph contains...
        // Or fall back to exhaustive search
        if (k >= map.size()
                || max_similarities >= map.size()) {

            NeighborList nl = new NeighborList(k);
            for (Node<T> node : map.keySet()) {
                nl.add(
                        new Neighbor(
                                node,
                                similarity.similarity(
                                        query,
                                        node.value)));
            }
            return nl;
        }

        // Node => Similarity with query node
        HashMap<Node<T>, Double> visited_nodes = new HashMap<Node<T>, Double>();
        int computed_similarities = 0;
        double global_highest_similarity = 0;
        ArrayList<Node<T>> nodes = new ArrayList<Node<T>>(map.keySet());
        Random rand = new Random();

        while (true) { // Restart...
            //System.out.println("Restart...");
            if (computed_similarities >= max_similarities) {
                break;
            }

            // Select a random node from the graph
            Node<T> current_node = nodes.get(rand.nextInt(nodes.size()));

            // Already been here => restart
            if (visited_nodes.containsKey(current_node)) {
                continue;
            }

            // starting point too far (similarity too small) => restart!
            double restart_similarity = similarity.similarity(
                    query,
                    current_node.value);
            computed_similarities++;
            if (restart_similarity < global_highest_similarity / expansion) {
                continue;
            }

            while (computed_similarities < max_similarities) {

                NeighborList nl = this.get(current_node);

                // Node has no neighbor => restart!
                if (nl == null) {
                    break;
                }

                // Check all neighbors and try to find a node with higher
                // similarity
                Iterator<Neighbor> Y_nl_iterator = nl.iterator();
                Node<T> node_higher_similarity = null;
                while (Y_nl_iterator.hasNext()) {

                    Node<T> other_node = Y_nl_iterator.next().node;

                    if (visited_nodes.containsKey(other_node)) {
                        continue;
                    }

                    // Compute similarity to query
                    double sim = similarity.similarity(
                            query,
                            other_node.value);
                    computed_similarities++;
                    visited_nodes.put(other_node, sim);

                    // If this node provides an improved similarity, keep it
                    if (sim > restart_similarity) {
                        node_higher_similarity = other_node;
                        restart_similarity = sim;

                        // early break...
                        break;
                    }
                }

                // No node provides higher similarity
                // => we reached the end of this track...
                // => restart!
                if (node_higher_similarity == null) {

                    if (restart_similarity > global_highest_similarity) {
                        global_highest_similarity = restart_similarity;
                    }
                    break;
                }

                current_node = node_higher_similarity;
            }
        }

        NeighborList neighbor_list = new NeighborList(k);
        for (Map.Entry<Node<T>, Double> entry : visited_nodes.entrySet()) {
            neighbor_list.add(new Neighbor(entry.getKey(), entry.getValue()));
        }
        return neighbor_list;
    }

    /**
     * Writes the graph as a GEXF file (to be used in Gephi, for example).
     *
     * @param filename
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public void writeGEXF(String filename) throws FileNotFoundException, IOException {
        Writer out = new OutputStreamWriter(new FileOutputStream(filename));
        out.write(GEXF_HEADER);

        // Write nodes
        out.write("<nodes>\n");
        for (Node node : map.keySet()) {
            out.write("<node id=\"" + node.id + "\" label=\"" + node.id + "\" />\n");
        }
        out.write("</nodes>\n");

        // Write edges
        out.write("<edges>\n");
        int i = 0;
        for (Node source : map.keySet()) {
            for (Neighbor target : this.get(source)) {
                out.write("<edge id=\"" + i + "\" source=\"" + source.id + "\" "
                        + "target=\"" + target.node.id + "\" "
                        + "weight=\"" + target.similarity + "\" />\n");
                i++;
            }
        }

        out.write("</edges>");

        // End the file
        out.write("</graph>\n"
                + "</gexf>");
        out.close();
    }

    private static final String GEXF_HEADER
            = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<gexf xmlns=\"http://www.gexf.net/1.2draft\" version=\"1.2\">\n"
            + "<meta>\n"
            + "<creator>info.debatty.java.graphs.Graph</creator>\n"
            + "<description></description>\n"
            + "</meta>\n"
            + "<graph mode=\"static\" defaultedgetype=\"directed\">\n";

    private class SearchTask implements Callable<NeighborList> {

        private final ArrayList<Node<T>> nodes;
        private final T query;
        private final int start;
        private final int stop;

        SearchTask(
                ArrayList<Node<T>> nodes,
                T query,
                int start,
                int stop) {

            this.nodes = nodes;
            this.query = query;
            this.start = start;
            this.stop = stop;
        }

        public NeighborList call() throws Exception {
            NeighborList nl = new NeighborList(k);
            for (int i = start; i < stop; i++) {
                Node<T> other = nodes.get(i);
                nl.add(new Neighbor(
                        other,
                        similarity.similarity(query, other.value)));
            }
            return nl;

        }

    }

    /**
     * remove a node from the graph
     * @param node to delete
     * #Fabio
     */

    public NeighborList remove (Node<T> node) {
        NeighborList nl = map.remove(node);

        return nl;

    }

    /**
     * remove a node from the graph and update the neighbourlist
     * deleting the node from the neighbourlists without updating it
     *
     * @param node to delete
     * #Fabio
     */

    public void removeNodeFromNeighbourlist (Node<T> node) {
        //scroll the NLs
        for (NeighborList nl : map.values()) {
            //can't do it without scrolling because the nl is composed of neighbors and not node
            //we would miss the distance measure and the "remove" function would not
            //find the node
        ArrayList<Neighbor> to_remove = new ArrayList<Neighbor>();
        for (Neighbor n : nl) {
            if (n.node.equals(node)) {
                to_remove.add(n);
            }
        }

            boolean result= nl.removeAll(to_remove);
           // System.out.println("\n looking for:" +node+" it says: "+result+" and the nl is: "+nl);
        }
    }

    /**
     * remove a node from the graph and update the neighbourlist
     * deleting the node from the neighbourlists and updating it
     *
     * @param node to delete
     *
     *  @return number of modified nodes
     * #Fabio
     *
     */

    public int removeAndUpdate_flat (Node<T> node) {
        int modified=0; //number of nodes modified
        // array of the nodes to update (out of the scrolling)
        ArrayList<Node<T>> nodes2update_array = new ArrayList<Node<T>>();
        //remove the node from all the neighbourlists
        this.removeNodeFromNeighbourlist(node);
        //now scroll the nodes and identify which ones has been involved in the deletion
        for (Node <T> node2update : map.keySet()) {
            if (map.get(node2update).size()==k-1) nodes2update_array.add(node2update);
        }
        //now scroll the nodes and update them
        for (Node <T> node2update : nodes2update_array) {
            NeighborList nl = this.search(node2update.value, k);
            map.put(node2update, nl);
            modified++;
        }
        return modified;
    }
    /*
    public void removeAndUpdate_flat (Node<T> node) {
        //scroll the NLs, this time from the nodes
        this.remove(node);
         // array of the nodes to update
        ArrayList<Node<T>> nodes2update_array = new ArrayList<Node<T>>();
        for (Node <T> node2update : map.keySet()) {

                //can't do it without scrolling because the nl is composed of neighbors and not node
                //we would miss the distance measure and the "remove" function would not
                //find the node
                boolean flag = false; //this flag signals if the nl has to be updated
                ArrayList<Neighbor> to_remove = new ArrayList<Neighbor>();

                for (Neighbor n : map.get(node2update)) {

                    if (n.node.equals(node)) {
                        nodes2update_array.add(node2update);
                        to_remove.add(n);
                        System.out.println("\n just found the node: "+n.node+" into the nl of: "+node2update);
                        flag=true;
                        break;
                    }
                }
                if (flag==true) {
                    NeighborList nl = this.search(node2update.value, k);
                    System.out.println("\n For it just found: "+nl);
                    map.put(node2update, nl);
                    System.out.println("\n now its nl is: "+map.get(node2update));
                }

                // System.out.println("\n looking for:" +node+" it says: "+result+" and the nl is: "+nl);

        }
        for (Node <T> node2update : nodes2update_array) {
            NeighborList nl = this.search(node2update.value, k);
            System.out.println("\n For the node: " + node2update + " just found: " + nl);
            map.put(node2update, nl);
            System.out.println("\n now its nl is: " + map.get(node2update));
        }
    }


    public NeighborList removeNodeFromNeighbourlist (Node<T> node) {
        //hashmap of the nodes to update

        //iterate all the nodes
        for (Node<T> graph_node : map.keySet()) {
            //look in their nl
            NeighborList nl = map.get(graph_node);
            //search for the node
            for (Neighbor n : nl) {
                if (n.node.id.equals(node.id)) {
                    //backup this node
                    Node node_temp= new Node<T> (graph_node.id, graph_node.value);
                    NeighborList nl_temp = nl;

                }
            }

        }



        //finally remove the node from the graph
        return map.remove(node);

    }
 */
}
