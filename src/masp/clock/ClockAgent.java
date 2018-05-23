


package masp.clock;

import masp.support.PropertiesLoaderImpl;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class ClockAgent extends Agent {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	long TimeRateControl = (long)(PropertiesLoaderImpl.TIME_RATE_CONTROL*PropertiesLoaderImpl.ERROR_TRIGGER)/2;
	

	//private ContentManager manager = (ContentManager) getContentManager();
	// This agent "speaks" the SL language
	//private Codec codec = new SLCodec();

	// This agent "knows" the PlatDominiumOntology()
	// private Ontology ontology = PlatDominiumOntology.getInstance();

	protected void setup() {
		


		addBehaviour(new HandleClockBehaviour(this, TimeRateControl));
		
		if(PropertiesLoaderImpl.DEBUG) System.out.println(this.getName() +" alive!!!!!!!!!!\nStarting the simulation time");
		
		SimulationClock.getInstance().setSimulationStarted(true);
	}

}