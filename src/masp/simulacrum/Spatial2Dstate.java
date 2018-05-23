package masp.simulacrum;

import masp.clock.SimulationClock;

import com.vividsolutions.jts.geom.Coordinate;

public class Spatial2Dstate implements Cloneable {

	Coordinate coordinate;
	double lastUpdateCoordinateTime;
	double course;
	double speed;
	static SimulationClock simulationClock = SimulationClock.getInstance();
	
	
	public Spatial2Dstate(){
		this.coordinate = new Coordinate();
		this.course = 0;
		this.speed = 0.0;
		this.lastUpdateCoordinateTime = SimulationClock.getInstance().getSimulationTime();
	}
	

	private void setLastUpdateCoordinateTime(double lastUpdateCoordinateTime) {
		this.lastUpdateCoordinateTime = lastUpdateCoordinateTime;
	}


	public double getCourse() {
		return course;
	}

	public void setCourse(double course) {
		this.course = course;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Coordinate getCoordinate() {
		setCoordinate();
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public void setCoordinate() {
		//System.out.println(this.toString() +" - 1 - lastUpdateCoordinateTime = "  + lastUpdateCoordinateTime+ " na coordenada " + coordinate);
		//System.out.println("this.getSpeed()" + this.getSpeed() );
		
		double currentTime = simulationClock.getSimulationTime() / 1000;
		double deltaTime = currentTime - lastUpdateCoordinateTime;
		lastUpdateCoordinateTime = currentTime;

		
		double curseRad = this.course * (2 * Math.PI) / 360;
		double deltaSpace = this.speed * deltaTime;
		Coordinate transCoord = new Coordinate();

		if (curseRad <= (Math.PI / 2)) {
			transCoord.y = deltaSpace * Math.cos(curseRad);
			transCoord.x = deltaSpace * Math.sin(curseRad);
		} else if (curseRad <= (Math.PI)) {
			curseRad = Math.PI - curseRad;
			transCoord.y = (-1) * deltaSpace * Math.cos(curseRad);
			transCoord.x = deltaSpace * Math.sin(curseRad);
		} else if (curseRad <= (Math.PI * 3 / 2)) {
			curseRad = (Math.PI * 3 / 2) - curseRad;
			transCoord.y = (-1) * deltaSpace * Math.sin(curseRad);
			transCoord.x = (-1) * deltaSpace * Math.cos(curseRad);
		} else if (curseRad <= (Math.PI * 2)) {
			curseRad = (Math.PI * 2) - curseRad;
			transCoord.y = deltaSpace * Math.cos(curseRad);
			transCoord.x = (-1) * deltaSpace * Math.sin(curseRad);
		}

		this.coordinate = new Coordinate(this.coordinate.x + transCoord.x,
				this.coordinate.y + transCoord.y);
		
		//System.out.println(this.toString() +" - 2 -lastUpdateCoordinateTime = "  + lastUpdateCoordinateTime+ " na coordenada " + coordinate);
		

	}
	
	public String  toString(){
		return  ( " cordinate " + coordinate.toString() + "at "+ lastUpdateCoordinateTime + " of simulationTime seconds. Course " + course + " degres ans Speed " +speed +" m/sec") ;
		
	}
	
	public Spatial2Dstate clone(){
		Spatial2Dstate clone = new Spatial2Dstate();
		clone.setSpeed(this.speed);
		clone.setCourse(this.course);
		clone.setLastUpdateCoordinateTime(this.lastUpdateCoordinateTime);
		clone.setCoordinate(new Coordinate(this.coordinate));
		
		return clone;
	}

	public static void main (String args
			[]){
		
		//papra testes
		System.out.println("testes de deslocamento");
		System.out.println( " de 0,0 para 0,10");
		Coordinate cord1 = new Coordinate(0,0);
	}

	
}
