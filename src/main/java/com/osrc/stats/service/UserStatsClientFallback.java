package com.osrc.stats.service;

import com.osrc.stats.pojo.UserStatsContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * fall back
 * @author tom
 * @date 2022/5/17 11:45
 */
public class UserStatsClientFallback implements UserStatsClient {

	@Override
	public UserStatsContent getUserStats(String username) {
		return new UserStatsContent();
	}

}
