package demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(generator = "sequence-generator")
	private int id;

	@Column
	private String name;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String toString() {
		return name;
	}
}
