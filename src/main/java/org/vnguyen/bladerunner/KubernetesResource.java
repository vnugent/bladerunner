package org.vnguyen.bladerunner;

/**
 * A simplified view of a Kubernetes object
 */
public class KubernetesResource {
	final String id;
	final String type;
	final String creationTimestamp;
	
	public KubernetesResource(String id, String type, String creationTimestamp) {
		this.id = id;
		this.type = type;
		this.creationTimestamp = creationTimestamp;
	}
	
}
