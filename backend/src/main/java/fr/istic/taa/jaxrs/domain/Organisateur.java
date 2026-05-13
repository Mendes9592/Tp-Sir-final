package fr.istic.taa.jaxrs.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Organisateur extends Personne {

	private String nomStructure;
	private String numeroSiret;
	private String adresseSiege;
	private List<Evenement> evenements = new ArrayList<>();

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

	public String getAdresseSiege() {
		return adresseSiege;
	}

	public void setAdresseSiege(String adresseSiege) {
		this.adresseSiege = adresseSiege;
	}

	@OneToMany(mappedBy = "organisateur", cascade = CascadeType.PERSIST)
	public List<Evenement> getEvenements() {
		return evenements;
	}

	public void setEvenements(List<Evenement> evenements) {
		this.evenements = evenements;
	}

	@Override
	public String toString() {
		return "Organisateur{" +
				"nomStructure='" + nomStructure + '\'' +
				", numeroSiret='" + numeroSiret + '\'' +
				", adresseSiege='" + adresseSiege + '\'' +
				", personneId=" + getIdPersonne() +
				'}';
	}

	public void orh() {
	}
}
