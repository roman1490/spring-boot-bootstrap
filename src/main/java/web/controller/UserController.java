package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    private UserService userService;
    private RoleService roleService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @PostMapping("admin/reg")
    public String regUser(@ModelAttribute User newUser,
                          @RequestParam(name = "ageNewUser", required = false) Integer age,
                          @RequestParam(name = "rolesNewUser", required = false) List<Integer> roles) {

        newUser.setAge(age);
        if (roles.size() > 0) {
            roles.forEach(roleIndex -> newUser.getRoles().add(roleService.getRoleById(roleIndex)));
        }

        userService.addUser(newUser);

        return "redirect:/admin";
    }

    @PatchMapping("admin/{id}")
    public String update(@ModelAttribute("userFromList") User userFromList,
                         @PathVariable("id") int id,
                         @RequestParam(name = "firstName", required = false) String firstName,
                         @RequestParam(name = "lastName", required = false) String lastName,
                         @RequestParam(name = "age", required = false) Integer age,
                         @RequestParam(name = "email", required = false) String email,
                         @RequestParam(name = "password", required = false) String password,
                         @RequestParam(name = "rolesEditUser", required = false) List<Integer> roles) {
        userFromList.setFirstName(firstName);
        userFromList.setLastName(lastName);
        userFromList.setAge(age);
        userFromList.setEmail(email);
        if(!password.isEmpty()) {
            userFromList.setPassword(password);
        }
        userFromList.getRoles().clear();
        if (roles.size() > 0) {
            roles.forEach(roleIndex -> userFromList.getRoles().add(roleService.getRoleById(roleIndex)));
        }
        userService.updateUser(id, userFromList);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("user")
    public String getUserPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.getUserByEmail(auth.getName());
        if (user.hasRole("ROLE_ADMIN")) {
            return "redirect:/admin";
        }

        model.addAttribute("user", user);
        return "userPage";
    }

    @GetMapping("admin")
    public String getAdminPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.getUserByEmail(auth.getName());
        model.addAttribute("user", user);

        User newUser = new User();
        model.addAttribute("newUser", newUser);

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("roles", roleService.getRoleList());
        return "adminPage";
    }

    @GetMapping("admin/user/{id}")
    public String getPersonalPage(Model model, @PathVariable("id") int id) {

        model.addAttribute("roles", roleService.getRoleList());
        model.addAttribute("user", userService.getUserById(id));
        return "userPageFromAdminPanel";
    }
}
