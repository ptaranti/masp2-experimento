








package scenario.behaviours;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import masp.clock.FactorySimulationClockControl;
import masp.clock.SimulationClock;
import masp.clock.SimulationClockControl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class TP3Logger {

	protected SimulationClock simulationClock = SimulationClock.getInstance();
	protected SimulationClockControl simulationClockControl = FactorySimulationClockControl
			.getSimulationClockControl();


	private File fileRegister;
	private FileWriter writerRegister;
	private PrintWriter outRegister;

	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static Lock readLock = rwl.readLock();
	private Lock writeLock = rwl.writeLock();

	private static TP3Logger INSTANCE = new TP3Logger();

	public static TP3Logger getInstance() {
		readLock.lock();
		try {
			return INSTANCE;
		} finally {
			readLock.unlock();
		}
	}

	private TP3Logger() {
		if (fileRegister == null)
			fileRegister = new File("TP3Logger.csv");
		if (writerRegister == null)
			try {
				writerRegister = new FileWriter("TP3Logger.csv", true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("erro na abertura de TP3Logger.csv");
				e.printStackTrace();
			}

		if (outRegister == null) {
			outRegister = new PrintWriter(writerRegister, true);
		}
	}

	public void executeLogKinematics(Agent a, Behaviour b, String event,
			long realTime, double now, double error, double delay) {
		writeLock.lock();
		//counter++;
		//if (counter % logStep == 0) {
			try {
				outRegister.println("\"" + a.getLocalName() + "\";\""
						+ b.getBehaviourName() + "\";" + realTime + ";" + now
						+ ";" + error + ";" + delay + ";");
			} finally {
				writeLock.unlock();
			}
	//	}
	}

}
