/**
 * Student ID: W2052195
 * Student Name: Dahamya Silva
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Enhanced parser for reading flow network descriptions with error handling
 */
public class NetworkParser {

    public static FlowNetwork parseFromFile(String filename) throws IOException {
        // Open the file
        FileReader fileReader = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fileReader);

        int lineNumber = 0;

        try {
            // Read the first line to get number of nodes
            String line = reader.readLine();
            lineNumber++;

            if (line == null) {
                throw new IllegalArgumentException("Empty input file");
            }

            // Remove any spaces at beginning or end
            line = line.trim();

            // Check if the line contains a valid integer
            int numNodes;
            try {
                numNodes = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Line " + lineNumber + ": Number of nodes must be an integer");
            }

            // Check if the number of nodes is valid
            if (numNodes <= 0) {
                throw new IllegalArgumentException("Line " + lineNumber + ": Number of nodes must be positive");
            }

            // Make a new network with that many nodes
            FlowNetwork network = new FlowNetwork(numNodes);

            // Keep reading lines until end of file
            line = reader.readLine();
            while (line != null) {
                lineNumber++;

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    line = reader.readLine();
                    continue;
                }

                // Split the line by spaces
                String[] parts = line.trim().split("\\s+");

                // Check if line has exactly 3 parts
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Line " + lineNumber +
                            ": Expected 3 values (from to capacity), found " + parts.length);
                }

                // Parse the 3 numbers from the line
                int from, to, capacity;
                try {
                    from = Integer.parseInt(parts[0]);
                    to = Integer.parseInt(parts[1]);
                    capacity = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Line " + lineNumber +
                            ": All values must be integers");
                }

                // Check if node indices are valid
                if (from < 0 || from >= numNodes) {
                    throw new IllegalArgumentException("Line " + lineNumber +
                            ": Source node " + from + " is out of range (0-" + (numNodes-1) + ")");
                }

                if (to < 0 || to >= numNodes) {
                    throw new IllegalArgumentException("Line " + lineNumber +
                            ": Destination node " + to + " is out of range (0-" + (numNodes-1) + ")");
                }

                // Check if capacity is positive
                if (capacity < 0) {
                    throw new IllegalArgumentException("Line " + lineNumber +
                            ": Capacity must positive");
                }

                // Add this edge to our network
                network.addEdge(from, to, capacity);

                // Read next line
                line = reader.readLine();
            }

            return network;

        } catch (Exception e) {
            // Wrap any other exceptions with file information
            if (e instanceof IllegalArgumentException) {
                throw e;  // Already formatted properly
            } else {
                throw new IllegalArgumentException("Error parsing file: " + e.getMessage());
            }
        } finally {
            // Close the file when done
            reader.close();
        }
    }
}