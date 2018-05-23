package scenario.agents;

import com.vividsolutions.jts.geom.Coordinate;

import masp.simulacrum.MaspAgent;

public class NiteroiClassAgent extends MaspAgent {

	public NiteroiClassAgent() {
		super();


	}

	 public void setupSimulation() {
		// TODO Auto-generated method stub
		spatial2Dstate.setCoordinate(new Coordinate(20, 0));
		spatial2Dstate.setCourse(000);
		spatial2Dstate.setSpeed(15.3);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
