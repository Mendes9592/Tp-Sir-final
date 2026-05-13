package fr.istic.taa.jaxrs.service;

import fr.istic.taa.jaxrs.dao.generic.ArtisteDao;
import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.dto.ArtisteDto;

import java.util.List;

/**
 * Service pour la gestion des artistes
 * Contient la logique métier liée aux artistes
 */
public class ArtisteService {

    private final ArtisteDao dao = new ArtisteDao();

    /**
     * Récupère tous les artistes
     * @return Liste des artistes
     */
    public List<Artiste> getAllArtistes() {
        return dao.findAll();
    }

    /**
     * Récupère un artiste par ID
     * @param id Identifiant de l'artiste
     * @return L'artiste ou null
     */
    public Artiste getArtisteById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }
        return dao.findOne(id);
    }

    /**
     * Crée un nouvel artiste
     * @param artiste Données de l'artiste
     * @return Artiste créé
     */
    public Artiste createArtiste(Artiste artiste) {
        if (artiste == null || artiste.getNom() == null || artiste.getPrenom() == null) {
            throw new IllegalArgumentException("Données invalides");
        }
        dao.save(artiste);
        return artiste;
    }

    /**
     * Met à jour un artiste existant
     * @param id ID de l'artiste
     * @param artiste Nouvelles données
     * @return Artiste mis à jour ou erreur si inexistant
     */
    public Artiste updateArtiste(Long id, Artiste artiste) {
        if (id == null || id <= 0 || artiste == null) {
            throw new IllegalArgumentException("Données invalides");
        }

        Artiste existing = dao.findOne(id);
        if (existing == null) {
            throw new RuntimeException("Artiste introuvable");
        }

        artiste.setIdArtiste(id);
        dao.update(artiste);
        return artiste;
    }

    /**
     * Supprime un artiste
     * @param id ID de l'artiste
     */
    public void deleteArtiste(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }

        Artiste artiste = dao.findOne(id);
        if (artiste == null) {
            throw new RuntimeException("Artiste introuvable");
        }

        dao.delete(artiste);
    }

    /**
     * Recherche avancée d'artistes avec DTO
     * @param searchDTO Critères de recherche
     * @return Liste des artistes correspondants
     */
    public List<Artiste> searchArtistes(ArtisteDto searchDTO) {
        return dao.searchArtistes(searchDTO);
    }

    /**
     * Recherche des artistes par style artistique
     * @param style Style artistique
     * @return Liste des artistes correspondants
     */
    public List<Artiste> getArtistesByStyle(String style) {
        if (style == null || style.trim().isEmpty()) {
            throw new IllegalArgumentException("Style invalide");
        }

        ArtisteDto dto = new ArtisteDto();
        dto.setStyleArtistique(style); // ✔ corrigé

        return dao.searchArtistes(dto);
    }

    /**
     * Recherche des artistes par nationalité
     * @param nationalite Nationalité
     * @return Liste des artistes correspondants
     */
    public List<Artiste> getArtistesByNationalite(String nationalite) {
        if (nationalite == null || nationalite.trim().isEmpty()) {
            throw new IllegalArgumentException("Nationalité invalide");
        }

        ArtisteDto dto = new ArtisteDto();
        dto.setNationalite(nationalite);

        return dao.searchArtistes(dto);
    }
}