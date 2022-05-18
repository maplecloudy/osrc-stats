package com.osrc.stats.service;

import com.osrc.stats.pojo.OsrcStatsContent;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * organization stats fall back factory
 * @author tom
 * @date 2022/5/17 11:45
 */
@Component
public class OrganizationStatsClientFallbackFactory implements FallbackFactory<OrganizationStatsClient> {

	private static final Logger log = LoggerFactory.getLogger(
			OrganizationStatsClientFallbackFactory.class);

	@Override
	public OrganizationStatsClient create(Throwable throwable) {
		log.warn(throwable.getMessage(), throwable);
		return username -> new OsrcStatsContent();
	}
}
