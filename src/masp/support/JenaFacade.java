package masp.support;

import java.util.List;
import java.util.Vector;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.impl.OntModelImpl;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.impl.NodeIteratorImpl;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.vocabulary.RDF;

public class JenaFacade {

	// verificando comportamentos

	public static List<String> getAgents() {
		List<String> agentesName = new Vector<String>();

		OntModelImpl ontRawModel = OntoModels.getOntoModel();
		ontRawModel.enterCriticalSection(Lock.READ);

		try {

			List<Individual> agentes = ontRawModel.listIndividuals(
					ontRawModel.getOntClass(OntoAddress.ontologyNS + "Agent"))
					.toList();

			for (Individual agt : agentes)
				agentesName.add(agt.getLocalName());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			ontRawModel.leaveCriticalSection();
		}

		return agentesName;

	}

	public static String getRoleForAgent(String agentLocalName) {
		String roleForAgent = null;
		OntModelImpl ontRawModel = OntoModels.getOntoModel();
		ontRawModel.enterCriticalSection(Lock.READ);

		try {
			Individual ontAgtIndividual = ontRawModel
					.getIndividual(OntoAddress.ontologyNS + agentLocalName);
			ObjectProperty playProperty = ontRawModel
					.getObjectProperty(OntoAddress.ontologyNS + "play");
			Resource roleRsc = (Resource) (ontRawModel.getProperty(
					ontAgtIndividual, playProperty)).getObject();
			roleForAgent = roleRsc.getLocalName();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			ontRawModel.leaveCriticalSection();
		}
		return roleForAgent;
	}

	public static String getOntologyClassForIndividual(String indivudual) {
		String ontologyClassForIndividual = null;
		OntModelImpl ontRawModel = OntoModels.getOntoModel();
		ontRawModel.enterCriticalSection(Lock.READ);
		try {
			Individual ontIndividual = ontRawModel
					.getIndividual(OntoAddress.ontologyNS + indivudual);
			ontologyClassForIndividual = (ontRawModel.getProperty(
					ontIndividual, RDF.type)).getObject().asNode()
					.getLocalName();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			ontRawModel.leaveCriticalSection();
		}
		return ontologyClassForIndividual;

	}

	public static List<String> getBehavioursForRole(String roleLocalName) {
		List<String> behavioursName = new Vector<String>();
		OntModelImpl ontInfered = OntoModels.getInferModel();
		ontInfered.enterCriticalSection(Lock.READ);

		try {
			Resource roleRsc = ontInfered.getResource(OntoAddress.ontologyNS
					+ roleLocalName);
			// if (PropertiesLoaderImpl.DEBUG)
			// System.out.println(roleLocalName + " associado ao " + roleRsc);
			ObjectProperty hasBehaviourProperty = OntoModels
					.getInferModel()
					.getObjectProperty(OntoAddress.ontologyNS + "has_Behaviour");

			List<RDFNode> behaviours = ((NodeIteratorImpl) OntoModels
					.getInferModel().listObjectsOfProperty(roleRsc,
							hasBehaviourProperty)).toList();

			for (RDFNode bhv : behaviours)
				behavioursName.add(bhv.asNode().getLocalName());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			ontInfered.leaveCriticalSection();
		}
		return behavioursName;
	}

	public static String getDataTypePropertyValueString(String individualName,
			String dataTypePropertyName) {

		String dataTypePropertyValue = null;
		OntModelImpl ontInfered = OntoModels.getInferModel();

		ontInfered.enterCriticalSection(Lock.READ);

		try {

			Individual ontIndividual = ontInfered
					.getIndividual(OntoAddress.ontologyNS + individualName);

			DatatypeProperty dataTypeProperty = ontInfered
					.getDatatypeProperty(OntoAddress.ontologyNS
							+ dataTypePropertyName);
			Statement stmTmp = ontInfered.getProperty(ontIndividual,
					dataTypeProperty);
			if (stmTmp == null)
				dataTypePropertyValue = null;
			else
				dataTypePropertyValue = stmTmp.getString();

			if (PropertiesLoaderImpl.DEBUG)
				System.out.println("getDataTypeValue->  indivuduo: "
						+ individualName + " dataTypePropertyName: "
						+ dataTypePropertyName + " value: "
						+ dataTypePropertyValue);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			ontInfered.leaveCriticalSection();
		}
		return dataTypePropertyValue;
	}

	public static double getDataTypePropertyValueDouble(String individualName,
			String dataTypePropertyName) {
		double dataTypePropertyValue = 0;
		OntModelImpl ontInfered = OntoModels.getInferModel();
		ontInfered.enterCriticalSection(Lock.READ);

		try {
			Individual ontIndividual = ontInfered
					.getIndividual(OntoAddress.ontologyNS + individualName);

			DatatypeProperty dataTypeProperty = ontInfered
					.getDatatypeProperty(OntoAddress.ontologyNS
							+ dataTypePropertyName);
			Statement stmTmp = ontInfered.getProperty(ontIndividual,
					dataTypeProperty);
			if (stmTmp == null)
				dataTypePropertyValue = 0;
			else
				dataTypePropertyValue = stmTmp.getDouble();

			// if (PropertiesLoaderImpl.DEBUG)
			// System.out.println("getDataTypeValue->  indivuduo: " +
			// individualName
			// + " dataTypePropertyName: " + dataTypePropertyName + " value: " +
			// dataTypePropertyValue );
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			ontInfered.leaveCriticalSection();
		}
		return dataTypePropertyValue;
	}

	public static int getDataTypePropertyValueInt(String individualName,
			String dataTypePropertyName) {
		int dataTypePropertyValue = 0;
		OntModelImpl ontInfered = OntoModels.getInferModel();
		ontInfered.enterCriticalSection(Lock.READ);

		try {
			Individual ontIndividual = ontInfered
					.getIndividual(OntoAddress.ontologyNS + individualName);

			DatatypeProperty dataTypeProperty = ontInfered
					.getDatatypeProperty(OntoAddress.ontologyNS
							+ dataTypePropertyName);
			Statement stmTmp = ontInfered.getProperty(ontIndividual,
					dataTypeProperty);
			if (stmTmp == null)
				dataTypePropertyValue = 0;
			else
				dataTypePropertyValue = stmTmp.getInt();

			// if (PropertiesLoaderImpl.DEBUG)
			// System.out.println("getDataTypeValue->  indivuduo: " +
			// individualName
			// + " dataTypePropertyName: " + dataTypePropertyName + " value: " +
			// dataTypePropertyValue );
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			ontInfered.leaveCriticalSection();
		}
		return dataTypePropertyValue;
	}

}
