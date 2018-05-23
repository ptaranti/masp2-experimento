package masp.simulacrum;

import masp.support.PropertiesLoaderImpl;

public abstract class Masp2DMovementSimulationBehaviour extends
MaspSimulationBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MaspAgent a;
	
	

	public Masp2DMovementSimulationBehaviour(MaspAgent a) {
		super(a);
		this.a = a;
	
	}

	@Override
	protected void onTick() {
		a.spatial2Dstate.setCoordinate();
		a.virtualSpace.putAvatar(a.getLocalName(), a.spatial2Dstate);
		eachUpdate();
	}

	protected abstract void eachUpdate();

	// For persistence service
	


		 //System.out.println(myAgent.getLocalName() + " ->  behavior "
		 //+ this.getBehaviourName()
		// + " assumindo thisBeraviourSliceTime de  "
		// + super.simulatedTimePeriod/1000 + " seconsds");

	}


