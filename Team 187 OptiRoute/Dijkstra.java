package algorithm;

import model.*;
import graph.*;

import java.util.*;

public class Dijkstra {

    public static PathResult shortestPath(Graph graph,
                                          Node source,
                                          Node destination,
                                          double wCost,
                                          double wTime,
                                          double wTransfer) {

        // Distance map (best known state per node)
        Map<Node, State> dist = new HashMap<>();

        // Parent tracking (for path reconstruction)
        Map<Node, Node> parent = new HashMap<>();

        // NEW: track which edge was used to reach a node
        Map<Node, Edge> parentEdge = new HashMap<>();

        // Priority queue based on weighted score
        PriorityQueue<State> pq = new PriorityQueue<>(
                Comparator.comparingDouble(s ->
                        score(s, wCost, wTime, wTransfer))
        );

        // Initialize source
        State start = new State(source, 0, 0, 0);
        dist.put(source, start);
        pq.add(start);

        while (!pq.isEmpty()) {

            State curr = pq.poll();
            Node u = curr.getNode();

            // Early exit if destination reached
            if (u.equals(destination)) break;

            for (Edge edge : graph.getNeighbors(u)) {

                Node v = edge.getDest();

                double newCost = curr.getCost() + edge.getCost();
                double newTime = curr.getTime() + edge.getTime();

                // ✅ YOUR RULE: every move = transfer
                int newTransfers = curr.getTransfers() + 1;

                State next = new State(v, newCost, newTime, newTransfers);

                if (!dist.containsKey(v) ||
                        score(next, wCost, wTime, wTransfer) <
                        score(dist.get(v), wCost, wTime, wTransfer)) {

                    dist.put(v, next);
                    parent.put(v, u);
                    parentEdge.put(v, edge); // ⭐ critical fix
                    pq.add(next);
                }
            }
        }

        // If destination unreachable
        if (!dist.containsKey(destination)) return null;

        // ---------------- PATH RECONSTRUCTION ----------------
        List<Node> path = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        Node step = destination;

        while (step != null) {
            path.add(step);

            Edge e = parentEdge.get(step);
            if (e != null) {
                edges.add(e);
            }

            step = parent.get(step);
        }

        Collections.reverse(path);
        Collections.reverse(edges);

        State end = dist.get(destination);

        return new PathResult(
                path,
                edges,
                end.getCost(),
                end.getTime(),
                end.getTransfers()
        );
    }

    // ---------------- SCORE FUNCTION ----------------
    private static double score(State s,
                                double wc,
                                double wt,
                                double wtr) {

        return (s.getCost() * wc) +
               (s.getTime() * wt) +
               (s.getTransfers() * wtr);
    }
}