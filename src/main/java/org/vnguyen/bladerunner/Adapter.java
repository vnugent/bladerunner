package org.vnguyen.bladerunner;

public interface Adapter<T> {
	KubernetesResource from(T resource);
}
