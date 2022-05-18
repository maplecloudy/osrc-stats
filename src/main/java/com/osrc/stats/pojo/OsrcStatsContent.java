package com.osrc.stats.pojo;

/**
 * osrc stats entity
 * @author tom
 * @date 2022/5/16 16:03
 */
public class OsrcStatsContent {

	/**
	 * 主体下项目数量
	 */
	private int totalProject;

	/**
	 * 主体下运行时数量
	 */
	private int totalRunTime;

	/**
	 * 主体下项目已获取的总喜欢数
	 */
	private int totalStarEarned;

	/**
	 * 主体下运行时被点击观看的总次数
	 */
	private int totalWatched;

	/**
	 * 主体的follower数量
	 */
	private int totalFollower;

	/**
	 * 主体的总体质量等级 A A+ A++ A+++
	 */
	private String rank = "A+";

	public int getTotalProject() {
		return totalProject;
	}

	public void setTotalProject(int totalProject) {
		this.totalProject = totalProject;
	}

	public int getTotalRunTime() {
		return totalRunTime;
	}

	public void setTotalRunTime(int totalRunTime) {
		this.totalRunTime = totalRunTime;
	}

	public int getTotalStarEarned() {
		return totalStarEarned;
	}

	public void setTotalStarEarned(int totalStarEarned) {
		this.totalStarEarned = totalStarEarned;
	}

	public int getTotalWatched() {
		return totalWatched;
	}

	public void setTotalWatched(int totalWatched) {
		this.totalWatched = totalWatched;
	}

	public int getTotalFollower() {
		return totalFollower;
	}

	public void setTotalFollower(int totalFollower) {
		this.totalFollower = totalFollower;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
}
