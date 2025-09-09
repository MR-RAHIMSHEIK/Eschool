package com.jts.login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

/**
 *@author Rahim Sheik
 *@created 08-Sept-2025
 */
@Configuration
@Getter
public class AppProperties {

	@Value("${app.upload.dir}")
	private String uploadDir;

}
