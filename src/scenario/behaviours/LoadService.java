package scenario.behaviours;

import masp.support.PropertiesLoaderImpl;

public class LoadService {

	public LoadService() {

	}

	public void load(long startTime, long load) {

		long now = System.currentTimeMillis();
		long millis = load * (now - startTime)/((PropertiesLoaderImpl.EXPERIMENT_DURATION_MIN) * 60000);
		long a = 0;
		while(System.currentTimeMillis() < (now + millis)){ a++;}
		//System.out.println("startTime -> "+startTime+" ; millis -> "+millis+" ; now -> "+now);
		//System.out.println(System.currentTimeMillis() - now);
		

	}
}
