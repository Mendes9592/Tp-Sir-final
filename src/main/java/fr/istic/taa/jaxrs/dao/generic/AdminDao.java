package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Admin;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des administrateurs
 */
public class AdminDao extends AbstractJpaDao<Long, Admin> {

	public AdminDao() {
		super();
		setClazz(Admin.class);
	}

	/**
	 * Recherche les administrateurs actifs (requête JPQL)
	 * @return Liste des administrateurs actifs
	 */
	public List<Admin> findAdminsActifs() {
		String jpql = "SELECT a FROM Admin a WHERE a.actif = true";
		TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class);
		return query.getResultList();
	}

	/**
	 * Recherche les administrateurs nommés avant une date
	 * @param date La date limite
	 * @return Liste des administrateurs
	 */
	public List<Admin> findAdminsBefore(LocalDate date) {
		String jpql = "SELECT a FROM Admin a WHERE a.dateNomination <= :date ORDER BY a.dateNomination DESC";
		TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class);
		query.setParameter("date", date);
		return query.getResultList();
	}

	/**
	 * Recherche un administrateur par email
	 * @param email L'email
	 * @return L'admin ou null
	 */
	public Admin findByEmail(String email) {
		String jpql = "SELECT a FROM Admin a WHERE a.email = :email";
		TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class);
		query.setParameter("email", email);
		List<Admin> results = query.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}

	/**
	 * Recherche avancée multicritères avec Criteria API
	 * @param nom Nom de l'admin
	 * @param actif Actif ou non
	 * @return Liste des admins correspondant aux critères
	 */
	public List<Admin> searchAdmins(String nom, Boolean actif) {
		var cb = entityManager.getCriteriaBuilder();
		var cr = cb.createQuery(Admin.class);
		var root = cr.from(Admin.class);

		cr.select(root);

		List<Predicate> predicates = new ArrayList<>();

		if (nom != null && !nom.trim().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("nom")), "%" + nom.toLowerCase() + "%"));
		}

		if (actif != null) {
			predicates.add(cb.equal(root.get("actif"), actif));
		}

		if (predicates.isEmpty()) {
			return findAll();
		}

		var query = cr.where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(query).getResultList();
	}

	/**
	 * Compte le nombre d'administrateurs actifs
	 * @return Le nombre d'admins actifs
	 */
	public Long countAdminsActifs() {
		String jpql = "SELECT COUNT(a) FROM Admin a WHERE a.actif = true";
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		return query.getSingleResult();
	}

	/**
	 * Désactive les administrateurs nommés avant une date
	 * @param date La date limite
	 * @return Le nombre d'administrateurs mis à jour
	 */
	public int desactiverAdminsBefore(LocalDate date) {
		String jpql = "UPDATE Admin a SET a.actif = false WHERE a.dateNomination < :date AND a.actif = true";
		jakarta.persistence.Query query = entityManager.createQuery(jpql);
		query.setParameter("date", date);
		return query.executeUpdate();
	}
}

