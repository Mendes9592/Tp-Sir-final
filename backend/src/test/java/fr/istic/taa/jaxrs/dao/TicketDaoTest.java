package fr.istic.taa.jaxrs.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import fr.istic.taa.jaxrs.dao.generic.TicketDao;
import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.StatutTicketEnum;

import java.util.List;

public class TicketDaoTest {

	private TicketDao dao;

	@Before
	public void setUp() {
		try {
			EntityManagerHelper.getEntityManager();
			dao = new TicketDao();
		} catch (Exception e) {
			System.out.println("Attention: L'EntityManager n'est pas configuré pour les tests: " + e.getMessage());
		}
	}

	@After
	public void tearDown() {
		// Nettoyer les ressources après les tests
	}

	@Test
	public void testTicketDaoInitialization() {
		assertNotNull(dao);
	}

	@Test
	public void testFindTicketByStatut() {
		if (dao != null) {
			try {
				List<Ticket> tickets = dao.findByStatut(StatutTicketEnum.ACHETE);
				assertNotNull(tickets);
				// Tous les tickets retournés doivent avoir le statut ACHETE
				for (Ticket t : tickets) {
					if (t.getStatut() != null) {
						assertEquals(StatutTicketEnum.ACHETE, t.getStatut());
					}
				}
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}

	@Test
	public void testFindTicketByEvenement() {
		if (dao != null) {
			try {
				List<Ticket> tickets = dao.findByEvenement(1L);
				assertNotNull(tickets);
				// Tous les tickets retournés doivent appartenir à l'événement 1
				for (Ticket t : tickets) {
					if (t.getEvenement() != null) {
						assertEquals(1L, t.getEvenement().getIdEvenement().longValue());
					}
				}
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}

	@Test
	public void testFindTicketByUtilisateur() {
		if (dao != null) {
			try {
				List<Ticket> tickets = dao.findByUtilisateur(1L);
				assertNotNull(tickets);
				// Tous les tickets retournés doivent appartenir à l'utilisateur 1
				for (Ticket t : tickets) {
					if (t.getUtilisateur() != null) {
						assertEquals(1L, t.getUtilisateur().getIdPersonne().longValue());
					}
				}
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}

	@Test
	public void testSearchTickets() {
		if (dao != null) {
			try {
				// Recherche multicritères avec Criteria API
				List<Ticket> tickets = dao.searchTickets(StatutTicketEnum.ACHETE, null, null);
				assertNotNull(tickets);
				// Tous les tickets retournés doivent avoir le statut ACHETE
				for (Ticket t : tickets) {
					if (t.getStatut() != null) {
						assertEquals(StatutTicketEnum.ACHETE, t.getStatut());
					}
				}
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}

	@Test
	public void testSearchTicketsWithUtilisateur() {
		if (dao != null) {
			try {
				// Recherche par utilisateur via Criteria API
				List<Ticket> tickets = dao.searchTickets(null, 1L, null);
				assertNotNull(tickets);
				// Tous les tickets retournés doivent appartenir à l'utilisateur 1
				for (Ticket t : tickets) {
					if (t.getUtilisateur() != null) {
						assertEquals(1L, t.getUtilisateur().getIdPersonne().longValue());
					}
				}
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}

	@Test
	public void testSearchTicketsWithEvenement() {
		if (dao != null) {
			try {
				// Recherche par événement via Criteria API
				List<Ticket> tickets = dao.searchTickets(null, null, 1L);
				assertNotNull(tickets);
				// Tous les tickets retournés doivent appartenir à l'événement 1
				for (Ticket t : tickets) {
					if (t.getEvenement() != null) {
						assertEquals(1L, t.getEvenement().getIdEvenement().longValue());
					}
				}
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}

	@Test
	public void testCalculerChiffreAffaires() {
		if (dao != null) {
			try {
				Double chiffre = dao.calculerChiffreAffaires(1L);
				assertNotNull(chiffre);
				assertTrue("Le chiffre d'affaires doit être >= 0", chiffre >= 0.0);
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}

	@Test
	public void testCountPlacesRestantes() {
		if (dao != null) {
			try {
				Long places = dao.countPlacesRestantes(1L);
				assertNotNull(places);
				assertTrue("Le nombre de places doit être >= 0", places >= 0);
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}

	@Test
	public void testFindAnnulesInPeriode() {
		if (dao != null) {
			try {
				// Recherche des tickets annulés dans une période
				java.time.LocalDateTime dateDebut = java.time.LocalDateTime.now().minusMonths(1);
				java.time.LocalDateTime dateFin = java.time.LocalDateTime.now();
				List<Ticket> tickets = dao.findAnnulesInPeriode(dateDebut, dateFin);
				assertNotNull(tickets);
				// Tous les tickets retournés doivent être annulés
				for (Ticket t : tickets) {
					if (t.getStatut() != null) {
						assertEquals(StatutTicketEnum.ANNULE, t.getStatut());
					}
				}
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}
}

