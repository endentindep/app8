package demo.userservice;

import org.springframework.security.core.GrantedAuthority;

import demo.models.Role;

public class UserAuthority implements GrantedAuthority {

	private Role role;

	UserAuthority(Role role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		String str = role.getName();
		if (str.equals("admin")) {
			return "ROLE_ADMIN";
		} else if (str.equals("user")) {
			return "ROLE_USER";
		} else
			return null;
	}

}
