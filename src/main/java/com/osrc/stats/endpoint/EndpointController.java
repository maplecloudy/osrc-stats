package com.osrc.stats.endpoint;

import com.osrc.stats.pojo.Constant;
import com.osrc.stats.pojo.UserStatsContent;
import com.osrc.stats.service.UserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tom
 * @date 2022/5/16 15:32
 */
@Controller
@RequestMapping("/stats")
public class EndpointController {

	private static final String STATS_PAGE = "stats_";

	@Autowired
	private UserStatsService statsService;

	@GetMapping(produces = "image/svg+xml")
	public String userStatsInfo(@RequestParam(value = "username") String username,
			@RequestParam(value = "theme", required = false, defaultValue = Constant.DEFAULT) String theme, Model model) {
		UserStatsContent statsContent = statsService.getUserStatsContent(username);
		model.addAttribute("stats", statsContent);
		return STATS_PAGE + theme;
	}
}
