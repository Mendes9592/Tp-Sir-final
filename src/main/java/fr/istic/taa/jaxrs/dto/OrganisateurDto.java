package fr.istic.taa.jaxrs.dto;

import jakarta.ws.rs.core.MultivaluedMap;

/**
 * DTO pour la recherche et sérialisation des organisateurs
 */
public class OrganisateurDto {
	private String nom;
	private String prenom;
	private String email;
	private String nomStructure;
	private String numeroSiret;

	// Constructeur vide
	public OrganisateurDto() {
	}

	// Constructeur avec MultivaluedMap (depuis QueryParameters)
	public OrganisateurDto(MultivaluedMap<String, String> queryParameters) {
		if (queryParameters != null) {
			this.nom = queryParameters.getFirst("nom");
			this.prenom = queryParameters.getFirst("prenom");
			this.email = queryParameters.getFirst("email");
			this.nomStructure = queryParameters.getFirst("nomStructure");
			this.numeroSiret = queryParameters.getFirst("numeroSiret");
		}
	}

	// Getters et Setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomStructure() {
		return nomStructure;
	}

	public void setNomStructure(String nomStructure) {
		this.nomStructure = nomStructure;
	}

	public String getNumeroSiret() {
		return numeroSiret;
	}

	public void setNumeroSiret(String numeroSiret) {
		this.numeroSiret = numeroSiret;
	}
}

