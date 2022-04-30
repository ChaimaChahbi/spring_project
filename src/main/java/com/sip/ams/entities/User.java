package com.sip.ams.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nom_user", length = 50)
	@NotBlank(message = "Label is mandatory")
	private String nom;

	@Column(name = "user_prenom", length = 50)
	@NotBlank(message = "Label is mandatory")
	private String prenom;

	@Column(unique = true)
	@Email
	@NotBlank(message = "Label is mandatory")
	private String email;

	@Column(name = "login", unique = true)
	@NotBlank(message = "Label is mandatory")
	private String login;

	@Column(name = "password")
	@Size(min = 6)
	@NotBlank(message = "Label is mandatory")
	private String password;
	
	@Transient
    private String passwordConfirm;

	@Enumerated(EnumType.STRING)
	private Role role;

}
