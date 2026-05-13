package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.dao.generic.ArtisteDao;
import fr.istic.taa.jaxrs.dao.generic.EvenementDao;
import fr.istic.taa.jaxrs.dao.generic.OrganisateurDao;
import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.domain.Evenement;

import java.util.List;
import java.util.stream.Collectors;

public class EvenementService {

    private final EvenementDao evenementDao;
    private final OrganisateurDao organisateurDao;
    private final ArtisteDao artisteDao;

    public EvenementService() {
        evenementDao = new EvenementDao();
        organisateurDao = new OrganisateurDao();
        artisteDao = new ArtisteDao();
    }

    // 🔹 CREATE
    public Evenement createEvenement(Evenement evenement) {

        if (evenement == null || evenement.getNom() == null) {
            throw new IllegalArgumentException("Données invalides");
        }

        // 🔸 Recharger l'organisateur (important en JPA)
        if (evenement.getOrganisateur() != null && evenement.getOrganisateur().getIdPersonne() != null) {
            evenement.setOrganisateur(
                    organisateurDao.findOne(evenement.getOrganisateur().getIdPersonne())
            );
        }

        // 🔸 Recharger les artistes
        if (evenement.getArtistes() != null && !evenement.getArtistes().isEmpty()) {
            List<Artiste> artistes = evenement.getArtistes().stream()
                    .map(a -> artisteDao.findOne(a.getIdArtiste()))
                    .collect(Collectors.toList());

            evenement.setArtistes(artistes);
        }

        evenementDao.save(evenement);
        return evenement;
    }

    // 🔹 READ ALL
    public List<Evenement> getAllEvenements() {
        return evenementDao.findAll();
    }

    // 🔹 READ BY ID
    public Evenement getEvenementById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }
        return evenementDao.findOne(id);
    }

    // 🔹 UPDATE
    public Evenement updateEvenement(Long id, Evenement evenement) {

        if (id == null || evenement == null) {
            throw new IllegalArgumentException("Données invalides");
        }

        Evenement existing = evenementDao.findOne(id);

        if (existing == null) {
            throw new RuntimeException("Événement non trouvé");
        }

        evenement.setIdEvenement(id);

        return evenementDao.update(evenement);
    }

    // 🔹 DELETE
    public void deleteEvenement(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }

        Evenement evenement = evenementDao.findOne(id);

        if (evenement == null) {
            throw new RuntimeException("Événement non trouvé");
        }

        evenementDao.delete(evenement);
    }

    // 🔹 SEARCH
    public List<Evenement> search(String nom, String genre, String lieu) {
        return evenementDao.searchEvenements(nom, genre, lieu);
    }

    public List<Evenement> getByDate(java.time.LocalDate date) {
        return evenementDao.findByDate(date);
    }

    public List<Evenement> getPopular(Float seuil) {
        return evenementDao.findPopularEvenements(seuil);
    }

    public List<Evenement> getWithArtistes() {
        return evenementDao.findEvenementWithArtistes();
    }
}