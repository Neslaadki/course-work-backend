package com.example.courseworkbackend.controllers;

import com.example.courseworkbackend.entities.User;
import com.example.courseworkbackend.entities.dao.requests.EmployeeD;
import com.example.courseworkbackend.repositories.CoordinateRepository;
import com.example.courseworkbackend.repositories.UserRepository;
import com.example.courseworkbackend.services.CoordinatorService;
import com.example.courseworkbackend.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private CoordinatorService coordinatorService;
    @Autowired
    private CoordinateRepository coordinateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeService employeeService;

    private HashMap<String, String> responseMap;

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> userAuth(@RequestBody EmployeeD employeeD) throws NoSuchAlgorithmException {
        responseMap = new HashMap<>();
        System.out.println("Проверку проходит пользователь: " + employeeD.toString());
        User userWithSameLogin = userRepository.findUserByLogin(employeeD.getLogin());
        if (userWithSameLogin != null && Objects.equals(userWithSameLogin.getPassword(), employeeService.enctyptPass(employeeD.getPassword()))) {
            responseMap.put("id", userWithSameLogin.getEmployee().getId_employee().toString());
            responseMap.put("result", "true");
            responseMap.put("role", userWithSameLogin.getEmployee().getPositionId().getPosition_name());
            responseMap.put("role_id", userWithSameLogin.getEmployee().getPositionId().getPosition_id().toString());
            responseMap.put("country_id", userWithSameLogin.getEmployee().getHuman().getCountry().getId_country().toString());
            responseMap.put("access_level", userWithSameLogin.getEmployee().getAccessLevel().toString());
        } else {
            responseMap.put("id", null);
            responseMap.put("result", "false");
            responseMap.put("role", null);
            responseMap.put("role_id", null);
            responseMap.put("country_id", null);
            responseMap.put("access_level", null);
        }
        return responseMap;
    }

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> userRegistration(@RequestBody EmployeeD employeeD) throws NoSuchAlgorithmException {
        responseMap = new HashMap<>();
        try {
            boolean result = false;

            if (employeeD.getId_human() == null) {
                result = employeeService.addNewEmployee(
                        employeeD.getFirstName().trim(),
                        employeeD.getLastName().trim(),
                        employeeD.getBirthday(),
                        employeeD.getCountryId(),
                        employeeD.getPositionId(),
                        employeeD.getExperience(),
                        employeeD.getAccessLevel(),
                        new Timestamp(System.currentTimeMillis()),
                        null,
                        employeeD.getLogin().trim(),
                        employeeD.getPassword().trim()
                );
            } else {
                result = employeeService.addExistEmployee(
                        employeeD.getId_human(),
                        employeeD.getPositionId(),
                        employeeD.getExperience(),
                        employeeD.getAccessLevel(),
                        new Timestamp(System.currentTimeMillis()),
                        null,
                        employeeD.getLogin().trim(),
                        employeeD.getPassword().trim(),
                        employeeD.getCountryId());
            }

            responseMap.put("login", employeeD.getLogin());
            responseMap.put("password", employeeD.getPassword());
            responseMap.put("position", employeeService.getPositionNameById(employeeD.getPositionId()));
            responseMap.put("result", result ? "true" : "false");
            responseMap.put("access_level", employeeD.getAccessLevel().toString());
        } catch (Exception e) {
            responseMap.put("result", "false");
        }

        return responseMap;
    }


}
