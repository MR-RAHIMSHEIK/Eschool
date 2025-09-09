package com.jts.login.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jts.login.domain.Employee;
import com.jts.login.domain.User;
import com.jts.login.dto.EmployeeRequest;
import com.jts.login.dto.EmployeeResponse;
import com.jts.login.dto.SignupRequest;
import com.jts.login.dto.SignupResponse;
import com.jts.login.repo.EmployeeRepository;
import com.jts.login.repo.LoginRepository;

/**
 *@author Rahim Sheik
 *@created 07-Sept-2025
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginRepository loginRepository;
    
	@Autowired
	private PasswordEncoder passwordEncoder;

    // CREATE Employee
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        // 1. Check duplicate employeeCode
        Optional<Employee> existing = employeeRepository.findByEmployeeCodeIgnoreCase(request.getEmployeeCode());
        if (existing.isPresent()) {
            throw new RuntimeException("Employee code already exists: " + request.getEmployeeCode());
        }

        // 2. Map request → entity
        Employee employee = mapToEntity(request);

        // 3. Save employee first
        employee = employeeRepository.save(employee);

        // 4. If isAppUser → create User
        if (Boolean.TRUE.equals(request.getIsAppUser())) {
            SignupRequest signup = new SignupRequest();
            signup.setName(request.getEmployeeName());
            signup.setUsername(request.getUsername());
            signup.setPassword(passwordEncoder.encode(request.getPassword()));
            signup.setAddress(request.getAddress());
            signup.setMobileno(request.getPrimaryContact());
            signup.setAge(String.valueOf(request.getAge()));

            SignupResponse signupResponse = loginService.doRegister(signup);

            // Fetch created user & link it
            User user = loginRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User creation failed: " + signupResponse.getResponse()));
            user.setEmployee(employee);
            loginRepository.save(user);
            employee.setIsAppUser(true);
        }

        return mapToResponse(employee);
    }

    // UPDATE Employee
    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + id));

        employee.setEmployeeName(request.getEmployeeName());
        employee.setQualification(request.getQualification());
        employee.setCategory(request.getCategory());
        employee.setDob(request.getDob());
        employee.setAge(request.getAge());
        employee.setPrimaryContact(request.getPrimaryContact());
        employee.setSecondaryContact(request.getSecondaryContact());
        employee.setAddress(request.getAddress());
        employee.setIsAppUser(request.getIsAppUser());
        employee.setGender(request.getGender());
        employee.setPhotograph(request.getPhotograph());
        // Handle user linkage
        if (Boolean.TRUE.equals(request.getIsAppUser())) {
            if (employee.getUser() == null) {
                // Create new user if none exists
                SignupRequest signup = new SignupRequest();
                signup.setName(request.getEmployeeName());
                signup.setUsername(request.getUsername());
                signup.setPassword(request.getPassword());
                signup.setAddress(request.getAddress());
                signup.setMobileno(request.getPrimaryContact());
                signup.setAge(String.valueOf(request.getAge()));
                loginService.doRegister(signup);

                User user = loginRepository.findByUsername(request.getUsername())
                        .orElseThrow(() -> new RuntimeException("User creation failed"));
                user.setEmployee(employee);
                loginRepository.save(user);
            } else {
                // Update existing user
                User user = employee.getUser();
                user.setName(request.getEmployeeName());
                user.setAddress(request.getAddress());
                user.setMobileNo(request.getPrimaryContact());
                loginRepository.save(user);
            }
        } else {
            if (employee.getUser() != null) {
                User user = employee.getUser();
                loginRepository.delete(user);
                employee.setUser(null);
            }
        }

        employee = employeeRepository.save(employee);
        return mapToResponse(employee);
    }

    // DELETE Employee
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + id));

        if (employee.getUser() != null) {
            loginRepository.delete(employee.getUser());
        }
        employeeRepository.delete(employee);
    }

    // GET Employee by ID
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + id));
        return mapToResponse(employee);
    }

    // GET all Employees
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // --- Helpers ---
    private Employee mapToEntity(EmployeeRequest request) {
        Employee e = new Employee();
        e.setEmployeeCode(request.getEmployeeCode());
        e.setEmployeeName(request.getEmployeeName());
        e.setQualification(request.getQualification());
        e.setCategory(request.getCategory());
        e.setDob(request.getDob());
        e.setAge(request.getAge());
        e.setPrimaryContact(request.getPrimaryContact());
        e.setSecondaryContact(request.getSecondaryContact());
        e.setAddress(request.getAddress());
        e.setGender(request.getGender());
        e.setPhotograph(request.getPhotograph());
        e.setIsAppUser(request.getIsAppUser());
        return e;
    }

    private EmployeeResponse mapToResponse(Employee e) {
        EmployeeResponse r = new EmployeeResponse();
        r.setId(e.getId());
        r.setEmployeeCode(e.getEmployeeCode());
        r.setEmployeeName(e.getEmployeeName());
        r.setQualification(e.getQualification());
        r.setCategory(e.getCategory());
        r.setDob(e.getDob());
        r.setAge(e.getAge());
        r.setPrimaryContact(e.getPrimaryContact());
        r.setSecondaryContact(e.getSecondaryContact());
        r.setAddress(e.getAddress());
        r.setGender(e.getGender());
        r.setPhotograph(e.getPhotograph());
        r.setIsAppUser(e.getIsAppUser());

        if (e.getUser() != null) {
            r.setUserId(e.getUser().getId());
            r.setUsername(e.getUser().getUsername());
        }
        return r;
    }
}

