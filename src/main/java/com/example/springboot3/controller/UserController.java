package com.example.springboot3.controller;

import com.example.springboot3.service.RoleService;
import com.example.springboot3.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.springboot3.model.Role;
import com.example.springboot3.model.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }
    @GetMapping(value = "/user")
    public String userInfo(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "userpage";
    }
    @GetMapping(value = "/admin")
    public String listUsers(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "all-user";
    }
    @GetMapping(value = "/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "create";
    }
    @PostMapping(value = "/admin/add-user")
    public String addUser(@ModelAttribute @Valid User user, BindingResult bindingResult,
                          @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {

        if (bindingResult.hasErrors()) {
            return "create";
        } else {
            Set<Role> roleSet = new HashSet<>();
            roleService.checkBoxRole(checkBoxRoles);
            user.setRoles(roleSet);
            userService.addUser(user);
            return "redirect:/admin";
        }
    }
    @GetMapping(value = "/{id}/edit")
    public String editUserForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }
    @PatchMapping(value = "/{id}")
    public String editUser(@ModelAttribute@Valid User user, BindingResult bindingResult,
                           @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
        if (bindingResult.hasErrors()) {
            return "edit";
        } else {
            Set<Role> roleSet = new HashSet<>();
            roleService.checkBoxRole(checkBoxRoles);
            user.setRoles(roleSet);
            userService.updateUser(user);
            return "redirect:/admin";
        }
    }
    @DeleteMapping(value = "/remove/{id}")
    public String removeUser(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }
}
