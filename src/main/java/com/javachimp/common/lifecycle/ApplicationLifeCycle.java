package com.javachimp.common.lifecycle;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.yaml.YamlConfiguration;

import com.javachimp.garage.Application;

public class ApplicationLifeCycle implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		LoggerContext ctx = (LoggerContext) LogManager.getContext();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		
		try (InputStream cfgInput = cl.getResourceAsStream("log4j2.yml")) {
			ConfigurationSource cfgSource =  new ConfigurationSource(cfgInput);
			YamlConfiguration cfg = new YamlConfiguration(ctx, cfgSource);
			Configurator.initialize(cfg);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		Logger logger = 
				LogManager.getLogger(ApplicationLifeCycle.class);
		
		logger.info(()-> "Spothero Garage MicroService Initializing...");
		Application.init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		return;
	}
}
