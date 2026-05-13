package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artiste")
@NamedQuery(name = "Artiste.findByStyleArtistique", query = "SELECT a FROM Artiste a WHERE a.styleArtistique = :style")
@NamedQuery(name = "Artiste.findByNationalite", query = "SELECT a FROM Artiste a WHERE a.nationalite = :nationalite")
public class Artiste implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_artiste")
	private Long idArtiste;

	@Column(name = "nom", nullable = false)
	private String nom;

	@Column(name = "prenom", nullable = false)
	private String prenom;

	@Column(name = "style_artistique")
	private String styleArtistique;

	@Column(name = "date_naissance")
	private LocalDate dateNaissance;

	@Column(name = "nationalite")
	private String nationalite;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "popularite")
	private Integer popularite;

	@Column(name = "site_web")
	private String siteWeb;

    @Column(name = "image_url")
    private String imageUrl;

    @JsonIgnore
	@ManyToMany(mappedBy = "artistes")
	private List<Evenement> evenements = new ArrayList<>();

	// Constructeurs
	public Artiste() {
	}

	public Artiste(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	public Artiste(String nom, String prenom, String styleArtistique) {
		this.nom = nom;
		this.prenom = prenom;
		this.styleArtistique = styleArtistique;
	}

	// Getters et Setters
	public Long getIdArtiste() {
		return idArtiste;
	}

	public void setIdArtiste(Long idArtiste) {
		this.idArtiste = idArtiste;
	}

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

	public String getStyleArtistique() {
		return styleArtistique;
	}

	public void setStyleArtistique(String styleArtistique) {
		this.styleArtistique = styleArtistique;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPopularite() {
		return popularite;
	}

	public void setPopularite(Integer popularite) {
		this.popularite = popularite;
	}

	public String getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public List<Evenement> getEvenements() {
		return evenements;
	}

	public void setEvenements(List<Evenement> evenements) {
		this.evenements = evenements;
	}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

	@Override
	public String toString() {
		return "Artiste{" +
				"idArtiste=" + idArtiste +
				", nom='" + nom + '\'' +
				", prenom='" + prenom + '\'' +
				", styleArtistique='" + styleArtistique + '\'' +
				", dateNaissance=" + dateNaissance +
				", nationalite='" + nationalite + '\'' +
				", description='" + description + '\'' +
				", popularite=" + popularite +
				", siteWeb='" + siteWeb + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
				'}';
	}
}