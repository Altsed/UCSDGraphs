package roadgraph;

import geography.GeographicPoint;

class Edges {
	private GeographicPoint start;
	private GeographicPoint end;
	private String roadName;
	private String roadType;
	private double distance;
	
	public Edges(GeographicPoint start, GeographicPoint end, String roadName, String roadType, double distance) {
		this.start = start;
		this.end = end;
		this.roadName = roadName;
		this.roadType = roadType;
		this.distance = distance;
		
	}
	
	// return end GeographicPoint  
	public  GeographicPoint getNeighbors() {
		return this.end;
	}
	public  double getDistance() {
		return this.distance;
	}
	
}
