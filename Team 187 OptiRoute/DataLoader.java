package data;

import model.*;
import graph.*;

import java.util.*;

public class DataLoader {

    // ---------------- CITY MODEL ----------------
    static class City {
        String name;
        double lat, lon;
        boolean metro;

        City(String name, double lat, double lon, boolean metro) {
            this.name = name;
            this.lat = lat;
            this.lon = lon;
            this.metro = metro;
        }
    }

    // ---------------- UTIL ----------------
    private static String norm(String s) {
        return s.trim().toLowerCase();
    }

    private static final List<City> cities = Arrays.asList(
            new City("Pune",18.5204,73.8567,false),
            new City("Mumbai",19.0760,72.8777,true),
            new City("Delhi",28.7041,77.1025,true),
            new City("Bangalore",12.9716,77.5946,true),
            new City("Chennai",13.0827,80.2707,true),
            new City("Hyderabad",17.3850,78.4867,true),
            new City("Kolkata",22.5726,88.3639,true),
            new City("Guwahati",26.1445,91.7362,false),
            new City("Jaipur",26.9124,75.7873,false),
            new City("Lucknow",26.8467,80.9462,false),
            new City("Patna",25.5941,85.1376,false),
            new City("Ranchi",23.3441,85.3096,false),
            new City("Surat",21.1702,72.8311,false),
            new City("Ahmedabad",23.0225,72.5714,false)
    );

    // ---------------- MAIN LOAD ----------------
    public static Graph loadGraph() {

        Graph g = new Graph();

        Map<String, Node> train = new HashMap<>();
        Map<String, Node> bus = new HashMap<>();
        Map<String, Node> air = new HashMap<>();

        // ---------------- CREATE NODES ----------------
        for (City c : cities) {

            Node t = new Node(c.name + "_T", c.name + " Station",
                    "TRAIN", c.lat, c.lon);

            Node b = new Node(c.name + "_B", c.name + " Bus Stand",
                    "BUS", c.lat, c.lon);

            train.put(norm(c.name), t);
            bus.put(norm(c.name), b);

            g.addNode(t);
            g.addNode(b);

            // airport only for metro cities
            if (c.metro) {
                Node a = new Node(c.name + "_A", c.name + " Airport",
                        "AIRPORT", c.lat, c.lon);

                air.put(norm(c.name), a);
                g.addNode(a);

                // transfer train <-> air
                g.addEdge(t, new Edge(a, "TRANSFER", 200, 1, 0));
                g.addEdge(a, new Edge(t, "TRANSFER", 200, 1, 0));
            }

            // transfer train <-> bus
            g.addEdge(t, new Edge(b, "TRANSFER", 50, 1, 0));
            g.addEdge(b, new Edge(t, "TRANSFER", 50, 1, 0));
        }

        // ---------------- TRAIN NETWORK (REALISTIC LINKS) ----------------
        addTrain(g, train, "Pune", "Mumbai", 150);
        addTrain(g, train, "Mumbai", "Surat", 280);
        addTrain(g, train, "Surat", "Ahmedabad", 270);
        addTrain(g, train, "Ahmedabad", "Jaipur", 660);
        addTrain(g, train, "Jaipur", "Delhi", 280);
        addTrain(g, train, "Delhi", "Lucknow", 500);
        addTrain(g, train, "Lucknow", "Patna", 550);
        addTrain(g, train, "Patna", "Kolkata", 600);
        addTrain(g, train, "Kolkata", "Guwahati", 1000);
        addTrain(g, train, "Bangalore", "Chennai", 350);
        addTrain(g, train, "Bangalore", "Hyderabad", 570);

        // ---------------- BUS NETWORK (LOCAL ONLY) ----------------
        for (City c1 : cities) {
            for (City c2 : cities) {

                if (c1 == c2) continue;

                double d = DistanceUtil.distance(
                        c1.lat, c1.lon,
                        c2.lat, c2.lon);

                if (d < 250) {
                    g.addEdge(bus.get(norm(c1.name)),
                            new Edge(bus.get(norm(c2.name)),
                                    "BUS",
                                    d * 1.5,
                                    d / 40,
                                    d));
                }
            }
        }

        // ---------------- FLIGHT NETWORK (METRO ONLY) ----------------
        for (City c1 : cities) {
            for (City c2 : cities) {

                if (c1 == c2) continue;

                if (c1.metro && c2.metro) {

                    double d = DistanceUtil.distance(
                            c1.lat, c1.lon,
                            c2.lat, c2.lon);

                    if (d > 300) {

                        Node a1 = air.get(norm(c1.name));
                        Node a2 = air.get(norm(c2.name));

                        if (a1 != null && a2 != null) {
                            g.addEdge(a1,
                                    new Edge(a2,
                                            "FLIGHT",
                                            d * 4,
                                            d / 600,
                                            d));
                        }
                    }
                }
            }
        }

        return g;
    }

    // ---------------- SAFE TRAIN ADD ----------------
    private static void addTrain(Graph g,
                                 Map<String, Node> train,
                                 String c1, String c2,
                                 double distance) {

        Node n1 = train.get(norm(c1));
        Node n2 = train.get(norm(c2));

        if (n1 == null || n2 == null) {
            System.out.println("⚠ Missing TRAIN node: " + c1 + " -> " + c2);
            return;
        }

        double cost = distance * 1.2;
        double time = distance / 70;

        g.addEdge(n1, new Edge(n2, "TRAIN", cost, time, distance));
        g.addEdge(n2, new Edge(n1, "TRAIN", cost, time, distance));
    }

    // ---------------- PUBLIC HELPERS ----------------
    public static List<Node> getNodesByCity(Graph g, String city) {

        List<Node> list = new ArrayList<>();

        for (Node n : g.getAllNodes()) {
            if (n.getName().toLowerCase()
                    .startsWith(city.trim().toLowerCase())) {
                list.add(n);
            }
        }

        return list;
    }

    public static Set<String> getAllCities(Graph g) {
        Set<String> set = new TreeSet<>();
        for (City c : cities) set.add(c.name);
        return set;
    }
}