/**
 * Student ID: W2052195
 * Student Name: Dahamya Silva
 */


import java.util.ArrayList;
import java.util.List;

/**
 * FlowNetwork class representing a flow network using adjacency lists
 */
public class FlowNetwork {
    private final int numNodes;
    private final List<Edge>[] adjacencyList;
    private final List<Edge> edges;
    private final int source;
    private final int destination;

    public FlowNetwork(int numNodes) {
        this.numNodes = numNodes;
        this.source = 0;  // Source is node 0
        this.destination = numNodes - 1;  // Destination is node (n-1)

        // Initialize adjacency list
        adjacencyList = new List[numNodes];
        for (int i = 0; i < numNodes; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        edges = new ArrayList<>();
    }

    public void addEdge(int from, int to, int capacity) {
        Edge forward = new Edge(from, to, capacity);
        Edge backward = new Edge(to, from, 0);

        forward.setReverseEdge(backward);
        backward.setReverseEdge(forward);

        adjacencyList[from].add(forward);
        adjacencyList[to].add(backward);

        edges.add(forward);
    }

    public List<Edge> getOutgoingEdges(int node) {
        return adjacencyList[node];
    }

    public List<Edge> getAllEdges() {
        return edges;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public int getFlowValue() {
        int totalFlow = 0;
        for (Edge edge : adjacencyList[source]) {
            totalFlow += edge.getFlow();
        }
        return totalFlow;
    }

    public void resetFlows() {
        for (Edge edge : edges) {
            edge.setFlow(0);
        }
    }
}