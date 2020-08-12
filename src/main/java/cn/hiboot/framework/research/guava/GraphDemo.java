package cn.hiboot.framework.research.guava;

import com.google.common.graph.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class GraphDemo {

    @Test
    public void graph() {
        MutableGraph<Integer> graph = GraphBuilder.directed().build();
        graph.addNode(1);
        graph.putEdge(2, 3);  // also adds nodes 2 and 3 if not already present
        Set<Integer> successorsOfTwo = graph.successors(2); // returns {3}
        System.out.println(successorsOfTwo);
        graph.putEdge(3, 4);  // no effect; Graph does not support parallel edges
        System.out.println(graph);
        System.out.println(graph.nodes());
        System.out.println(graph.nodes().contains(1));//图上存在2
        System.out.println(graph.successors(2).contains(3));//图上存在边1->2
    }

    @Test
    public void valueGraph() {
        MutableValueGraph<Integer, Double> weightedGraph = ValueGraphBuilder.directed().build();
        weightedGraph.addNode(1);
        weightedGraph.putEdgeValue(2, 3, 1.5);  // also adds nodes 2 and 3 if not already present
        weightedGraph.putEdgeValue(3, 5, 1.5);  // edge values (like Map values) need not be unique
        weightedGraph.putEdgeValue(2, 3, 2.0);  // updates the value for (2,3) to 2.0
        System.out.println(weightedGraph);
        Set<EndpointPair<Integer>> edges = weightedGraph.edges();
        for (EndpointPair<Integer> endpointPair : edges) {
            System.out.println(weightedGraph.edgeValue(endpointPair.source(), endpointPair.target()));
            System.out.println(weightedGraph.edgeValueOrDefault(endpointPair.source(), 9, 1.0));
        }
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed().nodeOrder(ElementOrder.<String>natural()).allowsSelfLoops(true).build();
        graph.putEdgeValue("A", "B", 10);
        graph.putEdgeValue("A", "C", 3);
        graph.putEdgeValue("A", "D", 20);
        graph.putEdgeValue("B", "D", 5);
        graph.putEdgeValue("C", "B", 2);
        graph.putEdgeValue("C", "E", 15);
        graph.putEdgeValue("D", "E", 11);
//        DijkstraSolve.dijkstra("A", "D", graph);
    }

    @Test
    public void netWork() {

        MutableNetwork<Integer, String> network = NetworkBuilder.directed().build();
        network.addNode(1);
        network.addEdge(2, 3, "2->3");  // also adds nodes 2 and 3 if not already present

        Set<Integer> successorsOfTwo = network.successors(2);  // returns {3}
        System.out.println(successorsOfTwo);
        Set<String> outEdgesOfTwo = network.outEdges(2);   // returns {"2->3"}
        System.out.println(outEdgesOfTwo);

        network.addEdge(2, 3, "2->3 too");  // throws; Network disallows parallel edges
        // by default
        network.addEdge(2, 3, "2->3");  // no effect; this edge is already present
        // and connecting these nodes in this order

        Set<String> inEdgesOfFour = network.inEdges(4); // throws; node not in graph
        System.out.println(inEdgesOfFour);
    }

}

