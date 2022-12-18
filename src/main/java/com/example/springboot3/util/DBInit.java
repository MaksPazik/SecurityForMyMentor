package com.example.springboot3.util;

import com.example.springboot3.model.Role;
import com.example.springboot3.model.User;
import com.example.springboot3.service.RoleService;
import com.example.springboot3.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DBInit {
    private final RoleService roleService;
    private final UserService userService;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DBInit(RoleService roleService, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @PostConstruct
    private void postConstruct() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        roleService.addRole(adminRole);
        roleService.addRole(userRole);
        Set<Role> roles_admin = new HashSet<>();
        roles_admin.add(roleService.getRoleByName("ROLE_ADMIN"));
        User admin = new User("Max", "Pazik", "admin@mail.ru", "admin", roles_admin);
        userService.addUser(admin);
        Set<Role> roles_user = new HashSet<>();
        roles_user.add(roleService.getRoleByName("ROLE_USER"));
        User user = new User("Sveta", "Mentor", "Mentor@mail.ru", "mentor",  roles_user);
        userService.addUser(user);
    }
}
