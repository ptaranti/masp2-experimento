package masp.simulacrum;

import masp.clock.StatisticCollector;
import masp.support.PropertiesLoaderImpl;

public class MaspSimulationTimeTardinessLevelInformationBehaviour extends
		MaspSimulationBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaspSimulationTimeTardinessLevelInformationBehaviour(MaspAgent a) {
		super(a);
		super.isPartSimulationModel =false;
		// TODO Auto-generated constructor stub
		setSimulatedTimePeriod();
	}

	@Override
	protected void onTick() {
		// TODO Auto-generated method stub

	}
	
	// For persistence service
		private void setSimulatedTimePeriod() {
		this.simulatedTimePeriod = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
			if (PropertiesLoaderImpl.DEBUG)
				System.out.println("DEBUG " + myAgent.getLocalName() + " "
						+ this.getBehaviourName() + "  rate de execução "
						+ this.simulatedTimePeriod);
		}
		


}
