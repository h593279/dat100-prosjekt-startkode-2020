package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
		}
		return distance;

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		double diff = 0;
		for (int i = 1; i < gpspoints.length ; i++) {

			diff = gpspoints[i].getElevation() - gpspoints[i - 1].getElevation();
			if (diff > 0) {
			
			elevation += diff;
		}
		}
		return elevation;
	}

	// beregn total tiden for hele turen (i sekunder)

	public int totalTime() {

		int time = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			time += gpspoints[i].getTime() - gpspoints[i - 1].getTime();

		}

		return time;

	}

	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {

		double[] speed = new double[gpspoints.length - 1];

		for (int i = 1; i < gpspoints.length; i++) {
			speed[i - 1] = GPSUtils.speed(gpspoints[i - 1], gpspoints[i]);

		}

		return speed;

	}

	public double maxSpeed() {

		double maxspeed = 0;

		for (int i = 0; i < speeds().length; i++) {
			if (speeds()[i] > maxspeed) {
				maxspeed = speeds()[i];
			} 
		}
		return maxspeed;
	}

	// TODO - SLUTT

	public double averageSpeed() {

		double average = 0;
		double distance = totalDistance();
		double time = totalTime();

		average = (distance / time) * 3.6;

		return average;

	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling, general
	 * 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0 bicycling,
	 * 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9 mph, racing or
	 * leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph, racing/not drafting
	 * or >19 mph drafting, very fast, racing general 12.0 bicycling, >20 mph,
	 * racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;
		double speedmph = speed * MS;

		double time = secs / 3600.0;

		if (speedmph < 10) {
			met = 4.0;
		} else if (10 < speedmph && speedmph < 12) {
			met = 6.0;
		} else if (12 < speedmph && speedmph < 14) {
		met = 8.0;
		}else if (14 < speedmph && speedmph < 16) {
			met = 10.0;
		} else if (16 < speedmph && speedmph < 20) {
			met = 12.0;
		} else {
			met = 16.0; 
		}  

		kcal = (met * weight * time);

		return kcal;

	}

	public double totalKcal(double weight) {

		double totalkcal = 0;
		int tid; 
		double speed; 
		
		for(int i = 1; i < gpspoints.length; i++) {
			speed = speeds()[i-1];
			tid = gpspoints[i].getTime() - gpspoints[i-1].getTime();
			totalkcal += kcal(weight, tid, speed); 
			
		}
		
		return totalkcal; 

	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		System.out.println("==============================================");

		System.out.println("Total Time; " + GPSUtils.formatTime(totalTime()));
		System.out.println("Total distance: " + String.format("%10.2f",  totalDistance()/1000)+ "km");
		System.out.println("Total elevation: " + String.format("%10.2f", totalElevation()) + "m");
		System.out.println("Max speed: " + String.format("%10.2f", maxSpeed())+ "km/t");
		System.out.println("Average speed: " + String.format("10.2f", averageSpeed()) + "km/t");
		System.out.println("Energy: " + String.format("10.2f", totalKcal (WEIGHT)) + "kcal");


	}

}
