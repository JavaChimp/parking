package com.javachimp.common;

import com.javachimp.common.naming.Registry;

public class ApplicationContext {

	private Registry registry;
	
	private ApplicationContext(Builder builder) {
		this.registry = builder.registry;
	}
	
	public Registry getRegistry() {
		return registry;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		
		private Registry registry;
		
		private Builder() {
			super();
		}
		
		public  Builder setRegistry(Registry registry) {
			this.registry = registry;
			return this;
		}
		
		public ApplicationContext build() {
			return new ApplicationContext(this);
		}
	}
}
