package com.javachimp.common.naming;

public interface Registry {
	
	public abstract <T> boolean register(RegistryKeys key, T object, Class<T> clazz);
	public abstract boolean unregister(RegistryKeys key);
	public abstract <T> T find(RegistryKeys key);
	public abstract <T> T find(RegistryKeys key, T clazz);
}
