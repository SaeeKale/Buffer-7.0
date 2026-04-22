package main;
import model.*;
import graph.*;
import algorithm.*;
import data.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
    	int chk = 1;
    	do
    	{
    		
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("==================================");
            System.out.println("   MULTI-MODAL ROUTE PLANNER");
            System.out.println("==================================\n");

            Graph graph = DataLoader.loadGraph();

            // ---------------- DISPLAY CITIES ----------------
            Set<String> cities = DataLoader.getAllCities(graph);

            System.out.println("Available Cities:");
            System.out.println("----------------------------------");

            int idx = 1;
            for (String c : cities) {
                System.out.println(idx + ". " + c);
                idx++;
            }

            System.out.println("----------------------------------\n");

            // ---------------- SOURCE INPUT ----------------
            String source;
            while (true) {
                System.out.print("Enter Source City: ");
                source = sc.nextLine().trim();

                if (cities.contains(source)) break;

                System.out.println("Invalid city. Please choose from list.\n");
            }

            // ---------------- DEST INPUT ----------------
            String dest;
            while (true) {
                System.out.print("Enter Destination City: ");
                dest = sc.nextLine().trim();

                if (cities.contains(dest)) break;

                System.out.println("Invalid city. Please choose from list.\n");
            }

            // ---------------- MODE ----------------
            System.out.println("\nOptimization Mode:");
            System.out.println("1. cost");
            System.out.println("2. time");
            System.out.println("3. transfer");

            System.out.print("Choose mode: ");
            String modeInput = sc.nextLine().trim().toLowerCase();

            String mode;
            switch (modeInput) {
                case "1":
                case "cost":
                    mode = "cost";
                    break;
                case "2":
                case "time":
                    mode = "time";
                    break;
                case "3":
                case "transfer":
                    mode = "transfer";
                    break;
                default:
                    System.out.println("Invalid mode selected.");
                    return;
            }

            // ---------------- NODE FETCH ----------------
            List<Node> sources = DataLoader.getNodesByCity(graph, source);
            List<Node> dests = DataLoader.getNodesByCity(graph, dest);

            double wc = 0, wt = 0, wr = 0;

            if (mode.equals("cost")) wc = 1;
            else if (mode.equals("time")) wt = 1;
            else wr = 1;

            // ---------------- SEARCH ----------------
            PathResult best = null;
            double bestScore = Double.MAX_VALUE;

            for (Node s : sources) {
                for (Node d : dests) {

                    PathResult result = Dijkstra.shortestPath(graph, s, d, wc, wt, wr);

                    if (result == null) continue;

                    double score =
                            wc * result.getTotalCost() +
                            wt * result.getTotalTime() +
                            wr * result.getTotalTransfers();

                    if (score < bestScore) {
                        bestScore = score;
                        best = result;
                    }
                }
            }

            // ---------------- OUTPUT ----------------
            if (best == null) {
                System.out.println("\n❌ No route found between " + source + " and " + dest);
                return;
            }

            System.out.println("\n==================================");
            System.out.println("          BEST ROUTE");
            System.out.println("==================================\n");

            List<Node> path = best.getPath();
            List<Edge> edges = best.getEdges();

            System.out.println(path.get(0));

            for (int i = 0; i < edges.size(); i++) {

                Edge e = edges.get(i);
                Node next = path.get(i + 1);

                System.out.println("   └──[" + e.getMode() +
                        " | " + (int)e.getDistance() + " km" +
                        " | Cost: " + e.getCost() +
                        " | Time: " + String.format("%.2f", e.getTime()) + "h]──> " + next);
            }

            System.out.println("\n----------------------------------");
            System.out.println("Total Cost      : " + best.getTotalCost());
            System.out.println("Total Time      : " + best.getTotalTime());
            System.out.println("Total Transfers : " + best.getTotalTransfers());
            System.out.println("==================================");

        } catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
        	System.out.println("Press 1 to continue and 0 to exit!");
        	chk = sc.nextInt();
        	if(chk==0)
        	{
        		sc.close();
        		System.exit(0);
        	}
        	else
        	{
        		continue;
        	}
        }
    	}while(chk==1);
    }
}