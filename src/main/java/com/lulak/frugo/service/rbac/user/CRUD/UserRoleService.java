package com.lulak.frugo.service.rbac.user.CRUD;

import com.lulak.frugo.model.auth.EmployeeRole;
import com.lulak.frugo.model.auth.EmployeeRoleId;
import com.lulak.frugo.model.auth.Role;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.employee.EmployeeLogin;
import com.lulak.frugo.repository.auth.EmployeeRoleRepository;
import com.lulak.frugo.repository.employee.EmployeeLoginRepository;
import com.lulak.frugo.repository.employee.EmployeeRepository;
import com.lulak.frugo.repository.rbac.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class UserRoleService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final EmployeeLoginRepository employeeLoginRepository;
    private final EmployeeRoleRepository employeeRoleRepository;

    public UserRoleService(EmployeeRepository employeeRepository, RoleRepository roleRepository, EmployeeLoginRepository employeeLoginRepository, EmployeeRoleRepository employeeRoleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.employeeLoginRepository = employeeLoginRepository;
        this.employeeRoleRepository = employeeRoleRepository;
    }

    @Transactional
    public void addRoleToUser(
            Integer employeeId,
            Integer roleId
    ){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found " + employeeId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found " + roleId));

        if("GOD".equals(role.getCode())){
            String username = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

            boolean activeUserHasGodRole =
                    employeeRoleRepository.existsByUsernameAndRoleCode(username, "GOD");

            if(!activeUserHasGodRole){
                throw new RuntimeException("Only a GOD user can assign the GOD role");
            }
        }

        EmployeeRole employeeRole = new EmployeeRole();

        employeeRole.setId(new EmployeeRoleId(employeeId, roleId));
        employeeRole.setEmployee(employee);
        employeeRole.setRole(role);

        employeeRoleRepository.save(employeeRole);
    }

    @Transactional
    public void removeRoleFromUser(
            Integer targetEmployeeId,
            Integer roleId
    ){
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        EmployeeLogin activeLogin = employeeLoginRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Active login not found"));

        Integer activeEmployeeId = activeLogin.getEmployeeId();

        if(activeEmployeeId.equals(targetEmployeeId)){
            throw new IllegalStateException("You cannot remove your own role.");
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if("GOD".equals(role.getCode())){
            boolean activeUserHasGodRole =
                    employeeRoleRepository.existsByUsernameAndRoleCode(username, "GOD");

            if(!activeUserHasGodRole){
                throw new IllegalStateException("Only GOD user can remove the GOD role");
            }

            long remainingGodUsers =
                    employeeRoleRepository.countActiveGodUsersExcludingEmployee(targetEmployeeId, "GOD");

            if(remainingGodUsers < 1){
                throw new IllegalStateException("At least one active GOD user must remain");
            }
        }

        EmployeeRoleId employeeRoleId = new EmployeeRoleId(targetEmployeeId, roleId);

        if(!employeeRoleRepository.existsById(employeeRoleId)){
            throw new RuntimeException("Employee does not have this role");
        }

        employeeRoleRepository.deleteById(employeeRoleId);
    }
}
