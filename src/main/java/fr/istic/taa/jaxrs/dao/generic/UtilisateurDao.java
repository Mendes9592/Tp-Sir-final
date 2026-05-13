package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Utilisateur;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des utilisateurs
 */
public class UtilisateurDao extends AbstractJpaDao<Long, Utilisateur> {

	public UtilisateurDao() {
		super();
		setClazz(Utilisateur.class);
	}

	/**
	 * Recherche un utilisateur par email (requête JPQL)
	 * @param email L'email de l'utilisateur
	 * @return L'utilisateur ou null
	 */
	public Utilisateur findByEmail(String email) {
		String jpql = "SELECT u FROM Utilisateur u WHERE u.email = :email";
		TypedQuery<Utilisateur> query = entityManager.createQuery(jpql, Utilisateur.class);
		query.setParameter("email", email);
		List<Utilisateur> results = query.getResultList();
		return results.isEmpty() ? null : results.get(0);
	}

	/**
	 * Recherche les utilisateurs par nom
	 * @param nom Le nom à rechercher
	 * @return Liste des utilisateurs correspondants
	 */
	public List<Utilisateur> findByNom(String nom) {
		String jpql = "SELECT u FROM Utilisateur u WHERE LOWER(u.nom) LIKE LOWER(:nom)";
		TypedQuery<Utilisateur> query = entityManager.createQuery(jpql, Utilisateur.class);
		query.setParameter("nom", "%" + nom + "%");
		return query.getResultList();
	}

	/**
	 * Recherche avancée multicritères avec Criteria API
	 * @param nom Nom de l'utilisateur
	 * @param prenom Prénom de l'utilisateur
	 * @param email Email de l'utilisateur
	 * @return Liste des utilisateurs correspondant aux critères
	 */
	public List<Utilisateur> searchUtilisateurs(String nom, String prenom, String email) {
		var cb = entityManager.getCriteriaBuilder();
		var cr = cb.createQuery(Utilisateur.class);
		var root = cr.from(Utilisateur.class);

		cr.select(root);

		List<Predicate> predicates = new ArrayList<>();

		if (nom != null && !nom.trim().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("nom")), "%" + nom.toLowerCase() + "%"));
		}

		if (prenom != null && !prenom.trim().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("prenom")), "%" + prenom.toLowerCase() + "%"));
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
	 * Récupère les utilisateurs actifs (avec au moins un ticket)
	 * @return Liste des utilisateurs actifs
	 */
	public List<Utilisateur> findUtilisateursActifs() {
		String jpql = "SELECT DISTINCT u FROM Utilisateur u LEFT JOIN u.tickets t WHERE t IS NOT NULL";
		TypedQuery<Utilisateur> query = entityManager.createQuery(jpql, Utilisateur.class);
		return query.getResultList();
	}

	/**
	 * Compte le nombre de tickets achetés par un utilisateur
	 * @param utilisateurId L'ID de l'utilisateur
	 * @return Le nombre de tickets
	 */
	public Long countTicketsUtilisateur(Long utilisateurId) {
		String jpql = "SELECT COUNT(t) FROM Ticket t WHERE t.utilisateur.idPersonne = :userId";
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		query.setParameter("userId", utilisateurId);
		return query.getSingleResult();
	}

}

