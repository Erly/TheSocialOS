package net.thesocialos.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
	
	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
	
	private PMF() {
	}
}
