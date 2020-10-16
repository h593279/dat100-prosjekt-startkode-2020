package no.hvl.dat100ptc.oppgave1;

import no.hvl.dat100ptc.TODO;

public class GPSPoint {

	private int TIME;
	private double LATITUDE; 
	private double LONGITUDE; 
	private double ELEVATION; 
		
	public GPSPoint(int time, double latitude, double longitude, double elevation) {
		
		TIME = time; 
		LATITUDE = latitude; 
		LONGITUDE = longitude; 
		ELEVATION = elevation; 

	}

	public int getTime() { 
		return TIME; 
		
		
	}

	public void setTime(int time) {
		this.TIME = time; 
				

	}

	public double getLatitude() {
		return LATITUDE;
		 
		
	}

	public void setLatitude(double latitude) {
		this.LATITUDE = latitude; 
		
		
	}

	public double getLongitude() {
		return LONGITUDE; 
		
		
	}

	public void setLongitude(double longitude) {
		this.LONGITUDE = longitude; 
		
	}

	public double getElevation() {
		return ELEVATION; 
		
		
	} 

	public void setElevation(double elevation) {
		this.ELEVATION = elevation; 
		
	}
	
	public String toString() {
		
	return TIME + " (" + LATITUDE + "," + LONGITUDE + ") " + ELEVATION + "\n ";
	
		
	}
}
