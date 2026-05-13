package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.dto.ArtisteDto;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ArtisteDao extends AbstractJpaDao<Long, Artiste> {

	public ArtisteDao() {
		super();
		setClazz(Artiste.class);
	}

	/**
	 * Recherche multicritères sur les artistes
	 * 
	 * @param searchDTO Critères de recherche
	 * @return Liste des artistes correspondant aux critères
	 */
	public List<Artiste> searchArtistes(ArtisteDto searchDTO) {
		if (searchDTO == null) {
			return findAll();
		}

		var cb = entityManager.getCriteriaBuilder();
		var cr = cb.createQuery(Artiste.class);
		var root = cr.from(Artiste.class);

		cr.select(root);

		List<Predicate> predicates = new ArrayList<>();

		// Recherche par nom (partielle)
		if (searchDTO.getNom() != null && !searchDTO.getNom().trim().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("nom")), "%" + searchDTO.getNom().toLowerCase() + "%"));
		}

		// Recherche par prénom (partielle)
		if (searchDTO.getPrenom() != null && !searchDTO.getPrenom().trim().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("prenom")), "%" + searchDTO.getPrenom().toLowerCase() + "%"));
		}

		// Recherche par nom de scène (partielle) - NOTE : nomScene n'existe pas dans Artiste
		// Cette recherche est désactivée car le champ n'existe pas
		// Si vous avez besoin d'une recherche similaire, utilisez styleArtistique
		if (searchDTO.getStyleArtistique() != null && !searchDTO.getStyleArtistique().trim().isEmpty()) {
			// Recherche par styleArtistique comme alternative
			predicates.add(cb.like(cb.lower(root.get("styleArtistique")), 
					"%" + searchDTO.getStyleArtistique().toLowerCase() + "%"));
		}

		// Recherche par nationalité (exacte)
		if (searchDTO.getNationalite() != null && !searchDTO.getNationalite().trim().isEmpty()) {
			predicates.add(cb.equal(cb.lower(root.get("nationalite")), 
					searchDTO.getNationalite().toLowerCase()));
		}

		// Recherche par popularité minimale
		if (searchDTO.getPopularite() != null && searchDTO.getPopularite() > 0) {
			predicates.add(cb.greaterThanOrEqualTo(root.get("popularite"), searchDTO.getPopularite()));
		}

		// Application des prédicats
		if (predicates.isEmpty()) {
			return findAll();
		}

		var query = cr.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(query).getResultList();
	}
}