package masp.nursery.test;

import static org.junit.Assert.*;

import masp.nursery.Starter;

import org.junit.Test;

public class StarterTest {

	
	@Test
	public void testStarter() {
		Starter s = new Starter();
		assert(s == null);
		}
	
	
	@Test
	public void testGetInstance() {
		Starter st = Starter.getInstance();
		assert (st != null);
		}



	@Test
	public void testCreateSimulatedEnvironment() {
		fail("Not yet implemented");
	}

	@Test
	public void testStopHeating() {
		fail("Not yet implemented");
	}

	@Test
	public void testStopHeating2() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRt() {
		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}
