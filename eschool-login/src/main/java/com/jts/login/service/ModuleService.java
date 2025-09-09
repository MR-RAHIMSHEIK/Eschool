package com.jts.login.service;

import com.jts.login.dto.ModuleDto;
import com.jts.login.repo.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public Set<ModuleDto> getModulesForUser(String username) {
        return moduleRepository.findModulesByUsername(username)
                .stream()
                .map(m -> new ModuleDto(m.getId(), m.getName(), m.getIcon(), m.getRoute()))
                .collect(Collectors.toSet());
    }
}
