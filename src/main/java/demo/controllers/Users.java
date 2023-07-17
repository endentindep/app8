package demo.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		return "index";
	}

	@GetMapping("/getUserData")
	@ResponseBody
	public String indexGetUserData(Principal principal) {
		UserPrincipal up = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return up.toString();
	}

	@GetMapping("/user")
	public String user(ModelMap model, Principal principal) {
		return "user";
	}

	@GetMapping("/admin")
	public String admin(ModelMap model) {
		return "admin";
	}

	@GetMapping("/getUsersData")
	@ResponseBody
	public Map<String, String> indexGetUsersData() {
		List<? extends User> list = dao.getUsers();
		HashMap<String, String> json = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			json.put("" + i, list.get(i).toString());
		}
		return json;
	}

	@PostMapping(value = "/admin")
	@ResponseBody
	public Map<String, String> adminPost(@RequestBody Map<String, String> data) {
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
			dao.updateUser(Long.parseLong(data.get("id")), data.get("name"),
					data.get("password"), set);
		} else {
			dao.removeUser(Long.parseLong(data.get("id")));
		}
		List<? extends User> list = dao.getUsers();
		HashMap<String, String> json = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			json.put("" + i, list.get(i).toString());
		}
		return json;
	}
}