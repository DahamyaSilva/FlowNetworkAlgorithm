/**
 * Student ID: W2052195
 * Student Name: Dahamya Silva
 */


import java.io.IOException;

/**
 * Main program to solve maximum flow problems
 */
public class MaxFlowSolver {

    public static void main(String[] args) {
        String inputFile = "src/bridge_3.txt";

        try {
            // Parse the network from the input file
            System.out.println("Reading network from " + inputFile);
            FlowNetwork network = NetworkParser.parseFromFile(inputFile);

            // Print network information
            System.out.println("Network information:");
            System.out.println("- Number of nodes: " + network.getNumNodes());
            System.out.println("- Number of edges: " + network.getAllEdges().size());
            System.out.println("- Source node: " + network.getSource());
            System.out.println("- Target node: " + network.getDestination());

            // Compute the maximum flow
            MaxFlowAlgorithm algorithm = new MaxFlowAlgorithm(network);
            int maxFlow = algorithm.computeMaxFlow();

            // Print the solution
            System.out.println("\n=== MAXIMUM FLOW SOLUTION ===");
            System.out.println("Maximum flow value: " + maxFlow);

            // Print algorithm steps
            System.out.println("\n=== ALGORITHM STEPS ===");
            for (String step : algorithm.getSteps()) {
                System.out.println(step);
            }

            // Print final flow configuration
            System.out.println("\n=== FINAL FLOW CONFIGURATION ===");
            for (Edge edge : network.getAllEdges()) {
                System.out.println(edge);
            }

        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}