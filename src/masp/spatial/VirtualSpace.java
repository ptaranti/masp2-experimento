package masp.spatial;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import masp.simulacrum.Spatial2Dstate;

public class VirtualSpace {

	static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	static Lock readLock = rwl.readLock();
	Lock writeLock = rwl.writeLock();

	Hashtable<String, Spatial2Dstate> spatial2DstateAll = new Hashtable<String, Spatial2Dstate>();

	private static VirtualSpace singleton = new VirtualSpace();

	public static VirtualSpace getInstance() {
		readLock.lock();
		try {
			return singleton;
		} finally {
			readLock.unlock();
		}
	}

	public void putAvatar(String key, Spatial2Dstate spatial2Dstate) {
		writeLock.lock();
		try {
			singleton.spatial2DstateAll.put(key, spatial2Dstate);
		} finally {
			writeLock.unlock();
		}
	}

	public void removeAvatar(String key, Spatial2Dstate spatial2Dstate) {
		writeLock.lock();
		try {
			singleton.spatial2DstateAll.remove(key);
		} finally {
			writeLock.unlock();
		}
	}

	public void getAvatar(String key, Spatial2Dstate spatial2Dstate) {
		readLock.lock();
		try {
			singleton.spatial2DstateAll.get(key);
		} finally {
			readLock.unlock();
		}
	}

	public Hashtable<String, Spatial2Dstate> cloneSpatial2DstateAll() {

		Hashtable<String, Spatial2Dstate> cloneSpatial2DstateAll = new Hashtable<String, Spatial2Dstate>();
		readLock.lock();
		try {
			Enumeration<String> nomesAvatares = this.spatial2DstateAll.keys();
			while (nomesAvatares.hasMoreElements()) {
				String key = nomesAvatares.nextElement();
				cloneSpatial2DstateAll.put(new String(key),
						spatial2DstateAll.get(key).clone());
			}
		} finally {
			readLock.unlock();
		}

		return cloneSpatial2DstateAll;

	}

	public String toString(){
		String string = new String();
		readLock.lock();
			try {
			Enumeration<String> nomesAvatares = this.spatial2DstateAll.keys();
			while (nomesAvatares.hasMoreElements()) {
				String key = nomesAvatares.nextElement();
				string = string +( "agent " + key + " spatial state = " + spatial2DstateAll.get(key).toString() + "\n");
			}
		} finally {
			readLock.unlock();
		}
return string;

	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
