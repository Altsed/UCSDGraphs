package roadgraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

import geography.GeographicPoint;

class Vertices implements Comparator<Vertices> {

	
	private GeographicPoint coord;
	private List <Edges> edges;
	private double currDistance;
	private double estDistance;
	Vertices() {
		
	}
	
	Vertices(GeographicPoint coord) {
		this.coord = coord;
		edges = new ArrayList<Edges>();
	}
	
	
	public GeographicPoint getGeoPoint() {
		return this.coord;
	}
	
	public void addEdges(GeographicPoint start, GeographicPoint end, String roadName, String roadType, double distance) {
		edges.add(new Edges(start, end, roadName, roadType, distance));
	}
	
	public List<Edges> getEdges() {
		LinkedList<Edges> result = new LinkedList<Edges>();
		for (Edges s : edges) {
			result.add(s);
		}
		return result;
	}
	public void setCurrDistance (double currDistance) {
		this.currDistance = currDistance;
	}
	
	public double getCurrDistance() {
		return this.currDistance;
	}

	public void setEstDistance (double estDistance) {
		this.estDistance = estDistance;
	}
	
	public double getEstDistance() {
		return this.estDistance;
	}

	@Override
	public int compare(Vertices x, Vertices y) {
	        
		if ((x.getCurrDistance() + x.getEstDistance()) < (y.getCurrDistance() + y.getEstDistance())) {
	    	//System.out.println(x.getEstDistance() + " less then " + y.getEstDistance());
	        return -1;
	    }
	    if ((x.getCurrDistance() + x.getEstDistance()) > (y.getCurrDistance() + y.getEstDistance())) {
	     //  	System.out.println(x.getEstDistance() + " more then " + y.getEstDistance());
	        return 1;
	    }
	        return 0;
	}
	
	
	
}
