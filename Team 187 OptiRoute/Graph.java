package graph;

import model.*;
import java.util.*;

public class Graph {

    private Map<Node, List<Edge>> adj = new HashMap<>();

    public void addNode(Node node) {
        adj.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(Node src, Edge edge) {

        if (src == null || edge == null) {
            throw new IllegalArgumentException("Null node or edge");
        }

        // 🔥 AUTO-FIX: ensure node exists
        adj.putIfAbsent(src, new ArrayList<>());

        adj.get(src).add(edge);
    }

    public List<Edge> getNeighbors(Node node) {
        return adj.getOrDefault(node, new ArrayList<>());
    }

    public Set<Node> getAllNodes() {
        return adj.keySet();
    }
}