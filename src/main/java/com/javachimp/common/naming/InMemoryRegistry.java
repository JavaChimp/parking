package com.javachimp.common.naming;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@SuppressWarnings({"rawtypes", "unchecked"})
public class InMemoryRegistry implements Registry {
	
	
	private final Map registry = new ConcurrentHashMap(); 
	
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
	private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
	
	@Override
	public <T> boolean register(RegistryKeys key, T object, Class<T> clazz) {
		
		try {
			writeLock.tryLock();
			return registry.putIfAbsent(key, object) == null;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean unregister(RegistryKeys key) {
		
		try {
			readLock.tryLock();
			return registry.remove(key) != null;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public <T> T find(RegistryKeys key) {
		return (T) registry.get(key);
	}
	
	@Override
	public <T> T find(RegistryKeys key, T clazz) {
		return (T) registry.get(key);
	}

}
