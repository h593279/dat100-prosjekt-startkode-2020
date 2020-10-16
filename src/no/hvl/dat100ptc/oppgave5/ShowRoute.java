package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 600;
	private static int MAPYSIZE = 600;
 
	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
		
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints)); 
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
	
		double ystep = MAPYSIZE / (Math.abs(maxlat -minlat));
		
		return ystep; 
		
		
	}

	public void showRouteMap(int ybase) {
		
		int xpos, ypos, xpos2, ypos2; 
		int radius = 2; 
		setColor(0, 255, 0);
		
		for( int i=0; i < gpspoints.length; i++ ) {
			xpos = (int) ((gpspoints[i].getLongitude() - GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints)))
					*xstep()); 
			ypos = (int) ((gpspoints[i].getLatitude()- GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints)))
					*ystep()); 
			
			fillCircle (xpos + MARGIN, ybase - ypos, radius); 
			
			if (i < gpspoints.length -1) {
				xpos2 = (int) ((gpspoints[i +1].getLongitude() - GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints)))
						*xstep()); 
				ypos2 = (int) ((gpspoints[i +1].getLatitude() - GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints)))
						*ystep()); 
				
				drawLine(xpos + MARGIN, ybase- ypos, xpos2 + MARGIN, ybase - ypos2 ); 
			}
		
			
		}
		
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		double WEIGHT = 80.0; 
		int y = 10; 
		
		
		
		String Time = ("Total Time: " + GPSUtils.formatTime(gpscomputer.totalTime()));
		
		String Distance = ("Total distance: " + String.format("%10.2f",  gpscomputer.totalDistance()/1000)+ "km");
		
		String Elevation = ("Total elevation: " + String.format("%10.2f", gpscomputer.totalElevation()) + "m");
		
		String Speed = ("Max speed: " + String.format("%10.2f", gpscomputer.maxSpeed())+ "km/t");
		
		String AvSpeed = ("Average speed: " + String.format("%10.2f", gpscomputer.averageSpeed()) + "km/t");
		
		String Energy = ("Energy: " + String.format("%10.2f", gpscomputer.totalKcal (WEIGHT)) + "kcal"); 
		
		
		drawString(Time, TEXTDISTANCE, y * 2); 
		drawString(Distance, TEXTDISTANCE, y * 4); 
		drawString(Elevation, TEXTDISTANCE, y * 6); 
		drawString(Speed, TEXTDISTANCE, y * 8); 
		drawString(AvSpeed, TEXTDISTANCE, y * 10); 
		drawString(Energy, TEXTDISTANCE, y * 12); 
	}
}
