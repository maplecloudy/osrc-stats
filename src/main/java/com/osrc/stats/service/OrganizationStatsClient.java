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
@FeignClient(name = "organization-osrc-center", url = "localhost:16891", fallbackFactory = OrganizationStatsClientFallbackFactory.class)
public interface OrganizationStatsClient {

	/**
	 * 获取组织stats信息
	 * @param organizationId 组织ID
	 * @return stats信息
	 */
	@RequestMapping(value = "/api/organizations/stats", method = RequestMethod.GET)
	OsrcStatsContent getOrganizationStats(@RequestParam("organizationId") Integer organizationId);

}
