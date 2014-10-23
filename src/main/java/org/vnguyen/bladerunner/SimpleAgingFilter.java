package org.vnguyen.bladerunner;

import java.util.concurrent.TimeUnit;

public class SimpleAgingFilter {
	private long expiration;
	private TimeUnit expirationTimeUnit;
	private String name;
	
	public SimpleAgingFilter(long expirationAter, TimeUnit timeUnit) {
		this.expiration = expirationAter;
		this.expirationTimeUnit = timeUnit;
		name = "SimpleAgingFilter["+TimeUnit.DAYS.convert(expirationAter, timeUnit) + " days]";
	}
	
	boolean accepted(long age, TimeUnit ageUnit) {
		return expiration - expirationTimeUnit.convert(age, ageUnit) > 0 ? false : true;
	}
	
	@Override
	public String toString() {
		return name; 
	}
}
