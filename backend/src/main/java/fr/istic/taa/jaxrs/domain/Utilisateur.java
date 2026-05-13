package fr.istic.taa.jaxrs.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Utilisateur extends Personne {

    private LocalDate dateInscription;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.PERSIST)
    private List<Ticket> tickets = new ArrayList<>();

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String email) {
        super(nom, prenom, email);
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void listTicket() {
    }

    public void acheterTicket() {
    }

    public void annulerTicket() {
    }
}