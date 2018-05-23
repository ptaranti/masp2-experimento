package masp.clock.controlApproach;



import masp.clock.FactorySimulationClockControl;
import masp.clock.SimulationClock;
import masp.clock.SimulationClockControl;
import masp.clock.StatisticCollector;
import masp.clock.Statistics;
import masp.support.PropertiesLoaderImpl;
import masp.support.analiseLoggers.ClockControlRegisterLogger;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public abstract class HandleTimeRateBehaviour extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected SimulationClock simulationClock = SimulationClock.getInstance();
	protected SimulationClockControl simulationClockControl = FactorySimulationClockControl.getSimulationClockControl();
	
	protected Statistics oldStatistics = StatisticCollector.getInstance().recoverData();
	protected Statistics currentStatistics;
	
	protected long lastRate;
	protected long lastGoodRate;
	//protected static long stabilityCounter;




	@Override
	protected void onTick() {
		
		this.currentStatistics  = StatisticCollector.getInstance().recoverData();
		
		// Shout-down the system if a critical error is achieved
		if ((currentStatistics.getNumberOfErrorsGreatherThenTriggerUpperLimit()> 0)
				&& PropertiesLoaderImpl.STOP_WHEN_OVERLOAD) {
			System.out
					.println("\n\n************************************************\n"
							+ "The Simulation achieved an inacetable error level\n"
							+ "The simulation is being stooped \n"
							+ "A new time configuration could be necessary before run it again "
							+ "\n ************************************* ");
			System.exit(1);
		}
	
		if (PropertiesLoaderImpl.AVOID_OVERLOAD
				|| PropertiesLoaderImpl.AUTOMATIC_CONTROL) {
	


	
			if(simulationClock.getSimulationStarted()) errorControl();
			
			if (PropertiesLoaderImpl.DEBUG)
				System.out.println("DEBUG " + myAgent.getLocalName() + " "
						+ this.getBehaviourName() + "currentError: "
						+ currentStatistics.getMaxErrorInformed());
			if (PropertiesLoaderImpl.FILE_LOG)
				ClockControlRegisterLogger.getInstance().executeLogClockControl(currentStatistics.getMaxErrorInformed());
			
			oldStatistics = currentStatistics;
		}
	
	}



	public HandleTimeRateBehaviour(Agent a, long period) {
		super(a, period);
	}

	abstract void errorControl();
}