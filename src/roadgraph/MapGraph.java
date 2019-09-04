/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.Queue;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	private int numVertices;
	private int numEdges;
	
	//HashMap of Vertices from loading map 
	private HashMap<GeographicPoint, Vertices> nodes;
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	//constructor for MapGraph new object
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		numVertices = 0;
		numEdges = 0;
		nodes = new HashMap<GeographicPoint, Vertices>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		// iterate through HashMap nodes and pass key (GeographicPoint) of each vertices to new HashSet. 
		Set <GeographicPoint> setOfNodes = new HashSet <GeographicPoint>();  
		for (GeographicPoint vertices : nodes.keySet()) {
			setOfNodes.add(vertices);
		}
			
		return setOfNodes;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return numEdges;
		
	}

	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		//Check if HashMap of vertices doesn't contains vertices with passed location and add it.
		if (nodes.get(location) != null || location == null) {
			return false; 
		}
		
		nodes.put(location, new Vertices (location));
		numVertices++;
			
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3
		// find Vertices in the HashMap of vertices by key (GeographicPoint)  and add edge to the List of Edges for this Vertices
		if (nodes.get(from) != null && nodes.get(to) != null && roadName != null && roadType != null && length >= 0) {
			nodes.get(from).addEdges(from, to, roadName, roadType, length);
			numEdges++;
				
		}
		else {
			System.out.println("Check arguments please");
			throw new IllegalArgumentException();
			
		}
			
			
		/* prev version
		 * try {
			nodes.get(from).edges.add(new Edges (from, to, roadName, roadType, length));
			numEdges++;
		} catch (IllegalArgumentException e) {
			System.out.println("Check arguments please");
		}
		 */
 	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		/* initialize variables: list - queue to store vertices which needed to be visited
		* visited - HashSet to store already visited vertices
		* parentsMap - HashMap <GeograficPoint, GeograficPoint> there key is GeograficPoint of next vertices to visit 
		* and value is GeograficPoint of current vertices
		* found - boolean variable to store state if goal vertices was found or not
		*/
		
		Queue<Vertices> list = new LinkedList<>(); 
		HashSet<Vertices> visited = new HashSet<>();
		HashMap<GeographicPoint, GeographicPoint> parentsMap = new HashMap<>();
		boolean found = false;
		
		//initialize Vertices variable curr. Find vertices in HashMap nodes with GeographicPoint start and assign it to curr.
		Vertices curr = nodes.get(start);
		list.add(curr);
		visited.add(curr);
		
		while (!list.isEmpty()) {
			curr = list.remove();
			nodeSearched.accept(curr.getGeoPoint());
			if (curr.getGeoPoint().equals(goal)) {
				found = true;
				break;
			}
					
			for (Edges s : curr.getEdges()) {
				Vertices next = nodes.get(s.getNeighbors());
				if (!visited.contains(next)) {
					visited.add(next); 
					list.add(next);
					parentsMap.put(next.getGeoPoint(), curr.getGeoPoint());
				}
			}
			

		}
		
		
		if (!found) {
			System.out.println("Path doesn't exist");
			return null;
		}
		
		//reconstruct path from parentsMap in back order, from goal point to start point 
		LinkedList<GeographicPoint> path = new LinkedList<>();
		GeographicPoint currGeo = goal;
		while (!currGeo.equals(start)) {
			path.addFirst(currGeo);
			currGeo = parentsMap.get(currGeo);
		}
		path.addFirst(start);
		
		return path;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	/*public class distanceComparator implements Comparator<Vertices> {
	    @Override
	    public int compare(Vertices x, Vertices y) {
	        
	        if (x.getCurrDistance() < y.getCurrDistance()) {
	        	System.out.println(x.getCurrDistance() + " less then " + y.getCurrDistance());
	            return -1;
	        }
	        else if (x.getCurrDistance() > y.getCurrDistance()) {
	        	System.out.println(x.getCurrDistance() + " more then " + y.getCurrDistance());
	            return 1;
	        }
	        return 0;
	    }
	}
	*/
	
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
			  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization. See writeup.
		// nodeSearched.accept(next.getLocation());
		int count = 0;
		PriorityQueue<Vertices> list = new PriorityQueue<Vertices> (11, new Vertices()); 
		HashSet<Vertices> visited = new HashSet<>();
		HashMap<GeographicPoint, GeographicPoint> parentsMap = new HashMap<>();
		boolean found = false;
		double pathDistance = 0;
			
		//initialize Vertices variable curr. Find vertices in HashMap nodes with GeographicPoint start and assign it to curr.
		Vertices curr = nodes.get(start);
		list.add(curr);
				
		while (!list.isEmpty()) {
			
			
			curr = list.remove();
			count++;
			pathDistance = curr.getCurrDistance();
			System.out.println("pathDistance :" + pathDistance);
			nodeSearched.accept(curr.getGeoPoint());
			if (!visited.contains(curr)) {
				visited.add(curr);
				
				if (curr.getGeoPoint().equals(goal)) {
					found = true;
					break;
				}
				
				for (Edges s : curr.getEdges()) {
					Vertices next = nodes.get(s.getNeighbors());
										
					if (!visited.contains(next)) {
						if (list.contains(next) && next.getCurrDistance() < (pathDistance + s.getDistance())) {
							
						}
						else {
							next.setCurrDistance(pathDistance + s.getDistance());
							list.add(next);
							parentsMap.put(next.getGeoPoint(), curr.getGeoPoint());
						}
						
						
						
				//		System.out.println("PQ added: " + next.getGeoPoint().getX() + "|" +  next.getGeoPoint().getY() + "  next " + next.getCurrDistance() + "   Top PQ:   " + list.peek().getCurrDistance());
					
					}
				}
				//System.out.println(list.size() + " PriorityQueue: " + list.element().getCurrDistance()); 
				
				Vertices[] arr = new Vertices[list.size()]; 
		        Vertices[] arr1 = list.toArray(arr); 
				for (int i = 0; i < arr1.length; i++) {
			//		System.out.println(arr1[i].getGeoPoint().getX() + "|" + arr1[i].getGeoPoint().getY() + "  distance:  " + arr1[i].getCurrDistance() + "+" + i + " PriorityQueue: " + list.peek().getCurrDistance());
				}
					
				
			}
			
			

		}
		

		if (!found) {
			System.out.println("Path doesn't exist");
			return null;
		}

		for (Map.Entry<GeographicPoint, GeographicPoint> entry : parentsMap.entrySet()) {
	//		System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		System.out.println("Visited # nodes dijstra*: " + visited.size());
//reconstruct path from parentsMap in back order, from goal point to start point 
		LinkedList<GeographicPoint> path = new LinkedList<>();
		GeographicPoint currGeo = goal;
		while (!currGeo.equals(start)) {
			path.addFirst(currGeo);
			currGeo = parentsMap.get(currGeo);
		}
		path.addFirst(start);
		System.out.println("Deleted count:" + count);
		return path;

	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		PriorityQueue<Vertices> list = new PriorityQueue<Vertices> (11, new Vertices()); 
		HashSet<Vertices> visited = new HashSet<>();
		HashMap<GeographicPoint, GeographicPoint> parentsMap = new HashMap<>();
		boolean found = false;
		double pathDistance = 0;
			
		//initialize Vertices variable curr. Find vertices in HashMap nodes with GeographicPoint start and assign it to curr.
		Vertices curr = nodes.get(start);
		list.add(curr);
				
		while (!list.isEmpty()) {
			
			
			curr = list.remove();
			pathDistance = curr.getCurrDistance();
		//	System.out.println("pathDistance :" + pathDistance);
			nodeSearched.accept(curr.getGeoPoint());
			if (!visited.contains(curr)) {
				visited.add(curr);
				
				if (curr.getGeoPoint().equals(goal)) {
					found = true;
					break;
				}
				
				for (Edges s : curr.getEdges()) {
					Vertices next = nodes.get(s.getNeighbors());
										
					if (!visited.contains(next)) {
						if (list.contains(next) && next.getEstDistance() < (pathDistance + s.getDistance())) {
							
						}
						else {
							next.setCurrDistance(pathDistance + s.getDistance());
							next.setEstDistance(next.getGeoPoint().distance(goal));
							list.add(next);
							parentsMap.put(next.getGeoPoint(), curr.getGeoPoint());
						}
						
						
						
				//		System.out.println("PQ added: " + next.getGeoPoint().getX() + "|" +  next.getGeoPoint().getY() + "  next " + next.getEstDistance() +
				//				"   Top PQ:   " + list.peek().getEstDistance());
					
					}
				}
				//System.out.println(list.size() + " PriorityQueue: " + list.element().getCurrDistance()); 
				
				Vertices[] arr = new Vertices[list.size()]; 
		        Vertices[] arr1 = list.toArray(arr); 
				for (int i = 0; i < arr1.length; i++) {
				//	System.out.println(arr1[i].getGeoPoint().getX() + "|" + arr1[i].getGeoPoint().getY() + "  distance:  " 
				//+ arr1[i].getEstDistance() + "+" + i + " PriorityQueue: " + list.peek().getEstDistance());
				}
					
				
			}
			
			

		}
		

		if (!found) {
			System.out.println("Path doesn't exist");
			return null;
		}

		for (Map.Entry<GeographicPoint, GeographicPoint> entry : parentsMap.entrySet()) {
		//	System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		System.out.println("Visited # nodes A*: " + visited.size());
//reconstruct path from parentsMap in back order, from goal point to start point 
		LinkedList<GeographicPoint> path = new LinkedList<>();
		GeographicPoint currGeo = goal;
		while (!currGeo.equals(start)) {
			path.addFirst(currGeo);
			currGeo = parentsMap.get(currGeo);
		}
		path.addFirst(start);

		return path;


	}

	
	
	public static void main(String[] args)
	{
	/*	System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE." + firstMap.getNumVertices());
		System.out.println(firstMap.numEdges);
		GeographicPoint start = new GeographicPoint(1.0, 1.0);
		GeographicPoint goal = new GeographicPoint(8.0, -1.0);
		// test dijkstra
		
		System.out.println (firstMap.dijkstra(start, goal).size());
		for (GeographicPoint list : firstMap.dijkstra(start, goal)) {
			System.out.println(list.getX() + "|" + list.getY());
		}
		
		
		
		// test aStarSearch
		System.out.println (firstMap.aStarSearch(start, goal).size());
		for (GeographicPoint list : firstMap.aStarSearch(start, goal)) {
			System.out.println(list.getX() + "|" + list.getY());
		}
		
		
		/* test bfs
		System.out.println (firstMap.bfs(start, goal).size());
		for (GeographicPoint list : firstMap.bfs(start, goal)) {
			System.out.println(list.getX() + "|" + list.getY());
		}
		*/
		
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		/*
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		/*
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		/*
		 MapGraph simpleTestMap = new MapGraph();
			GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
			
			GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
			GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
			
			System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
			List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
			List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
			
			
			MapGraph testMap = new MapGraph();
			GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
			
			// A very simple test using real data
			testStart = new GeographicPoint(32.869423, -117.220917);
			testEnd = new GeographicPoint(32.869255, -117.216927);
			System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
			testroute = testMap.dijkstra(testStart,testEnd);
			testroute2 = testMap.aStarSearch(testStart,testEnd);
			
			
			// A slightly more complex test using real data
			testStart = new GeographicPoint(32.8674388, -117.2190213);
			testEnd = new GeographicPoint(32.8697828, -117.2244506);
			System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
			testroute = testMap.dijkstra(testStart,testEnd);
			testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);

		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
	}
	
}
