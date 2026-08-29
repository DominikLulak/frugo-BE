package com.lulak.frugo.controller.auth;

import com.lulak.frugo.dto.LoginRequest;
import com.lulak.frugo.model.auth.EmployeeRole;
import com.lulak.frugo.model.employee.EmployeeLogin;
import com.lulak.frugo.repository.auth.EmployeeRoleRepository;
import com.lulak.frugo.repository.employee.EmployeeLoginRepository;
import com.lulak.frugo.security.JwtSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final EmployeeLoginRepository employeeLoginRepository;
    private final EmployeeRoleRepository employeeRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtSecurity jwtSecurity;

    public AuthController(
            EmployeeLoginRepository employeeLoginRepository,
            EmployeeRoleRepository employeeRoleRepository,
            PasswordEncoder passwordEncoder,
            JwtSecurity jwtSecurity
    ){
        this.employeeLoginRepository = employeeLoginRepository;
        this.employeeRoleRepository = employeeRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtSecurity = jwtSecurity;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request){

        EmployeeLogin login = employeeLoginRepository
                .findByUsername(request.getUsername())
                .orElse(null);

        if(login == null){
            return Map.of(
                    "success", false,
                    "message", "User not found"
            );
        }

        List<EmployeeRole> employeeRoles =
                employeeRoleRepository.findByEmployee_Id(
                        login.getEmployeeId()
                );

        List<String> roles = employeeRoles.stream()
                .map(employeeRole -> employeeRole.getRole().getCode())
                .toList();

        String token = jwtSecurity.generateToken(
                login.getUsername(),
                roles
        );

        return Map.of(
                "success", true,
                "token", token,
                "roles", roles,
                "username", login.getUsername()
        );
    }
}
