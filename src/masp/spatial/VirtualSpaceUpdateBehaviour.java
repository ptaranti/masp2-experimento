package masp.spatial;

import masp.support.PropertiesLoaderImpl;
import jade.core.behaviours.SimpleBehaviour;
import masp.clock.SimulationClock;
import masp.clock.SimulationClockControl;
import masp.clock.StatisticCollector;
import masp.support.analiseLoggers.BehaviorsLogger;

/*****************************************************************
	 * This classe is a re-write of the TickerBehavior (from JADE platform),
	 * adapted to use the MASP simulation time
	 *****************************************************************/

	/**
	 * " Original Class: JADE TickerBehaviour This abstract class implements a
	 * <code>Behaviour</code> that periodically executes a user-defined piece of
	 * code. The user is expected to extend this class re-defining the method
	 * <code>onTick()</code> and including the piece of code that must be
	 * periodically executed into it. author Giovanni Caire - TILAB"
	 * 
	 * 
	 * 
	 * 
	 * 
	 * This class was modified for allow use of simulation time in MASP
	 * 
	 */

	class VirtualSpaceUpdateBehaviour extends SimpleBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private double wakeupSimulationTime;
		protected double simulatedTimePeriod = (long) PropertiesLoaderImpl.STEPTIME_SPATIAL_UPDATE;
		private boolean finished;
		private int tickCount = 0;
		private SimulationClock simulationClock = SimulationClock.getInstance();
		

		// private long counter = 0;
		private int logStep = 1;

		public VirtualSpaceUpdateBehaviour() {
			super();
		}

		public VirtualSpaceUpdateBehaviour(SpatialManagerAgent a) {
			super(a);
			setSimulatedTimePeriod();
			this.wakeupSimulationTime = simulationClock
					.getSimulationTime() + simulatedTimePeriod;
		}

		public void onStart() {

			if (PropertiesLoaderImpl.DEBUG)
				System.out.println("DEBUG " + myAgent.getLocalName() + " "
						+ this.getBehaviourName() + " -> behaviour started ");

		}

		public final void action() {
			// Someone else may have stopped us in the meanwhile
			if (!finished) {
				double now = simulationClock.getSimulationTime();
				double blockTime = wakeupSimulationTime - now;
				if (blockTime <= 0) {
					// Timeout is expired --> execute the user defined action
					// and
					// re-initialize wakeupTime

					if (PropertiesLoaderImpl.DEBUG)
						System.out.println("DEBUG " + myAgent.getLocalName()
								+ " " + this.getBehaviourName() + " Cycle "
								+ this.tickCount);

					double error = (((simulatedTimePeriod - blockTime) / simulatedTimePeriod) - 1);

					if (PropertiesLoaderImpl.DEBUG)
						System.out.println(this + " " + error + " blockTime "
								+ blockTime + " simulatedTimePeriod "
								+ simulatedTimePeriod);

					if (PropertiesLoaderImpl.DEBUG)
						System.out.println("DEBUG " + myAgent.getLocalName()
								+ " " + this.getBehaviourName() + " error "
								+ error);

/*					if (PropertiesLoaderImpl.FILE_LOG
							&& this.tickCount % logStep == 0)
						BehaviorsLogger.getInstance().executeLogBehaviour(
								myAgent.getLocalName(), this, "startLoad",
								System.currentTimeMillis(), now, error,
								blockTime);
*/
					tickCount++;
					this.onTick();

	
					wakeupSimulationTime = now + simulatedTimePeriod;
					
					//blockTime = simulatedTimePeriod;

				}
				// Maybe this behaviour has been removed within the onTick()
				// method
				//if (myAgent != null && !finished) {
				//	block((long) (blockTime / SimulationClockControl
				//			.getInstance().getSimulationClockRate()));
				//}
			}
		}

		public final boolean done() {
			return finished;
		}

		/**
		 * This method is invoked periodically with the period defined in the
		 * constructor. Subclasses are expected to define this method specifying
		 * the action that must be performed at every tick.
		 */
		protected void onTick() {

			VirtualSpaceMirror.getInstance().setVirtualSpaceAvatars();

		}

		public void reset(long simulatedTimePeriod) {
			this.reset();
			if (simulatedTimePeriod <= 0) {
				throw new IllegalArgumentException(
						"Period must be greater than 0");
			}
			this.simulatedTimePeriod = simulatedTimePeriod;
		}

		/**
		 * This method must be called to reset the behaviour and starts again
		 * 
		 * @param timeout
		 *            indicates in how many milliseconds from now the behaviour
		 *            must be waken up again.
		 */
		public void reset() {
			super.reset();
			finished = false;
			tickCount = 0;
		}

		/**
		 * Make this <code>TickerBehaviour</code> terminate. Calling stop() has
		 * the same effect as removing this TickerBehaviour, but is Thread safe
		 */
		public void stop() {
			finished = true;
		}

		/**
		 * Retrieve how many ticks were done (i.e. how many times this behaviour
		 * was executed) since the last reset.
		 * 
		 * @return The number of ticks since the last reset
		 */
		public final int getTickCount() {
			return tickCount;
		}

		// #MIDP_EXCLUDE_BEGIN

		// For persistence service
		private void setTickCount(int tc) {
			tickCount = tc;
		}

		// For persistence service
		private void setSimulatedTimePeriod(long p) {
			simulatedTimePeriod = p;
		}

		// For persistence service
		private void setSimulatedTimePeriod() {
			if (PropertiesLoaderImpl.DEBUG)
				System.out
						.println("DEBUG "
								+ myAgent.getLocalName()
								+ " "
								+ this.getBehaviourName()
								+ " periodo de execução do audate do espaco virtual, em tempo de simulacão: \n");
			this.simulatedTimePeriod = (int) PropertiesLoaderImpl.STEPTIME_SPATIAL_UPDATE;
			if (PropertiesLoaderImpl.DEBUG)
				System.out.println(this.simulatedTimePeriod);

		}

		// For persistence service
		private void setWakeupSimulationTime(long wt) {
			simulatedTimePeriod = wt;
		}

		// For persistence service
		private double getWakeupSimulationTime() {
			return simulatedTimePeriod;
		}

	}


