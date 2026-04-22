# OptiRoute – Multimodal Route Optimization System

## Project Overview

OptiRoute is a Java-based multimodal transport routing system designed to find the most optimal travel path between cities using graph algorithms. It models transportation networks including Train, Bus, and Flight systems, and computes the best route based on cost, time, and number of transfers.

The system represents cities and transport modes as a weighted graph and applies a modified version of Dijkstra’s algorithm for efficient path finding.

---

## Key Features

- Multimodal transport support (Train, Bus, Flight)
- Real-world city-based graph structure
- Route optimization based on:
  - Cost
  - Travel Time
  - Number of Transfers
- Distance-based edge generation using Haversine formula
- Transfer-aware routing logic
- Console-based interactive user interface

---

## Algorithm Used

- Modified Dijkstra’s Algorithm for shortest path computation
- Weighted scoring function:
  score = (cost × wc) + (time × wt) + (transfers × wtr)
- Haversine formula used for calculating geographic distances between cities

---

## System Architecture

User Input → Graph Construction → Dijkstra’s Algorithm → Path Evaluation → Optimal Route Output

---

## Project Structure

model/
- Node.java
- Edge.java
- State.java
- PathResult.java

graph/
- Graph.java

algorithm/
- Dijkstra.java

data/
- DataLoader.java
- DistanceUtil.java

src/
- Main.java

---

## How to Run

1. Compile all Java files in the project
2. Run Main.java
3. Enter the following inputs when prompted:
 - Source City
 - Destination City
 - Optimization Mode (cost, time, transfer)

---

## Example Output

==================================
   MULTI-MODAL ROUTE PLANNER
==================================

Available Cities:
----------------------------------
1. Ahmedabad
2. Bangalore
3. Chennai
4. Delhi
5. Guwahati
6. Hyderabad
7. Jaipur
8. Kolkata
9. Lucknow
10. Mumbai
11. Patna
12. Pune
13. Ranchi
14. Surat
----------------------------------

Enter Source City: Pune
Enter Destination City: Mumbai

Optimization Mode:
1. cost
2. time
3. transfer
Choose mode: 2

==================================
          BEST ROUTE
==================================

Pune Station (TRAIN)
   └──[TRAIN | 150 km | Cost: 180.0 | Time: 2.14h]──> Mumbai Station (TRAIN)

----------------------------------
Total Cost      : 180.0
Total Time      : 2.142857142857143
Total Transfers : 1
==================================
---

## Technology Stack

- Java
- Object-Oriented Programming
- Graph Theory
- Dijkstra’s Algorithm
- Haversine Distance Calculation

---

## Future Improvements

- Implementation of A* search algorithm for faster routing
- GUI-based visualization of routes
- Multiple optimal path suggestions
- Real-time transport data integration
- Interactive map-based interface
- API integration for more accurate avaiability of data
---

## Team Members

- Saee Shashank Kale
- Shravani Ashish Joshi

---

## Conclusion

OptiRoute demonstrates the application of graph theory and shortest path algorithms in real-world transportation systems. It efficiently handles multimodal routing and provides optimized travel decisions based on user preferences.
