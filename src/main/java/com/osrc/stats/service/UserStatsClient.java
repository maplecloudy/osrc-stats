package com.osrc.stats.service;

import com.osrc.stats.pojo.UserStatsContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tom
 * @date 2022/5/17 11:45
 */
@FeignClient(name = "osrc-center", url = "localhost:16891", fallback = UserStatsClientFallback.class)
public interface UserStatsClient {

	/**
	 * 获取用户stats信息
	 * @param username 用户名称
	 * @return stats信息
	 */
	@RequestMapping(value = "/api/users/stats", method = RequestMethod.GET)
	UserStatsContent getUserStats(@RequestParam("username") String username);

}
