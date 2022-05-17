package com.osrc.stats.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static freemarker.template.Configuration.VERSION_2_3_31;

/**
 * @author tom
 * @date 2022/5/17 09:32
 */
@Service
public class XmlTemplateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlTemplateService.class);

	private final Map<String, Template> templateMap = new ConcurrentHashMap<>(8);

	public String render(String templateFile, Map<String, Object> param) throws Exception {
		Template template = getTemplate(templateFile);
		StringWriter sw = new StringWriter();
		template.process(param, sw);
		return sw.toString();
	}

	private Template getTemplate(String name){
		Template template = templateMap.get(name);
		if (template == null) {
			Configuration cfg = new Configuration(VERSION_2_3_31);
			cfg.setClassForTemplateLoading(this.getClass(), "/resources/");
			try {
				template = cfg.getTemplate(name);
				templateMap.put(name, template);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return template;
	}
}
