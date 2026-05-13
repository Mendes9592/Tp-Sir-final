package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Organisateur;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des organisateurs
 */
public class OrganisateurDao extends AbstractJpaDao<Long, Organisateur> {

	public OrganisateurDao() {
		super();
		setClazz(Organisateur.class);
	}

	/**
	 * Recherche un organisateur par SIRET (requête JPQL)
	 * @param siret Le numéro SIRET
	 * @return L'organisateur ou null
	 */
	public Organisateur findBySiret(String siret) {
		String jpql = "SELECT o FROM Organisateur o WHERE o.numeroSiret = :siret";
		TypedQuery<Organisateur> query = entityManager.createQuery(jpql, Organisateur.class);
		query.setParameter("siret", siret);
		List<Organisateur> results = query.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}

	/**
	 * Recherche les organisateurs par nom de structure
	 * @param nom Le nom de la structure
	 * @return Liste des organisateurs correspondants
	 */
	public List<Organisateur> findByNomStructure(String nom) {
		String jpql = "SELECT o FROM Organisateur o WHERE LOWER(o.nomStructure) LIKE LOWER(:nom)";
		TypedQuery<Organisateur> query = entityManager.createQuery(jpql, Organisateur.class);
		query.setParameter("nom", "%" + nom + "%");
		return query.getResultList();
	}

	/**
	 * Recherche les organisateurs par email
	 * @param email L'email
	 * @return L'organisateur ou null
	 */
	public Organisateur findByEmail(String email) {
		String jpql = "SELECT o FROM Organisateur o WHERE o.email = :email";
		TypedQuery<Organisateur> query = entityManager.createQuery(jpql, Organisateur.class);
		query.setParameter("email", email);
		List<Organisateur> results = query.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}

	/**
	 * Recherche avancée multicritères avec Criteria API
	 * @param nomStructure Nom de la structure
	 * @param nom Nom de l'organisateur
	 * @param email Email de l'organisateur
	 * @return Liste des organisateurs correspondant aux critères
	 */
	public List<Organisateur> searchOrganisateurs(String nomStructure, String nom, String email) {
		var cb = entityManager.getCriteriaBuilder();
		var cr = cb.createQuery(Organisateur.class);
		var root = cr.from(Organisateur.class);

		cr.select(root);

		List<Predicate> predicates = new ArrayList<>();

		if (nomStructure != null && !nomStructure.trim().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("nomStructure")), "%" + nomStructure.toLowerCase() + "%"));
		}

		if (nom != null && !nom.trim().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("nom")), "%" + nom.toLowerCase() + "%"));
		}

		if (email != null && !email.trim().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
		}

		if (predicates.isEmpty()) {
			return findAll();
		}

		var query = cr.where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(query).getResultList();
	}

	/**
	 * Récupère les organisateurs avec leurs événements
	 * @return Liste des organisateurs
	 */
	public List<Organisateur> findOrganisateursWithEvenements() {
		String jpql = "SELECT DISTINCT o FROM Organisateur o LEFT JOIN FETCH o.evenements WHERE o.id IS NOT NULL";
		TypedQuery<Organisateur> query = entityManager.createQuery(jpql, Organisateur.class);
		return query.getResultList();
	}

	/**
	 * Compte le nombre d'événements créés par un organisateur
	 * @param organisateurId L'ID de l'organisateur
	 * @return Le nombre d'événements
	 */
	public Long countEvenementsOrganisateur(Long organisateurId) {
		String jpql = "SELECT COUNT(e) FROM Evenement e WHERE e.organisateur.idPersonne = :orgId";
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		query.setParameter("orgId", organisateurId);
		return query.getSingleResult();
	}
}

