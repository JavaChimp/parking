package com.javachimp.garage;

import com.codahale.metrics.MetricRegistry;
import com.javachimp.common.ApplicationContext;
import com.javachimp.common.naming.InMemoryRegistry;
import com.javachimp.common.naming.Registry;
import com.javachimp.common.naming.RegistryKeys;
import com.javachimp.garage.rate.repository.InMemoryRateRepository;
import com.javachimp.garage.rate.repository.RateRepository;
import com.javachimp.garage.rate.repository.RateRepositoryType;

public class Application  {
	
	private static ApplicationContext context;
	
	private Application() {
		super();
	}	
	
	public static void init() {
		
		Registry registry = new InMemoryRegistry();
		
		String repoType = System.getProperty("RATE_REPOSITORY_TYPE");
		RateRepository rateRepo;
		
		switch (RateRepositoryType.valueOf(repoType)) {		
			case IN_MEMORY: 
				rateRepo = new InMemoryRateRepository("rate-repo.json");
				break;
			default:
			//	rateRepo = new InMemoryRateRepository("rate-repo.json");
				rateRepo = null;
		}
		
		rateRepo.init();
		
		registry.register(RegistryKeys.RATE_REPOSITORY, rateRepo, RateRepository.class);
		registry.register(RegistryKeys.METRICS, new MetricRegistry(), MetricRegistry.class);
		context = ApplicationContext.builder()
									.setRegistry(registry)
									.build();

		return;
	}
	
	public static ApplicationContext getContext() {
		return context;
	}
}
