package com.osrc.stats.endpoint;

import com.osrc.stats.pojo.Constant;
import com.osrc.stats.pojo.OsrcStatsContent;
import com.osrc.stats.service.OsrcStatsService;
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
	private static final String OSRC_URL = "https://page-os.osrc.com/projects/project_786689368686354432";

	@Autowired
	private OsrcStatsService statsService;

	@GetMapping(produces = "image/svg+xml")
	public String osrcStatsInfo(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "organization", required = false) Integer organization,
			@RequestParam(value = "theme", required = false, defaultValue = Constant.DEFAULT) String theme, Model model) {
		OsrcStatsContent statsContent;
		if (username != null) {
			statsContent = statsService.getUserStatsContent(username);
		} else if (organization != null) {
			statsContent = statsService.getOrganizationStatsContent(organization);
		} else {
			return"redirect:" + OSRC_URL;
		}
		model.addAttribute("stats", statsContent);
		return STATS_PAGE + theme;
	}
}
