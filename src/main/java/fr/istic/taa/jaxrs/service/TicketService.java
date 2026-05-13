package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.dao.generic.TicketDao;
import fr.istic.taa.jaxrs.dao.generic.EvenementDao;
import fr.istic.taa.jaxrs.dao.generic.UtilisateurDao;
import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.domain.StatutTicketEnum;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.Utilisateur;

import java.util.List;

public class TicketService {

    private final TicketDao ticketDao = new TicketDao();
    private final EvenementDao evenementDao = new EvenementDao();
    private final UtilisateurDao utilisateurDao = new UtilisateurDao();

    public Ticket createTicket(Ticket ticket) {
        if (ticket == null || ticket.getEvenement() == null || ticket.getUtilisateur() == null) {
            throw new IllegalArgumentException("Données invalides");
        }

        Long evenementId = ticket.getEvenement().getIdEvenement();
        Long utilisateurId = ticket.getUtilisateur().getIdPersonne();

        if (evenementId == null || utilisateurId == null) {
            throw new IllegalArgumentException("ID événement ou utilisateur manquant");
        }

        Evenement evenement = evenementDao.findOne(evenementId);
        Utilisateur utilisateur = utilisateurDao.findOne(utilisateurId);

        if (evenement == null) {
            throw new IllegalArgumentException("Événement introuvable");
        }

        if (utilisateur == null) {
            throw new IllegalArgumentException("Utilisateur introuvable");
        }

        ticket.setEvenement(evenement);
        ticket.setUtilisateur(utilisateur);

        if (ticket.getStatut() == null) {
            ticket.setStatut(StatutTicketEnum.ACHETE);
        }

        if (ticket.getPrixUnitaire() == null && evenement.getPrix() != null) {
            ticket.setPrixUnitaire(evenement.getPrix());
        }

        ticketDao.save(ticket);
        return ticket;
    }

    public List<Ticket> getAllTickets() {
        return ticketDao.findAll();
    }

    public Ticket getTicketById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }
        return ticketDao.findOne(id);
    }

    public Ticket updateTicket(Long id, Ticket ticket) {
        if (id == null || ticket == null) {
            throw new IllegalArgumentException("Données invalides");
        }

        Ticket existing = ticketDao.findOne(id);

        if (existing == null) {
            throw new IllegalArgumentException("Ticket non trouvé");
        }

        ticket.setIdTicket(id);
        return ticketDao.update(ticket);
    }

    public void deleteTicket(Long id) {
        Ticket ticket = getTicketById(id);
        ticketDao.delete(ticket);
    }

    public Ticket findOne(Long id) {
        return ticketDao.findOne(id);
    }

    public void delete(Ticket ticket) {
        ticketDao.delete(ticket);
    }

    public List<Ticket> getTicketsByStatut(StatutTicketEnum statut) {
        return ticketDao.findByStatut(statut);
    }

    public List<Ticket> getTicketsByUtilisateur(Long utilisateurId) {
        return ticketDao.findByUtilisateur(utilisateurId);
    }

    public List<Ticket> getTicketsByEvenement(Long evenementId) {
        return ticketDao.findByEvenement(evenementId);
    }

    public Double getChiffreAffaires(Long evenementId) {
        return ticketDao.calculerChiffreAffaires(evenementId);
    }

    public Long getPlacesRestantes(Long evenementId) {
        return ticketDao.countPlacesRestantes(evenementId);
    }
}