package com.lulak.frugo.service.rbac.user;

import com.lulak.frugo.dto.rbac.role.AdminRoleListDto;
import com.lulak.frugo.dto.rbac.user.AdminUserDetailDto;
import com.lulak.frugo.dto.rbac.user.AdminUserListDto;
import com.lulak.frugo.repository.rbac.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {

    private final UserRepository userRepository;

    public AdminUserService(
            UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    public List<AdminUserListDto> getAllUsers(){ return userRepository.getAllUsers(); }

    public AdminUserDetailDto getUserDetail(Integer id){

        AdminUserDetailDto user =
                userRepository.getUserDetail(id);

        if(user == null){
            throw new RuntimeException("User not found!");
        }

        List<AdminRoleListDto> roles =
                userRepository.getUserRoles(id);

            return new AdminUserDetailDto(
                    user.getId(),
                    user.getEmployeeNumber(),
                    user.getFullName(),
                    user.getLoginName(),
                    user.getSystemUsername(),
                    user.getDepartmentName(),
                    user.getJobPositionName(),
                    roles
            );
    }
}
