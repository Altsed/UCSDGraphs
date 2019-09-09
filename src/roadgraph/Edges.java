package roadgraph;

import java.util.Calendar;
import java.util.Date;

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
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int temp = calendar.get(Calendar.HOUR_OF_DAY);
		double result;
		if (temp > 8 && temp < 11 || temp > 16 && temp < 20 ) {
			result = distance/(this.speed_lim*0.8);
		}
		else {
			result = distance/this.speed_lim;
		}
		
		return result;
	}
	
}
