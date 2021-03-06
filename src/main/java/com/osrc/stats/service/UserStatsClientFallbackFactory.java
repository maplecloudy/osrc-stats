package com.osrc.stats.service;

import com.osrc.stats.pojo.OsrcStatsContent;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * user stats fall back factory
 * @author tom
 * @date 2022/5/17 11:45
 */
@Component
public class UserStatsClientFallbackFactory implements FallbackFactory<UserStatsClient> {

	private static final Logger log = LoggerFactory.getLogger(UserStatsClientFallbackFactory.class);

	@Override
	public UserStatsClient create(Throwable throwable) {
		log.warn(throwable.getMessage());
		return username -> new OsrcStatsContent(username);
	}
}
