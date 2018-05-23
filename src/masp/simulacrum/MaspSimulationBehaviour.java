/*****************************************************************
 This classe is a re-write of the TickerBehavior (from JADE platform), adapted to use the MASP simulation time
 *****************************************************************/

package masp.simulacrum;

import jade.core.behaviours.SimpleBehaviour;
import masp.clock.SimulationClock;

import masp.clock.StatisticCollector;
import masp.support.JenaFacade;
import masp.support.PropertiesLoaderImpl;
import masp.support.analiseLoggers.BehaviorsLogger;
import masp.support.analiseLoggers.ClockControlRegisterLogger;

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

public abstract class MaspSimulationBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double wakeupSimulationTime;
	protected double simulatedTimePeriod;
	private boolean finished;
	protected int tickCount = 0;
	private SimulationClock simulationClock = SimulationClock.getInstance();
	double speed;
	MaspAgent a;
	boolean isPartSimulationModel = true;

	// private long counter = 0;
	protected int logStep = 1;

	public MaspSimulationBehaviour(MaspAgent a) {
		super(a);
		this.a = a;
		setSimulatedTimePeriod();

	}

	public void onStart() {

		this.speed = a.spatial2Dstate.getSpeed();
		this.wakeupSimulationTime = simulationClock.getSimulationTime()
				+ simulatedTimePeriod;

		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("DEBUG " + myAgent.getLocalName() + " "
					+ this.getBehaviourName() + " -> behaviour started ");

	}

	public final void action() {
		if (!simulationClock.getSimulationStarted()) {
			try {
				Thread.sleep(PropertiesLoaderImpl.DEFAULT_SLICE_TIME * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!finished) {
			if (simulationClock.isKillSimulation())
				a.doDelete();
			double now = simulationClock.getSimulationTime();
			//System.out.println(">>>>>>> " + now + " " + wakeupSimulationTime);
			double blockTime = wakeupSimulationTime - now;
			if (blockTime <= 0) {
				// Timeout is expired --> execute the user defined action and
				// re-initialize wakeupTime

				// if(PropertiesLoaderImpl.TESTING_DEVS)a.stopSimulationTimeTardinessInforming(a);

				if (PropertiesLoaderImpl.DEBUG)
					System.out.println("DEBUG " + myAgent.getLocalName() + " "
							+ this.getBehaviourName() + " Cycle "
							+ this.tickCount);

				double tardiness = (((simulatedTimePeriod - blockTime) / simulatedTimePeriod) - 1);

				if (PropertiesLoaderImpl.DEBUG)
					System.out.println(this + " " + tardiness + " blockTime "
							+ blockTime + " simulatedTimePeriod "
							+ simulatedTimePeriod);

				if (PropertiesLoaderImpl.DEBUG)
					System.out.println("DEBUG " + a.getLocalName() + " "
							+ this.getBehaviourName() + " error " + tardiness);

				if (PropertiesLoaderImpl.FILE_LOG && isPartSimulationModel)
					BehaviorsLogger.getInstance().executeLogBehaviour(
							a.getLocalName(), this, "startLoad",
							System.currentTimeMillis(), now, tardiness,
							blockTime);

				tickCount++;

				this.onTick();

				if (a.getSpatial2Dstate().getSpeed() != 0)
					setSimulatedTimePeriod(a.getSpatial2Dstate().getSpeed());

				wakeupSimulationTime = now + simulatedTimePeriod;
				// System.out.println("tempo para proxima: "
				// + (wakeupSimulationTime - simulationClock
				// .getSimulationTime()));
				informError(tardiness);

			}

		}
	}

	public final boolean done() {
		return finished;
	}

	/**
	 * This method is invoked periodically with the period defined in the
	 * constructor. Subclasses are expected to define this method specifying the
	 * action that must be performed at every tick.
	 */
	protected abstract void onTick();

	/**
	 * This method must be called to reset the behaviour and starts again
	 * 
	 * @param period
	 *            the new tick time
	 */
	public void reset(long simulatedTimePeriod) {
		this.reset();
		if (simulatedTimePeriod <= 0) {
			throw new IllegalArgumentException("Period must be greater than 0");
		}
		this.simulatedTimePeriod = simulatedTimePeriod;
	}

	/**
	 * This method must be called to reset the behaviour and starts again
	 * 
	 * @param timeout
	 *            indicates in how many milliseconds from now the behaviour must
	 *            be waken up again.
	 */
	public void reset() {
		super.reset();
		finished = false;
		tickCount = 0;
	}

	/**
	 * Make this <code>TickerBehaviour</code> terminate. Calling stop() has the
	 * same effect as removing this TickerBehaviour, but is Thread safe
	 */
	public void stop() {
		finished = true;
	}

	/**
	 * Retrieve how many ticks were done (i.e. how many times this behaviour was
	 * executed) since the last reset.
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
	private void setSimulatedTimePeriod() {
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("DEBUG " + myAgent.getLocalName() + " "
					+ this.getBehaviourName()
					+ " verificando a rate de execução");
		double stepRate = JenaFacade.getDataTypePropertyValueDouble(
				this.getBehaviourName(), "stepRate");
		if (stepRate == 0)
			this.simulatedTimePeriod = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
		else
			this.simulatedTimePeriod = 1000
					* PropertiesLoaderImpl.DEFAULT_SLICE_TIME
					* ((long) stepRate);
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("DEBUG " + myAgent.getLocalName() + " "
					+ this.getBehaviourName() + "  rate de execução "
					+ this.simulatedTimePeriod);

		// para simular devs
		if (PropertiesLoaderImpl.TESTING_DEVS) {
			this.simulatedTimePeriod = this.simulatedTimePeriod * Math.random()
					* 2;
		}
	}

	private void setSimulatedTimePeriod(double currentSpeed) {

		this.simulatedTimePeriod = 1000 * (long) (PropertiesLoaderImpl.MAXIMUM_SPATIAL_ERROR / (2 * currentSpeed));

	}

	// For persistence service
	private void setWakeupSimulationTime(long wt) {
		simulatedTimePeriod = wt;
	}

	// For persistence service
	private double getWakeupSimulationTime() {
		return simulatedTimePeriod;
	}

	public void informError(double error) {
		StatisticCollector.getInstance().insertData(error,
				isPartSimulationModel);

	}

	/*
	 * public void executeLogBehaviour(Agent a, Behaviour b, String event, long
	 * realTime, double now, double error, double delay) { // if (timeRegister
	 * == null) // timeRegister = logFormatterRegisterName // .format(new
	 * GregorianCalendar().getTime()); if (fileRegister == null) fileRegister =
	 * new File("BehaviourRegister.csv"); if (writerRegister == null) try {
	 * writerRegister = new FileWriter("BehaviourRegister.csv", true); } catch
	 * (IOException e) { // TODO Auto-generated catch block
	 * System.out.println("erro na abertura de BehaviourRegister.csv");
	 * e.printStackTrace(); }
	 * 
	 * if (outRegister == null) { outRegister = new PrintWriter(writerRegister,
	 * true); } outRegister.println("\"" + a.getLocalName() + "\";\"" +
	 * this.getBehaviourName() + "\";" + realTime + ";" + now + ";" + error +
	 * ";" + delay + ";");
	 * 
	 * }
	 */

}
