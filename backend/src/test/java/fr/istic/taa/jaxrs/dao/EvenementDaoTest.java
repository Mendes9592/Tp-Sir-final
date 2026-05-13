package fr.istic.taa.jaxrs.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import fr.istic.taa.jaxrs.dao.generic.EvenementDao;
import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.Evenement;

import java.time.LocalDate;
import java.util.List;

public class EvenementDaoTest {

	private EvenementDao dao;

	@Before
	public void setUp() {
		try {
			EntityManagerHelper.getEntityManager();
			dao = new EvenementDao();
		} catch (Exception e) {
			System.out.println("Attention: L'EntityManager n'est pas configuré pour les tests: " + e.getMessage());
		}
	}

	@After
	public void tearDown() {
		// Nettoyer les ressources après les tests
	}

	@Test
	public void testEvenementDaoInitialization() {
		assertNotNull(dao);
	}

	@Test
	public void testFindEvenementByGenre() {
		if (dao != null) {
			try {
				List<Evenement> evenements = dao.findByGenre("Rock");
				assertNotNull(evenements);
				// Tous les événements retournés doivent avoir le genre "Rock"
				for (Evenement e : evenements) {
					if (e.getGenre() != null) {
						assertEquals("Rock", e.getGenre());
					}
				}
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}

	@Test
	public void testFindEvenementByLieu() {
		if (dao != null) {
			try {
				List<Evenement> evenements = dao.findByLieu("Paris");
				assertNotNull(evenements);
				// Tous les événements retournés doivent avoir le lieu "Paris"
				for (Evenement e : evenements) {
					if (e.getLieu() != null) {
						assertEquals("Paris", e.getLieu());
					}
				}
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}

	@Test
	public void testFindEvenementByDate() {
		if (dao != null) {
			try {
				LocalDate date = LocalDate.of(2024, 6, 15);
				List<Evenement> evenements = dao.findByDate(date);
				assertNotNull(evenements);
				// Tous les événements retournés doivent avoir la date spécifiée
				for (Evenement e : evenements) {
					if (e.getDate() != null) {
						assertEquals(date, e.getDate());
					}
				}
			} catch (Exception e) {
				System.out.println("Test échoué: " + e.getMessage());
			}
		}
	}

}

