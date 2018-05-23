package masp.clock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SimulationClock {

	/**
	 * Counter for simulation time, in milliseconds
	 */

	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static Lock readLock = rwl.readLock();
	private Lock writeLock = rwl.writeLock();

	private SimulationClockControl simulationClockControl;
	private long simulationTime;
	private boolean killSimulation;
	private boolean simulationStarted;

	/**
	 * Singleton
	 */
	private static SimulationClock INSTANCE = new SimulationClock();

	private SimulationClock() {

		this.simulationTime = 1;

	}

	/**
	 * @return Singleton to simulation clock
	 */
	public static SimulationClock getInstance() {
		readLock.lock();
		try {
			return INSTANCE;
		} finally {
			readLock.unlock();
		}
	}

	public long getSimulationTime() {

		readLock.lock();
		try {

			return this.simulationTime;

		} finally {
			readLock.unlock();
		}
	}

	public void setSimulationTime(long simulationTime) {

		writeLock.lock();
		try {

			this.simulationTime = simulationTime;

		} finally {
			writeLock.unlock();
		}
	}

	public void setKillSimulation(boolean killSimulation) {
		writeLock.lock();
		try {

			this.killSimulation = killSimulation;
		} finally {
			writeLock.unlock();
		}
	}

	public boolean isKillSimulation() {
		readLock.lock();
		try {

			return killSimulation;
		} finally {
			readLock.unlock();
		}
	}

	public void setSimulationStarted(boolean simulationStarted) {

		{
			writeLock.lock();
			try {
				StatisticCollector.getInstance().reset();
				this.simulationStarted = simulationStarted;
			} finally {
				writeLock.unlock();
			}
		}

	}	
	
	public boolean getSimulationStarted( ) {

		{
			readLock.lock();
			try {
				return this.simulationStarted;
			} finally {
				readLock.unlock();
			}
		}

	}
}