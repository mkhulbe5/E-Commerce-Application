package com.myStartup.model;

import java.util.Collection;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ManyToMany(mappedBy = "roles")
	private Collection<User> users = new HashSet<>();

	public Roles(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
