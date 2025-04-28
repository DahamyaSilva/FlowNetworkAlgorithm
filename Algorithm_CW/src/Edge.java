/**
 * Student ID: W2052195
 * Student Name: Dahamya Silva
 */

/**
 * Edge class representing a directed edge in the flow network
 */
class Edge {
    private final int from;
    private final int to;
    private final int capacity;
    private int flow;
    private Edge reverseEdge;

    public Edge(int from, int to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public void setReverseEdge(Edge reverseEdge) {
        this.reverseEdge = reverseEdge;
    }

    public Edge getReverseEdge() {
        return reverseEdge;
    }

    public int getResidualCapacity() {
        return capacity - flow;
    }

    public void addFlow(int amount) {
        flow += amount;
        reverseEdge.flow -= amount;
    }

    @Override
    public String toString() {
        return "Edge " + from + " -> " + to + ": flow " + flow + "/" + capacity;
    }
}