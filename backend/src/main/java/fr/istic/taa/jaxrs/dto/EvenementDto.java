package fr.istic.taa.jaxrs.dto;

import jakarta.ws.rs.core.MultivaluedMap;

/**
 * DTO pour la recherche et sérialisation des événements
 */
public class EvenementDto {
	private String nom;
	private String genre;
	private String lieu;
	private Float populariteMin;

	// Constructeur vide
	public EvenementDto() {
	}

	// Constructeur avec MultivaluedMap (depuis QueryParameters)
	public EvenementDto(MultivaluedMap<String, String> queryParameters) {
		if (queryParameters != null) {
			this.nom = queryParameters.getFirst("nom");
			this.genre = queryParameters.getFirst("genre");
			this.lieu = queryParameters.getFirst("lieu");

			try {
				String popStr = queryParameters.getFirst("populariteMin");
				if (popStr != null && !popStr.isEmpty()) {
					this.populariteMin = Float.parseFloat(popStr);
				}
			} catch (NumberFormatException e) {
				this.populariteMin = null;
			}
		}
	}

	// Getters et Setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public Float getPopulariteMin() {
		return populariteMin;
	}

	public void setPopulariteMin(Float populariteMin) {
		this.populariteMin = populariteMin;
	}
}

