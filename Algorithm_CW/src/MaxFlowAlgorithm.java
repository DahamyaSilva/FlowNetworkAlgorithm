/**
 * Student ID: W2052195
 * Student Name: Dahamya Silva
 */


import java.util.*;

/**
 * Simple implementation of Ford-Fulkerson algorithm
 */
public class MaxFlowAlgorithm {
    private FlowNetwork network;
    private List<String> steps;

    public MaxFlowAlgorithm(FlowNetwork network) {
        this.network = network;
        this.steps = new ArrayList<>();
    }

    /**
     * Compute the maximum flow
     */
    public int computeMaxFlow() {
        // Reset all flows to zero
        network.resetFlows();
        steps.clear();

        int iteration = 1;

        // Keep finding paths until we can't find anymore
        while (true) {
            // Find a path from source to destination
            List<Edge> path = findPath();

            // If no path found, we're done
            if (path == null) {
                steps.add("Maximum flow achieved.");
                break;
            }

            // Find the smallest capacity in the path
            int bottleneck = findBottleneck(path);

            // Add flow to all edges in the path
            addFlowToPath(path, bottleneck);

            // Create a path string like "0 -> 1 -> 2"
            String pathStr = createPathString(path);

            // Store this step
            steps.add("Iteration " + iteration + ":\n  Path: " + pathStr +
                    "\n  Bottleneck capacity: " + bottleneck +
                    "\n  New flow value: " + network.getFlowValue());

            iteration++;
        }

        return network.getFlowValue();
    }

    /**
     * Find a path from source to destination
     */
    private List<Edge> findPath() {
        // Keep track of visited nodes
        boolean[] visited = new boolean[network.getNumNodes()];

        // Keep track of the edge that led us to each node
        Edge[] edgeTo = new Edge[network.getNumNodes()];

        // Queue for BFS
        Queue<Integer> queue = new LinkedList<>();

        // Start at the source
        int source = network.getSource();
        int destination = network.getDestination();
        queue.add(source);
        visited[source] = true;

        // BFS to find a path
        while (!queue.isEmpty() && !visited[destination]) {
            int current = queue.poll();

            // Try all outgoing edges
            for (Edge edge : network.getOutgoingEdges(current)) {
                int next = edge.getTo();

                // If the edge has remaining capacity and we haven't visited the destination yet
                if (edge.getResidualCapacity() > 0 && !visited[next]) {
                    edgeTo[next] = edge;
                    visited[next] = true;
                    queue.add(next);
                }
            }
        }

        // If we didn't reach the destination, no path exists
        if (!visited[destination]) {
            return null;
        }

        // Construct the path
        List<Edge> path = new ArrayList<>();
        for (int v = destination; v != source; v = edgeTo[v].getFrom()) {
            path.add(0, edgeTo[v]);
        }

        return path;
    }

    /**
     * Find the bottleneck (smallest capacity) in a path
     */
    private int findBottleneck(List<Edge> path) {
        int bottleneck = Integer.MAX_VALUE;

        for (Edge edge : path) {
            bottleneck = Math.min(bottleneck, edge.getResidualCapacity());
        }

        return bottleneck;
    }

    /**
     * Add flow to all edges in a path
     */
    private void addFlowToPath(List<Edge> path, int flow) {
        for (Edge edge : path) {
            edge.addFlow(flow);
        }
    }

    /**
     * Create a string representation of a path
     */
    private String createPathString(List<Edge> path) {
        StringBuilder result = new StringBuilder();

        // Add the source
        result.append(network.getSource());

        // Add each edge's destination
        for (Edge edge : path) {
            result.append(" -> ").append(edge.getTo());
        }

        return result.toString();
    }

    /**
     * Get the steps of the algorithm
     */
    public List<String> getSteps() {
        return steps;
    }
}