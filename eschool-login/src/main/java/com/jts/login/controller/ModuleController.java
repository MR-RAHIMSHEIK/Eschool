package com.jts.login.controller;

import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jts.login.dto.ModuleDto;
import com.jts.login.service.ModuleService;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

	private final ModuleService moduleService;

	public ModuleController(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	@GetMapping("/userModules")
	public Set<ModuleDto> getUserModules(Authentication authentication) {
		String username = authentication.getName();
		return moduleService.getModulesForUser(username);
	}
}

