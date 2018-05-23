package masp.spatial;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.vividsolutions.jts.geom.Coordinate;

import masp.simulacrum.Spatial2Dstate;

public class VirtualSpaceMirror {

	static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	static Lock readLock = rwl.readLock();
	Lock writeLock = rwl.writeLock();

	Hashtable<String, Spatial2Dstate> spatial2DstateAll;

	private static VirtualSpaceMirror singleton = new VirtualSpaceMirror();

	public static VirtualSpaceMirror getInstance() {
		readLock.lock();
		try {
			return singleton;
		} finally {
			readLock.unlock();
		}
	}

	private VirtualSpaceMirror() {
		super();
		writeLock.lock();
		try {
			this.spatial2DstateAll = VirtualSpace.getInstance()
					.cloneSpatial2DstateAll();
		} finally {
			writeLock.unlock();
		}
	}

	protected Hashtable<String, Spatial2Dstate> getVirtualSpaceAvatars() {
		readLock.lock();
		try {
			return spatial2DstateAll;
		} finally {
			readLock.unlock();
		}
	}

	public void setVirtualSpaceAvatars() {
		writeLock.lock();
		try {
			this.spatial2DstateAll = VirtualSpace.getInstance()
					.cloneSpatial2DstateAll();
		} finally {
			writeLock.unlock();
		}
	}

	public String toString() {
		String string = new String();
		readLock.lock();
		try {
			Enumeration<String> nomesAvatares = this.spatial2DstateAll.keys();
			while (nomesAvatares.hasMoreElements()) {
				String key = nomesAvatares.nextElement();
				string = string
						+ ("agent " + key + " spatial state = "
								+ spatial2DstateAll.get(key).toString() + "\n");
			}
		} finally {
			readLock.unlock();
		}
		return string;

	}

	public Hashtable<String, Coordinate> targetsAtDistance(Double distanceDetection, Coordinate position) {
		Hashtable<String, Coordinate> targets = new Hashtable<String, Coordinate>();
		readLock.lock();
		try {
			Enumeration<String> nomesAvatares = this.spatial2DstateAll.keys();
			while (nomesAvatares.hasMoreElements()) {
				
				String targetLocalName = nomesAvatares.nextElement();
				
				Coordinate targetCoordinate  = spatial2DstateAll.get(targetLocalName).getCoordinate();
				
				
				//Inserir calculo de posicao estimada
				
				
				
				//System.out.println("XXXXXXXXX "+this +" "+targetLocalName + " NA POSICAO "+ targetCoordinate + "distancia " + targetCoordinate.distance(position));
				//System.out.println("distanceDetection >>" +distanceDetection + (targetCoordinate.distance(position) > distanceDetection) );
				
				if (targetCoordinate.distance(position) < distanceDetection) targets.put(targetLocalName, targetCoordinate);
				
			}
		} finally {
			readLock.unlock();
		}
		return targets;

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
