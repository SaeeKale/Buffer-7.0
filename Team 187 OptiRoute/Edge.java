package model;

public class Edge {

    private Node dest;
    private String mode;
    private double cost;
    private double time;
    private double distance; // ✅ NEW

    public Edge(Node dest, String mode,
                double cost, double time,
                double distance) {

        this.dest = dest;
        this.mode = mode;
        this.cost = cost;
        this.time = time;
        this.distance = distance;
    }

    public Node getDest() { return dest; }
    public String getMode() { return mode; }
    public double getCost() { return cost; }
    public double getTime() { return time; }
    public double getDistance() { return distance; }
}