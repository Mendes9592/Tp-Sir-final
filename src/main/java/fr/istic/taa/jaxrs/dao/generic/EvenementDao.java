package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Evenement;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des événements
 */
public class EvenementDao extends AbstractJpaDao<Long, Evenement> {

	public EvenementDao() {
		super();
		setClazz(Evenement.class);
	}

	/**
	 * Recherche les événements par date (requête nommée JPQL)
	 * @param date La date de l'événement
	 * @return Liste des événements à cette date
	 */
	public List<Evenement> findByDate(LocalDate date) {
		TypedQuery<Evenement> query = entityManager.createNamedQuery("Evenement.findByDate", Evenement.class);
		query.setParameter("date", date);
		return query.getResultList();
	}

	/**
	 * Recherche les événements par genre (requête JPQL classique)
	 * @param genre Le genre de l'événement
	 * @return Liste des événements du genre spécifié
	 */
	public List<Evenement> findByGenre(String genre) {
		TypedQuery<Evenement> query = entityManager.createNamedQuery("Evenement.findByGenre", Evenement.class);
		query.setParameter("genre", genre);
		return query.getResultList();
	}

	/**
	 * Recherche les événements par lieu (requête JPQL)
	 * @param lieu Le lieu de l'événement
	 * @return Liste des événements au lieu spécifié
	 */
	public List<Evenement> findByLieu(String lieu) {
		TypedQuery<Evenement> query = entityManager.createNamedQuery("Evenement.findByLieu", Evenement.class);
		query.setParameter("lieu", lieu);
		return query.getResultList();
	}

	/**
	 * Recherche avancée multicritères avec Criteria API
	 * @param nom Nom de l'événement (partiel)
	 * @param genre Genre de l'événement
	 * @param lieu Lieu de l'événement
	 * @return Liste des événements correspondant aux critères
	 */
	public List<Evenement> searchEvenements(String nom, String genre, String lieu) {
		var cb = entityManager.getCriteriaBuilder();
		var cr = cb.createQuery(Evenement.class);
		var root = cr.from(Evenement.class);

		cr.select(root);

		List<Predicate> predicates = new ArrayList<>();

		if (nom != null && !nom.trim().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("nom")), "%" + nom.toLowerCase() + "%"));
		}

		if (genre != null && !genre.trim().isEmpty()) {
			predicates.add(cb.equal(root.get("genre"), genre));
		}

		if (lieu != null && !lieu.trim().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("lieu")), "%" + lieu.toLowerCase() + "%"));
		}

		if (predicates.isEmpty()) {
			return findAll();
		}

		var query = cr.where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(query).getResultList();
	}

	/**
	 * Récupère les événements populaires (popularité >= seuil)
	 * @param seuilPopularite Seuil de popularité minimum
	 * @return Liste des événements populaires
	 */
	public List<Evenement> findPopularEvenements(Float seuilPopularite) {
		String jpql = "SELECT e FROM Evenement e WHERE e.popularite >= :seuil ORDER BY e.popularite DESC";
		TypedQuery<Evenement> query = entityManager.createQuery(jpql, Evenement.class);
		query.setParameter("seuil", seuilPopularite);
		return query.getResultList();
	}

	/**
	 * Récupère les événements avec leurs artistes
	 * @return Liste des événements
	 */
	public List<Evenement> findEvenementWithArtistes() {
		String jpql = "SELECT DISTINCT e FROM Evenement e LEFT JOIN FETCH e.artistes WHERE e.id IS NOT NULL";
		TypedQuery<Evenement> query = entityManager.createQuery(jpql, Evenement.class);
		return query.getResultList();
	}
}

