package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evenement")
@NamedQuery(name = "Evenement.findByDate", query = "SELECT e FROM Evenement e WHERE e.date = :date")
@NamedQuery(name = "Evenement.findByGenre", query = "SELECT e FROM Evenement e WHERE e.genre = :genre")
@NamedQuery(name = "Evenement.findByLieu", query = "SELECT e FROM Evenement e WHERE e.lieu = :lieu")
public class Evenement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvenement;

    private String nom;
    private LocalDate date;
    private String genre;
    private String lieu;
    private Long capacite;
    private Float popularite;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "heure")
    private String heure;

    @Column(name = "nb_tickets_vendus")
    private Long nbTicketsVendus;

    @Column(name = "categorie")
    private String categorie;

    @ManyToOne
    @JoinColumn(name = "organisateur_id")
    private Organisateur organisateur;

    @ManyToMany
    @JoinTable(
            name = "evenement_artiste",
            joinColumns = @JoinColumn(name = "evenement_id"),
            inverseJoinColumns = @JoinColumn(name = "artiste_id")
    )
    private List<Artiste> artistes = new ArrayList<>();

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.PERSIST)
    private List<Ticket> tickets = new ArrayList<>();

    // getters & setters SANS annotations

    public Long getIdEvenement() { return idEvenement; }
    public void setIdEvenement(Long id) { this.idEvenement = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Long getCapacite() { return capacite; }
    public void setCapacite(Long capacite) { this.capacite = capacite; }

    public Float getPopularite() { return popularite; }
    public void setPopularite(Float popularite) { this.popularite = popularite; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Organisateur getOrganisateur() { return organisateur; }
    public void setOrganisateur(Organisateur organisateur) { this.organisateur = organisateur; }

    public List<Artiste> getArtistes() { return artistes; }
    public void setArtistes(List<Artiste> artistes) { this.artistes = artistes; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public Long getNbTicketsVendus() {
        return nbTicketsVendus;
    }

    public void setNbTicketsVendus(Long nbTicketsVendus) {
        this.nbTicketsVendus = nbTicketsVendus;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}