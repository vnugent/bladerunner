package org.vnguyen.bladerunner;

/**
 * Define the transformation of a Kubernete object (pod, service, replication) to a simpler view
 * @param <T> Kubernetes object to be transformed
 */
public interface Adapter<T> {
	KubernetesResource from(T resource);
}
