package org.vnguyen.bladerunner;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterAdapter {
	private Logger log = LoggerFactory.getLogger(FilterAdapter.class);

	private SimpleAgingFilter filter;
	
	public FilterAdapter(SimpleAgingFilter filter) {
		this.filter = filter;
	}
	
	public <T> boolean accepted(Adapter<T> adapter, T resource) {
		KubernetesResource res = adapter.from(resource);
		try {
			Date creationTS = ISO8601DateParser.parse(res.creationTimestamp);
			long age = System.currentTimeMillis() - creationTS.getTime();
			log.info("Name: {}, Kind: {} age: {} hrs, creation ts: {}", res.id, res.type, TimeUnit.MILLISECONDS.toHours(age), creationTS);
			return filter.accepted(age, TimeUnit.MILLISECONDS);
		} catch (ParseException e) {
			log.error("Error parsing {}", res.creationTimestamp);
			return false;
		}
	}

}