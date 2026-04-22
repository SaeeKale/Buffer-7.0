package model;

import java.util.Objects;

public class Node {

    private String id;
    private String name;
    private String type;

    private double lat;   // ✅ NEW
    private double lon;   // ✅ NEW

    public Node(String id, String name, String type,
                double lat, double lon) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }

    public double getLat() { return lat; }
    public double getLon() { return lon; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node n = (Node) o;
        return id.equals(n.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}