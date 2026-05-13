package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.dao.generic.OrganisateurDao;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.dto.OrganisateurDto;

import java.util.List;

/**
 * Service métier pour la gestion des organisateurs
 */
public class OrganisateurService {

    private final OrganisateurDao dao = new OrganisateurDao();

    /**
     * Récupère tous les organisateurs
     */
    public List<Organisateur> findAll() {
        return dao.findAll();
    }

    /**
     * Récupère un organisateur par ID
     */
    public Organisateur findById(Long id) {
        return dao.findOne(id);
    }

    /**
     * Crée un organisateur
     */
    public void create(Organisateur organisateur) {
        dao.save(organisateur);
    }

    /**
     * Met à jour un organisateur
     */
    public void update(Organisateur organisateur) {
        dao.update(organisateur);
    }

    /**
     * Supprime un organisateur
     */
    public void delete(Long id) {
        Organisateur o = dao.findOne(id);
        if (o != null) {
            dao.delete(o);
        }
    }

    /**
     * Recherche avancée d'organisateurs
     */
    public List<Organisateur> search(OrganisateurDto dto) {
        return dao.searchOrganisateurs(
                dto.getNomStructure(),
                dto.getNom(),
                dto.getEmail()
        );
    }

    /**
     * Recherche par SIRET
     */
    public Organisateur findBySiret(String siret) {
        return dao.findBySiret(siret);
    }

    /**
     * Compte les événements d'un organisateur
     */
    public Long countEvenements(Long organisateurId) {
        return dao.countEvenementsOrganisateur(organisateurId);
    }
}