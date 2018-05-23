package scenario.agents;

import com.vividsolutions.jts.geom.Coordinate;

import masp.simulacrum.MaspAgent;

public class BravoSubmarineClassAgent extends MaspAgent {

	public BravoSubmarineClassAgent() {
		super();
	}
	
	public void setupSimulation() {
		
		this.spatial2Dstate.setCoordinate(new Coordinate(0, 0));
		this.spatial2Dstate.setCourse(000);
		this.spatial2Dstate.setSpeed(100);
		
	}

}
