package masp.spatial;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import masp.support.PropertiesLoaderImpl;

import jade.core.behaviours.Behaviour;


public class SpatialManagerAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ContentManager manager = (ContentManager) getContentManager();
	// This agent "speaks" the SL language
	private Codec codec = new SLCodec();

	// This agent "knows" the PlatDominiumOntology()
	// private Ontology ontology = PlatDominiumOntology.getInstance();

	protected void setup() {

		manager.registerLanguage(codec);
		// manager.registerOntology(ontology);

	
		this.addBehaviour( (Behaviour) new VirtualSpaceUpdateBehaviour(this));

		if (PropertiesLoaderImpl.DEBUG)
			System.out.println(this.getName()
					+ " alive!!!!!!!!!!\nStarting the simulation time");
	}

}