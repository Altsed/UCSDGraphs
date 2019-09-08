package roadgraph;

import geography.GeographicPoint;

class Edges {
	private GeographicPoint start;
	private GeographicPoint end;
	private String roadName;
	private String roadType;
	private double distance;
	private double speed_lim;
	
	public Edges(GeographicPoint start, GeographicPoint end, String roadName, String roadType, double distance) {
		this.start = start;
		this.end = end;
		this.roadName = roadName;
		this.roadType = roadType;
		this.distance = distance;
		//configure speed limits for different types of a roads
		if (!roadType.isEmpty()) {
			if (roadType.equals("residential")) {
				this.speed_lim = 1;
			}
			if (roadType.equals("secondary")) {
				this.speed_lim = 80;
			}
			else this.speed_lim = 100;
		}
		else this.speed_lim = 10;
		
	}
	
	// return end GeographicPoint  
	public  GeographicPoint getNeighbors() {
		return this.end;
	}
	public  double getDistance() {
		return this.distance;
	}
	public  double getTime() {
		return distance/this.speed_lim;
	}
	
}
