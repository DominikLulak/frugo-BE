package com.lulak.frugo.controller.admin;

import com.lulak.frugo.dto.rbac.user.AdminUserDetailDto;
import com.lulak.frugo.dto.rbac.user.AdminUserListDto;
import com.lulak.frugo.service.rbac.user.AdminUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(
            AdminUserService adminUserService
    ){
        this.adminUserService = adminUserService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public List<AdminUserListDto> getUsers(){ return adminUserService.getAllUsers(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('WAREHOUSE_READ')")
    public AdminUserDetailDto getUserDetail(
            @PathVariable Integer id
    ){
        return adminUserService.getUserDetail(id);
    }
}
