package org.vnguyen.bladerunner;

import java.util.concurrent.TimeUnit;

/**
 * <p>This filter defines the amount of time a resource is allowed to live inclusively</p>
 * {@code SimpleAgingFilter threeDays = new SimpleAgingFilter(3, TimeUnit.DAYS);}
 */
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
