package fr.istic.taa.jaxrs.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class Admin extends Personne {

	private LocalDate dateNomination;
	private Boolean actif;

	public LocalDate getDateNomination() {
		return dateNomination;
	}

	public void setDateNomination(LocalDate dateNomination) {
		this.dateNomination = dateNomination;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	@Override
	public String toString() {
		return "Administrateur{" +
				"dateNomination=" + dateNomination +
				", actif=" + actif +
				", personneId=" + getIdPersonne() +
				'}';
	}

	public void gererEvenement() {
		// logique métier future
	}
}