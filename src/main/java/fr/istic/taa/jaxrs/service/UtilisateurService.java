package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.dao.generic.UtilisateurDao;
import fr.istic.taa.jaxrs.domain.Utilisateur;

import java.util.List;

/**
 * Service pour la gestion des utilisateurs
 * Contient la logique métier liée aux utilisateurs
 */
public class UtilisateurService {

    private final UtilisateurDao dao = new UtilisateurDao();

    /**
     * Récupère tous les utilisateurs
     */
    public List<Utilisateur> getAllUtilisateurs() {
        return dao.findAll();
    }

    /**
     * Récupère un utilisateur par ID
     */
    public Utilisateur getUtilisateurById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }
        return dao.findOne(id);
    }

    /**
     * Crée un utilisateur
     */
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null || utilisateur.getNom() == null || utilisateur.getEmail() == null) {
            throw new IllegalArgumentException("Données invalides");
        }
        dao.save(utilisateur);
        return utilisateur;
    }

    /**
     * Met à jour un utilisateur
     */
    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateur) {
        if (id == null || id <= 0 || utilisateur == null) {
            throw new IllegalArgumentException("Données invalides");
        }

        Utilisateur existing = dao.findOne(id);
        if (existing == null) {
            return null;
        }

        utilisateur.setIdPersonne(id);
        dao.update(utilisateur);
        return utilisateur;
    }

    /**
     * Supprime un utilisateur
     */
    public boolean deleteUtilisateur(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }

        Utilisateur utilisateur = dao.findOne(id);
        if (utilisateur == null) {
            return false;
        }

        dao.delete(utilisateur);
        return true;
    }

    /**
     * Recherche avancée d'utilisateurs
     */
    public List<Utilisateur> searchUtilisateurs(String nom, String prenom, String email) {
        return dao.searchUtilisateurs(nom, prenom, email);
    }

    /**
     * Recherche par email
     */
    public Utilisateur getUtilisateurByEmail(String email) {
        return dao.findByEmail(email);
    }

    /**
     * Compte les tickets d’un utilisateur
     */
    public Long countTicketsUtilisateur(Long utilisateurId) {
        return dao.countTicketsUtilisateur(utilisateurId);
    }
}