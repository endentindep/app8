package demo.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import demo.dao.UserDAO;
import demo.models.Role;
import demo.models.User;
import demo.userservice.UserPrincipal;

@Controller
public class Users {
	@Autowired
	private UserDAO dao;

	@GetMapping("/")
	public String index(ModelMap model) {
		model.addAttribute("users", dao.getUsers());
		model.addAttribute("user", new User());
		return "index";
	}

	@GetMapping("/user")
	public String user(ModelMap model, Principal principal) {
		UserPrincipal up = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", up.toString());
		return "user";
	}

	@GetMapping("/admin")
	public String admin(ModelMap model) {
		model.addAttribute("users", dao.getUsers());
		model.addAttribute("user", new User());
		return "admin";
	}

	@PostMapping(value = "/admin")
	public String adminPost(@RequestParam Map<String, String> data, ModelMap model) {
		User user = new User();
		if (!data.containsKey("id")) {
			Role role = new Role();
			HashSet<Role> set = new HashSet<>();
			role.setName("user");
			role.setUser(user);
			set.add(role);
			if (data.get("role").equals("admin")) {
				Role roleAdmin = new Role();
				roleAdmin.setName("admin");
				roleAdmin.setUser(user);
				set.add(roleAdmin);
			}
			user.setName(data.get("name"));
			user.setPassword(data.get("password"));
			user.setRoles(set);
			dao.saveUser(user);
		} else if (data.containsKey("name")) {
			Role role = new Role();
			role.setName("user");
			HashSet<Role> set = new HashSet<>();
			set.add(role);
			if (data.get("role").equals("admin")) {
				Role roleAdmin = new Role();
				roleAdmin.setName("admin");
				set.add(roleAdmin);
			}
			dao.updateUser(Long.parseLong(data.get("id")), data.get("name"), data.get("password"), set);
		} else {
			dao.removeUser(Long.parseLong(data.get("id")));
		}
		model.addAttribute("users", dao.getUsers());
		model.addAttribute("user", new User());
		return "admin";
	}
}