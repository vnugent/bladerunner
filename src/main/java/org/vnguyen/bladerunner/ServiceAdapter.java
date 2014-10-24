package org.vnguyen.bladerunner;

import org.apache.stratos.kubernetes.api.model.Service;

/**
 * Transform a Kubernetes Service into a simpler {@link KubernetesResource}
 */
public class ServiceAdapter implements Adapter<Service> {

	@Override
	public KubernetesResource from(Service service) {
		return new KubernetesResource(service.getId(), service.getKind(), service.getCreationTimestamp());
	}

}
