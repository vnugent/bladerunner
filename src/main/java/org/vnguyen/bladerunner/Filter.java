package org.vnguyen.bladerunner;

public interface Filter<T> {
	boolean accept(Adapter<T> adapter, T resource);
}
