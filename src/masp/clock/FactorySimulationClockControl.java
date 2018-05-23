package masp.clock;
	
public class FactorySimulationClockControl {



		
		
		    public static SimulationClockControl getSimulationClockControl ()  {   
		       // if( PropertiesLoaderImpl.APPROACH == null ) return null;   
		    	return SimulationClockControl.getInstance();
		    	// else if( PropertiesLoaderImpl.APPROACH.equals("linear") ) return SimulationClockControl.getInstance();
			        //else if( PropertiesLoaderImpl.APPROACH.equals("sqr1") ) return  SimulationClockControlSqr01.getInstance();
		        //else if( PropertiesLoaderImpl.APPROACH.equals("sqr2") ) return  SimulationClockControlSqr02.getInstance(); 
		       // else return null;   
		      
		}   

	}

	

