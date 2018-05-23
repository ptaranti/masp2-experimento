package scenario.behaviours;

import jade.core.behaviours.OneShotBehaviour;
import masp.simulacrum.MaspAgent;
import masp.simulacrum.MaspSimulationBehaviour;
import masp.support.PropertiesLoaderImpl;

public class TP3 extends OneShotBehaviour {

	
	MaspAgent a;
	String target;

	public TP3(MaspAgent a, String target) {
		super(a);
		this.a = a;
		this.target = target;
		// TODO Auto-generated constructor stub
	}

	public void onStart() {
		//if (PropertiesLoaderImpl.DEBUG)
		//	System.out.println(this.getBehaviourName()
		//			+ " -> NOME DO BEHAVIOUR CARREGADO para o agente "
		//			+ myAgent.getLocalName());
	
		//System.out.println(a.getLocalName() + " " + this + "still alive !!!!");
		
		
	}

	@Override
	public void action() {
		//System.out.println("MMMMMMMMM  >>>"+a.getLocalName() + " lançando torpedo em " + target);
		
	}



}
