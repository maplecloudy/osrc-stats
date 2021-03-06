package com.osrc.stats.service;

import com.osrc.stats.pojo.OsrcStatsContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tom
 * @date 2022/5/17 11:45
 */
@FeignClient(name = "user-osrc-center", url = "https://osrc.com", fallbackFactory = UserStatsClientFallbackFactory.class)
public interface UserStatsClient {

	/**
	 * 获取用户stats信息
	 * @param username 用户名称
	 * @return stats信息
	 */
	@RequestMapping(value = "/api/users/stats", method = RequestMethod.GET)
	OsrcStatsContent getUserStats(@RequestParam("username") String username);

}
