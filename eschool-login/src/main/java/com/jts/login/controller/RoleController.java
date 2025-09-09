package com.jts.login.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jts.login.domain.Role;
import com.jts.login.repo.RoleRepository;

@RestController
@RequestMapping(path="/api/roles")
public class RoleController {

	 private final RoleRepository roleRepo;

	    public RoleController(RoleRepository roleRepo) {
	        this.roleRepo = roleRepo;
	    }

	    @PostMapping
	    public Role createRole(@RequestBody Role role) {
	        return roleRepo.save(role);
	    }

	    @GetMapping
	    public List<Role> getAllRoles() {
	        return roleRepo.findAll();
	    }

	    @PutMapping("/{id}")
	    public Role updateRole(@PathVariable Integer id, @RequestBody Role roleDetails) {
	        Role role = roleRepo.findById(id).orElseThrow();
	        role.setName(roleDetails.getName());
	        return roleRepo.save(role);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteRole(@PathVariable Integer id) {
	        roleRepo.deleteById(id);
	    }
}
