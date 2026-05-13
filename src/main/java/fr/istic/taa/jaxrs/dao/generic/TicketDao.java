package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.StatutTicketEnum;
import fr.istic.taa.jaxrs.domain.Ticket;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des tickets
 */
public class TicketDao extends AbstractJpaDao<Long, Ticket> {

	public TicketDao() {
		super();
		setClazz(Ticket.class);
	}

	/**
	 * Recherche les tickets par statut (requête nommée JPQL)
	 * @param statut Le statut du ticket
	 * @return Liste des tickets avec ce statut
	 */
	public List<Ticket> findByStatut(StatutTicketEnum statut) {
		TypedQuery<Ticket> query = entityManager.createNamedQuery("Ticket.findByStatut", Ticket.class);
		query.setParameter("statut", statut);
		return query.getResultList();
	}

	/**
	 * Recherche les tickets d'un utilisateur (requête nommée JPQL)
	 * @param utilisateurId L'ID de l'utilisateur
	 * @return Liste des tickets de cet utilisateur
	 */
	public List<Ticket> findByUtilisateur(Long utilisateurId) {
		TypedQuery<Ticket> query = entityManager.createNamedQuery("Ticket.findByUtilisateur", Ticket.class);
		query.setParameter("utilisateurId", utilisateurId);
		return query.getResultList();
	}

	/**
	 * Recherche les tickets d'un événement (requête JPQL classique)
	 * @param evenementId L'ID de l'événement
	 * @return Liste des tickets pour cet événement
	 */
	public List<Ticket> findByEvenement(Long evenementId) {
		String jpql = "SELECT t FROM Ticket t WHERE t.evenement.idEvenement = :eventId ORDER BY t.dateAchat DESC";
		TypedQuery<Ticket> query = entityManager.createQuery(jpql, Ticket.class);
		query.setParameter("eventId", evenementId);
		return query.getResultList();
	}

	/**
	 * Recherche les tickets annulés dans une plage de temps
	 * @param dateDebut Date de début
	 * @param dateFin Date de fin
	 * @return Liste des tickets annulés
	 */
	public List<Ticket> findAnnulesInPeriode(LocalDateTime dateDebut, LocalDateTime dateFin) {
		String jpql = "SELECT t FROM Ticket t WHERE t.statut = fr.istic.taa.jaxrs.domain.StatutTicketEnum.ANNULE " +
				"AND t.dateAnnulation BETWEEN :debut AND :fin";
		TypedQuery<Ticket> query = entityManager.createQuery(jpql, Ticket.class);
		query.setParameter("debut", dateDebut);
		query.setParameter("fin", dateFin);
		return query.getResultList();
	}

	/**
	 * Recherche avancée multicritères avec Criteria API
	 * @param statut Statut du ticket
	 * @param utilisateurId ID de l'utilisateur
	 * @param evenementId ID de l'événement
	 * @return Liste des tickets correspondant aux critères
	 */
	public List<Ticket> searchTickets(StatutTicketEnum statut, Long utilisateurId, Long evenementId) {
		var cb = entityManager.getCriteriaBuilder();
		var cr = cb.createQuery(Ticket.class);
		var root = cr.from(Ticket.class);

		cr.select(root);

		List<Predicate> predicates = new ArrayList<>();

		if (statut != null) {
			predicates.add(cb.equal(root.get("statut"), statut));
		}

		if (utilisateurId != null) {
			predicates.add(cb.equal(root.get("utilisateur").get("idPersonne"), utilisateurId));
		}

		if (evenementId != null) {
			predicates.add(cb.equal(root.get("evenement").get("idEvenement"), evenementId));
		}

		if (predicates.isEmpty()) {
			return findAll();
		}

		var query = cr.where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(query).getResultList();
	}

	/**
	 * Calcule le chiffre d'affaires pour un événement
	 * @param evenementId L'ID de l'événement
	 * @return Le chiffre d'affaires total
	 */
	public Double calculerChiffreAffaires(Long evenementId) {
		String jpql = "SELECT SUM(t.prixUnitaire) FROM Ticket t WHERE t.evenement.idEvenement = :eventId " +
				"AND t.statut = fr.istic.taa.jaxrs.domain.StatutTicketEnum.CONFIRME";
		TypedQuery<Double> query = entityManager.createQuery(jpql, Double.class);
		query.setParameter("eventId", evenementId);
		Double result = query.getSingleResult();
		return result != null ? result : 0.0;
	}

	/**
	 * Compte les places restantes pour un événement
	 * @param evenementId L'ID de l'événement
	 * @return Le nombre de places restantes
	 */
	public Long countPlacesRestantes(Long evenementId) {
		String jpql = "SELECT COUNT(t) FROM Ticket t WHERE t.evenement.idEvenement = :eventId " +
				"AND t.statut = fr.istic.taa.jaxrs.domain.StatutTicketEnum.CONFIRME";
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		query.setParameter("eventId", evenementId);
		return query.getSingleResult();
	}
}

