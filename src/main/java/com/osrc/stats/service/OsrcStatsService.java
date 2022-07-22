package com.osrc.stats.service;

import com.osrc.stats.pojo.OsrcStatsContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author tom
 * @date 2022/5/16 16:21
 */
@Service
public class OsrcStatsService {

	@Qualifier("com.osrc.stats.service.UserStatsClient")
	@Autowired
	private UserStatsClient userStatsClient;

	@Autowired
	private OrganizationStatsClient organizationStatsClient;

	/**
	 * 根据osrc服务获取指定用户的stats指标信息
	 * @param username 用户名称
	 * @return stats content
	 */
	public OsrcStatsContent getUserStatsContent(String username) {
		OsrcStatsContent statsContent = userStatsClient.getUserStats(username);
		statsContent.setRank(calculateStatsRank(statsContent));
		if (statsContent.getName() == null) {
			statsContent.setName(username);
		}
		return statsContent;
	}

	/**
	 * 根据osrc服务获取指定组织的stats指标信息
	 * @param organizationId 组织ID
	 * @return stats content
	 */
	public OsrcStatsContent getOrganizationStatsContent(Integer organizationId) {
		OsrcStatsContent statsContent = organizationStatsClient.getOrganizationStats(organizationId);
		statsContent.setRank(calculateStatsRank(statsContent));
		return statsContent;
	}

	/**
	 * https://github.com/anuraghazra/github-readme-stats/blob/master/src/calculateRank.js
	 * 根据stats指标计算用户评级
	 * @param statsContent stats
	 * @return 评级  A A+ A++ A+++
	 */
	private String calculateStatsRank(OsrcStatsContent statsContent) {
		int score = statsContent.getTotalProject() * 10 + statsContent.getTotalRunTime() * 5
				+ statsContent.getTotalFollower() * 2 + statsContent.getTotalStarEarned();
		StringBuilder rankBuilder = new StringBuilder("A");
		int maxRank = 3;
		int baseScore = 50;
		while (maxRank >= 0) {
			maxRank--;
			if (score - baseScore >= 0) {
				rankBuilder.append("+");
				baseScore = baseScore * 2;
			} else {
				break;
			}
		}
		return rankBuilder.toString();
	}

}
