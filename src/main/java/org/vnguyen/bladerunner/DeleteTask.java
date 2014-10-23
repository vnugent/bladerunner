package org.vnguyen.bladerunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.stratos.kubernetes.api.exceptions.KubernetesClientException;
import org.apache.stratos.kubernetes.api.model.Pod;
import org.apache.stratos.kubernetes.api.model.PodList;
import org.apache.stratos.kubernetes.api.model.Service;
import org.apache.stratos.kubernetes.api.model.ServiceList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteTask {
	private Logger log = LoggerFactory.getLogger(DeleteTask.class);

	private Main mainContext;
	private SimpleAgingFilter agingFilter; 
	private ExecutorService executor = Executors.newFixedThreadPool(5);

	
	public DeleteTask(Main context) {
		this.mainContext = context;
		agingFilter = new SimpleAgingFilter(1, TimeUnit.DAYS);
		log.info("Creating DeleteTask with Filter {}", agingFilter.toString());
	}
	
	public void delete() {
			FilterAdapter olderThanFunc = new FilterAdapter(agingFilter);
			List<Pod> podsToBeDeleted = new ArrayList<>();
			List<Service> servicesToBeDeleted = new ArrayList<>();
			
			for(Pod pod : getAllPods()) {
				log.info(pod.toString());
				if (olderThanFunc.accepted(new PodAdapter(), pod)) {
					podsToBeDeleted.add(pod);
				}
			}
			
			for(Service service : getAllServices()) {
				log.info(service.toString());
				if (olderThanFunc.accepted(new ServiceAdapter(), service)) {
					servicesToBeDeleted.add(service);
				}				
			}
			
			deletePods(podsToBeDeleted);
			deleteServices(servicesToBeDeleted);
	}
	
	
	private Pod[] getAllPods() {
		try {
			PodList list = mainContext.api().getAllPods();
			if (list == null || list.getItems() == null) {
				return new Pod[] {};
			}
			return list.getItems();
		} catch (KubernetesClientException e) {
			log.error(e.toString(), e);
		}
		return new Pod[] {};
	}
	
	private Service[] getAllServices() {
		try {
			ServiceList list = mainContext.api().getAllServices();
			if (list == null || list.getItems() == null) {
				return new Service[] {};
			}
			return list.getItems();
		} catch (KubernetesClientException e) {
			log.error(e.toString(), e);
		}
		return new Service[] {};	}
	
	private void deletePods(final List<Pod> list) {
		executor.submit(new Runnable() {
			@Override
			public void run() {
				for(Pod pod: list) {
					try {
						mainContext.api().deletePod(pod.getId());
						log.info("Deleting pod: {}", pod.getId());
					} catch (KubernetesClientException e) {
						log.info(e.toString(), e);
					}
				}
			}});
	}
	
	private void deleteServices(final List<Service> list) {
		executor.submit(new Runnable() {
			@Override
			public void run() {
				for(Service pod: list) {
					try {
						mainContext.api().deleteService(pod.getId());
						log.info("Deleting service: {}", pod.getId());
					} catch (KubernetesClientException e) {
						log.info(e.toString(), e);
					}
				}
			}});
	}
}
