package com.javachimp.common.lifecycle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface LifeCycle {
	
	public abstract void init();
	public default void destroy() {
		Logger logger = LogManager.getLogger(LifeCycle.class);
		logger.info(()-> "Licycle Aware Object is being destroyed");
	}

}
