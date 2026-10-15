package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.rbac.user.AdminUserDetailDto;
import com.lulak.frugo.dto.rbac.user.AdminUserListDto;
import com.lulak.frugo.service.rbac.user.AdminUserService;
import com.lulak.frugo.service.rbac.user.CRUD.UserRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;
    private final UserRoleService userRoleService;

    public AdminUserController(
            AdminUserService adminUserService,
            UserRoleService userRoleService){
        this.adminUserService = adminUserService;
        this.userRoleService = userRoleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USERS_READ')")
    public List<AdminUserListDto> getUsers(){ return adminUserService.getAllUsers(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USERS_READ')")
    public AdminUserDetailDto getUserDetail(
            @PathVariable Integer id
    ){
        return adminUserService.getUserDetail(id);
    }

    @PostMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasAuthority('USERS_MANAGE')")
    public ResponseEntity<Void> addRoleToUser(
            @PathVariable Integer userId,
            @PathVariable Integer roleId
    ){
        userRoleService.addRoleToUser(
                userId,
                roleId
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasAuthority('USERS_MANAGE')")
    public ResponseEntity<Void> removeRoleFromUser(
            @PathVariable Integer userId,
            @PathVariable Integer roleId
    ){
        userRoleService.removeRoleFromUser(
                userId,
                roleId
        );

        return ResponseEntity.noContent().build();
    }
}
