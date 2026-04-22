package model;

import java.util.List;

public class PathResult {

    private List<Node> path;
    private List<Edge> edges;
    private double cost;
    private double time;
    private int transfers;

    public PathResult(List<Node> path,
                      List<Edge> edges,
                      double cost,
                      double time,
                      int transfers) {

        this.path = path;
        this.edges = edges;
        this.cost = cost;
        this.time = time;
        this.transfers = transfers;
    }

    public List<Node> getPath() { return path; }
    public List<Edge> getEdges() { return edges; }

    public double getTotalCost() { return cost; }
    public double getTotalTime() { return time; }
    public int getTotalTransfers() { return transfers; }
}