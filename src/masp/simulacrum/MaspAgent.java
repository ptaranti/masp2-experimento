package masp.simulacrum;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.List;

import masp.clock.SimulationClock;
import masp.spatial.VirtualSpace;
import masp.spatial.VirtualSpaceMirror;
import masp.support.JenaFacade;
import masp.support.PropertiesLoaderImpl;

public abstract class MaspAgent extends Agent {

	private static final long serialVersionUID = 1L;

	public Spatial2Dstate spatial2Dstate;
	public VirtualSpaceMirror virtualSpaceMirror;
	public VirtualSpace virtualSpace;

	private File fileRegister;
	private FileWriter writerRegister;
	private PrintWriter outRegister;

	private MaspSimulationTimeTardinessLevelInformationBehaviour maspSimulationTimeTardinessLevelInformationBehaviour;

	// private static Log log = new Log();

	public MaspAgent() {
		super();
		if(PropertiesLoaderImpl.IS_SPATIAL_SIMULATION) {
			this.spatial2Dstate = new Spatial2Dstate();
			this.virtualSpaceMirror = VirtualSpaceMirror
					.getInstance();
			this.virtualSpace = VirtualSpace.getInstance();
		}
	}

	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		super.setup();

		if (PropertiesLoaderImpl.FILE_LOG)
			executeLogAgent(this, "startLoad", System.currentTimeMillis(),
					SimulationClock.getInstance().getSimulationTime());

		 if (PropertiesLoaderImpl.DEBUG)
		 System.out.println("dentro do código do metodo  setup do " +
		 this.getAID());
		String role = JenaFacade.getRoleForAgent(this.getLocalName());
		 if (PropertiesLoaderImpl.DEBUG)
		 System.out.println(this.getLocalName() + " no papel " + role);
		List<String> behaviours = JenaFacade.getBehavioursForRole(role);

		//System.out.println("ATENCAO _ INCLUIDO COMPORTAMENTO DE CONTROLE");
		//if(PropertiesLoaderImpl.TESTING_DEVS) startSimulationTimeTardinessInforming(this);

		loadBehaviours(behaviours);
		setupSimulation();
		

	}

	// protected abstract void setupSimulationClass();

	/**
	 * This method must be implemented and overriden in subclasses, and will be
	 * executed when loading the agent
	 */
	abstract public void setupSimulation();

	private void loadBehaviours(List<String> behaviours) {
		for (String bhv : behaviours) {
			// System.out.println(bhv);

			String behaviourName = ("scenario.behaviours." + bhv);
			// System.out.println("\n carregando " + behaviourName);
			try {
				Class[] parameterTypes = { MaspAgent.class };
				Class bhvCls = Class.forName(behaviourName);
				// System.out.println(" class: " + bhvCls.toString());
				Constructor ctBeh = bhvCls.getConstructor(parameterTypes);
				// System.out.println(" constructor: " + ctBeh);
				Object objectBehaviour = ctBeh.newInstance(this);
				// System.out.println(" object: " + objectBehaviour);
				this.addBehaviour((Behaviour) objectBehaviour);
				// System.out.println("carga completada" + behaviourName +
				// "\n");

			} catch (Throwable e) {
				//executeLogAgent(this, "failLoad", System.currentTimeMillis(),
				//		SimulationClock.getInstance().getSimulationTime());

				System.out.println("erro de carga  ao carregar "
						+ behaviourName + ": " + e + e.getStackTrace()
						+ e.getCause());
			}

		}

		//if (PropertiesLoaderImpl.FILE_LOG)
		//	executeLogAgent(this, "finalizeLoad", System.currentTimeMillis(),
		//			SimulationClock.getInstance().getSimulationTime());
	}

	public Spatial2Dstate getSpatial2Dstate() {
		if (this.spatial2Dstate == null)
			this.spatial2Dstate = new Spatial2Dstate();
		return this.spatial2Dstate;
	}

	public void executeLogAgent(Agent a, String event, long realTime,
			long simulationTime) {
		// if (timeRegister == null)
		// timeRegister = logFormatterRegisterName
		// .format(new GregorianCalendar().getTime());
		if (fileRegister == null)
			fileRegister = new File("AgentRegister" + ".csv");
		if (writerRegister == null)
			try {
				writerRegister = new FileWriter("AgentRegister" + ".csv", true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out
				.println("erro na abertura de AgentRegister" + ".csv");
				e.printStackTrace();
			}

		if (outRegister == null) {
			outRegister = new PrintWriter(writerRegister, true);

		}
		outRegister.println("\"" + a.getLocalName() + "\";\"" + event + "\";"
				+ realTime + ";" + simulationTime + ";");

	}

	boolean startSimulationTimeTardinessInforming(MaspAgent a) {
		if (a != this)
			return false;
		if (this.maspSimulationTimeTardinessLevelInformationBehaviour != null) stopSimulationTimeTardinessInforming(a);
		
		if (a == this) {
			this.maspSimulationTimeTardinessLevelInformationBehaviour = new MaspSimulationTimeTardinessLevelInformationBehaviour(
					a);
			this.addBehaviour(maspSimulationTimeTardinessLevelInformationBehaviour);
		}
		return true;

	}

	boolean stopSimulationTimeTardinessInforming(MaspAgent a) {
		if ((a != this)
				&& (this.maspSimulationTimeTardinessLevelInformationBehaviour == null))
			return false;
		if (a == this) {
			this.removeBehaviour(maspSimulationTimeTardinessLevelInformationBehaviour);
		}
		return true;

	}

}
