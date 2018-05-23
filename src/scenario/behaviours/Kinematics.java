/**
 * 
 */
package scenario.behaviours;

import com.vividsolutions.jts.geom.Coordinate;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import masp.clock.SimulationClock;
import masp.clock.SimulationClockControl;
import masp.simulacrum.Masp2DMovementSimulationBehaviour;
import masp.simulacrum.MaspAgent;
import masp.support.PropertiesLoaderImpl;
import masp.support.analiseLoggers.BehaviorsLogger;

/**
 * @author taranti
 * 
 */
public class Kinematics extends Masp2DMovementSimulationBehaviour {

	MaspAgent a;
	private SimulationClock simulationClock = SimulationClock.getInstance();
	SimulationClockControl scc = SimulationClockControl.getInstance();
	Coordinate coordinateOld = new Coordinate();
	long oldSimulationTime;
	//super.simulatedTimePeriod;
	
	
	/**
	 * @param a
	 */
	public Kinematics(MaspAgent a) {
		super(a);
		this.a = a;
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see masp.simulacrum.Masp2DMovementSimulationBehaviour#eachUpdate()
	 */
	@Override
	protected void eachUpdate() {
		// TODO Auto-generated method stub
		
		
		
	Coordinate newCoord = a.getSpatial2Dstate().getCoordinate();
		double simulatedDistance = coordinateOld.distance(newCoord);
		this.coordinateOld = newCoord;
		
		double expectedDistance = super.simulatedTimePeriod * a.getSpatial2Dstate().getSpeed() / 1000;  //tempo original em millisegundos, por isso div por 100

		
		if (PropertiesLoaderImpl.FILE_LOG
				&& tickCount % logStep == 0)
			KinematicsLogger.getInstance().executeLogKinematics(myAgent,
					this,  System.currentTimeMillis(), simulationClock.getSimulationTime(), scc.getSimulationClockRate(), 
					simulatedDistance, expectedDistance, Math.abs(simulatedDistance - expectedDistance)); 
		
		//Agent a, Behaviour b, long realTime, double simulationTime, double LC, double distance, double expectedDistance, double Spatialerror
	
		//System.out.println(a.getLocalName() + " with following data :\n"+ a.virtualSpaceMirror.toString());
		//System.out.println(a.getLocalName() + " " +this+ " " + this.simulatedTimePeriod + " this.simulatedTimePeriod");
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
