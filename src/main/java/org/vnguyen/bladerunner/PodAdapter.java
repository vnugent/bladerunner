package org.vnguyen.bladerunner;

import org.apache.stratos.kubernetes.api.model.Pod;

public class PodAdapter implements Adapter<Pod> {

	@Override
	public KubernetesResource from(Pod pod) {
		return new KubernetesResource(pod.getId(), pod.getKind(), pod.getCreationTimestamp());
	}

}
