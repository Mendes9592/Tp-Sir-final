package fr.istic.taa.jaxrs.dto;

import jakarta.ws.rs.core.MultivaluedMap;

/**
 * DTO pour la recherche et sérialisation des tickets
 */
public class TicketDto {
	private String statut;
	private Long utilisateurId;
	private Long evenementId;
	private Double prixMin;
	private Double prixMax;

	// Constructeur vide
	public TicketDto() {
	}

	// Constructeur avec MultivaluedMap (depuis QueryParameters)
	public TicketDto(MultivaluedMap<String, String> queryParameters) {
		if (queryParameters != null) {
			this.statut = queryParameters.getFirst("statut");
			
			try {
				String userStr = queryParameters.getFirst("utilisateurId");
				if (userStr != null && !userStr.isEmpty()) {
					this.utilisateurId = Long.parseLong(userStr);
				}
			} catch (NumberFormatException e) {
				this.utilisateurId = null;
			}

			try {
				String eventStr = queryParameters.getFirst("evenementId");
				if (eventStr != null && !eventStr.isEmpty()) {
					this.evenementId = Long.parseLong(eventStr);
				}
			} catch (NumberFormatException e) {
				this.evenementId = null;
			}

			try {
				String prixMinStr = queryParameters.getFirst("prixMin");
				if (prixMinStr != null && !prixMinStr.isEmpty()) {
					this.prixMin = Double.parseDouble(prixMinStr);
				}
			} catch (NumberFormatException e) {
				this.prixMin = null;
			}

			try {
				String prixMaxStr = queryParameters.getFirst("prixMax");
				if (prixMaxStr != null && !prixMaxStr.isEmpty()) {
					this.prixMax = Double.parseDouble(prixMaxStr);
				}
			} catch (NumberFormatException e) {
				this.prixMax = null;
			}
		}
	}

	// Getters et Setters
	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Long getUtilisateurId() {
		return utilisateurId;
	}

	public void setUtilisateurId(Long utilisateurId) {
		this.utilisateurId = utilisateurId;
	}

	public Long getEvenementId() {
		return evenementId;
	}

	public void setEvenementId(Long evenementId) {
		this.evenementId = evenementId;
	}

	public Double getPrixMin() {
		return prixMin;
	}

	public void setPrixMin(Double prixMin) {
		this.prixMin = prixMin;
	}

	public Double getPrixMax() {
		return prixMax;
	}

	public void setPrixMax(Double prixMax) {
		this.prixMax = prixMax;
	}
}

