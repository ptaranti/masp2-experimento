package scenario.agents;

import com.vividsolutions.jts.geom.Coordinate;

import masp.simulacrum.MaspAgent;
import masp.support.PropertiesLoaderImpl;


public class IKL209ClassAgent extends MaspAgent {

	
	public IKL209ClassAgent() {
		super();
	}
	
	 public void  setupSimulation() {
		
		 if(PropertiesLoaderImpl.IS_SPATIAL_SIMULATION) {
		 spatial2Dstate.setCoordinate(new Coordinate(10, 0));
		spatial2Dstate.setCourse(000);
		spatial2Dstate.setSpeed(15.3);
		 }

	}
	
}
