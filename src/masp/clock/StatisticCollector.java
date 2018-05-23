package masp.clock;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;


import masp.support.PropertiesLoaderImpl;
import masp.support.analiseLoggers.StatisticsLogger;

/**
 * @author taranti Statistic is a class that only colect information to be used
 *         in time control activity
 */
public class StatisticCollector extends Statistics {

	private double triggerPoint;

	private double triggerUpperLimit;

	private static StatisticCollector statisticCollector;

	private File fileRegister;
	private FileWriter writerRegister;
	private PrintWriter outRegister;

	private StatisticCollector() {
		this.maxPeriodInformed = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
		this.maxErrorInformed = 0;
		this.errorSum = 0;
		this.numberOfInformations = 0;
		this.numberOfErrorsGreatherThenTriggerPoint = 0;
		this.numberOfErrorsGreatherThenTriggerUpperLimit = 0;
		this.triggerPoint = PropertiesLoaderImpl.ERROR_TRIGGER;
		this.triggerUpperLimit = PropertiesLoaderImpl.MAX_ERROR_TRIGGER;
	}

	public static StatisticCollector getInstance() {
		if (statisticCollector == null)
			statisticCollector = new StatisticCollector();
		return statisticCollector;
	}

	public void insertData(long period, double error) {

		this.maxPeriodInformed  = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
		if (this.maxErrorInformed < error)
			this.maxErrorInformed = error;
		this.errorSum = this.errorSum + error;
		this.numberOfInformations++;
		if (this.triggerPoint < error)
			this.numberOfErrorsGreatherThenTriggerPoint++;
		if (this.triggerUpperLimit < error)
			this.numberOfErrorsGreatherThenTriggerUpperLimit++;
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("data inserted  " + period + " " + error);
	}

	public void insertData(double error, boolean isPartOfSimulationModel ) {

		this.maxPeriodInformed  = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
		
		if (this.maxErrorInformed < error)
			this.maxErrorInformed = error;
		this.errorSum = this.errorSum + error;
		this.numberOfInformations++;
		if (this.triggerPoint < error)
			this.numberOfErrorsGreatherThenTriggerPoint++;
		if (this.triggerUpperLimit < error && isPartOfSimulationModel)
			this.numberOfErrorsGreatherThenTriggerUpperLimit++;
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("error inserted  " +  error);
	}
	
	public Statistics recoverData() {
		Statistics statistics = new Statistics(this.maxPeriodInformed,
				this.maxErrorInformed, this.errorSum,
				this.numberOfInformations,
				this.numberOfErrorsGreatherThenTriggerPoint,
				this.numberOfErrorsGreatherThenTriggerUpperLimit);

		if (PropertiesLoaderImpl.FILE_LOG)
			StatisticsLogger.getInstance().logStatistics(this.maxPeriodInformed,
					this.maxErrorInformed, this.errorSum,
					this.numberOfInformations,
					this.numberOfErrorsGreatherThenTriggerPoint,
					this.numberOfErrorsGreatherThenTriggerUpperLimit);
		this.reset();

		return statistics;
	}

	public void reset() {
		this.maxPeriodInformed  = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
		this.maxErrorInformed = 0;
		this.errorSum = 0;
		this.numberOfInformations = 0;
		this.numberOfErrorsGreatherThenTriggerPoint = 0;
		this.numberOfErrorsGreatherThenTriggerUpperLimit = 0;

	}



}
