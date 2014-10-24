package org.vnguyen.bladerunner;

import org.apache.stratos.kubernetes.api.model.Pod;

/**
 * Transform a Kubernetes Pod into a simpler {@link KubernetesResource}
 * 
 */
public class PodAdapter implements Adapter<Pod> {

	@Override
	public KubernetesResource from(Pod pod) {
		return new KubernetesResource(pod.getId(), pod.getKind(), pod.getCreationTimestamp());
	}

}
