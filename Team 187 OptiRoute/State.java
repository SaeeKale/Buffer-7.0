package model;

public class State {

    private Node node;
    private double cost;
    private double time;
    private int transfers;

    public State(Node node, double cost, double time, int transfers) {
        this.node = node;
        this.cost = cost;
        this.time = time;
        this.transfers = transfers;
    }

    public Node getNode() { return node; }
    public double getCost() { return cost; }
    public double getTime() { return time; }
    public int getTransfers() { return transfers; }
}