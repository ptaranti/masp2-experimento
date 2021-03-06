package scenario.behaviours;

import jade.core.behaviours.Behaviour;

import java.util.Enumeration;
import java.util.Hashtable;

import com.vividsolutions.jts.geom.Coordinate;

import masp.simulacrum.MaspAgent;
import masp.simulacrum.MaspSimulationBehaviour;
import masp.support.PropertiesLoaderImpl;

public class Sonar2 extends MaspSimulationBehaviour {

	static long startTime;
	MaspAgent a;

	public Sonar2(MaspAgent a) {
		super(a);
		this.a = a;
		// TODO Auto-generated constructor stub
	}

	public void onStart() {
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println(this.getBehaviourName()
					+ " -> NOME DO BEHAVIOUR CARREGADO para o agente "
					+ myAgent.getLocalName());
		startTime = System.currentTimeMillis();
	}

	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println(this.getBehaviourName()
					+ " -> NOME DO BEHAVIOUR CARREGADO para o agente "
					+ myAgent.getLocalName() + " " + getTickCount() + "ciclo");

		
		
		
		new LoadService().load(startTime, 200);
	}

}