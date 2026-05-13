package fr.istic.taa.jaxrs.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class EvenementTest {

	private Evenement evenement;

	@Before
	public void setUp() {
		evenement = new Evenement();
	}

	@Test
	public void testEvenementCreation() {
		evenement.setIdEvenement(1L);
		evenement.setNom("Festival Rock 2024");
		evenement.setDate(LocalDate.of(2024, 6, 15));
		evenement.setGenre("Rock");
		evenement.setLieu("Paris");
		evenement.setCapacite(5000L);

		assertEquals(1L, evenement.getIdEvenement().longValue());
		assertEquals("Festival Rock 2024", evenement.getNom());
		assertEquals(LocalDate.of(2024, 6, 15), evenement.getDate());
		assertEquals("Rock", evenement.getGenre());
		assertEquals("Paris", evenement.getLieu());
		assertEquals(5000L, evenement.getCapacite().longValue());
	}

	@Test
	public void testEvenementPopularite() {
		evenement.setPopularite(4.5f);
		assertEquals(4.5f, evenement.getPopularite(), 0.01f);
	}

	@Test
	public void testEvenementDescription() {
		String description = "Grand festival international de musique";
		evenement.setDescription(description);
		assertEquals(description, evenement.getDescription());
	}

	@Test
	public void testEvenementArtistes() {
		evenement.setArtistes(new ArrayList<>());
		assertTrue(evenement.getArtistes().isEmpty());

		Artiste artiste = new Artiste();
		artiste.setNom("Dupont");
		artiste.setPrenom("Jean");
		evenement.getArtistes().add(artiste);

		assertEquals(1, evenement.getArtistes().size());
		assertEquals("Dupont", evenement.getArtistes().get(0).getNom());
	}

	@Test
	public void testEvenementTickets() {
		evenement.setTickets(new ArrayList<>());
		assertTrue(evenement.getTickets().isEmpty());

		Ticket ticket = new Ticket();
		ticket.setNumeroPlace("T001");
		evenement.getTickets().add(ticket);

		assertEquals(1, evenement.getTickets().size());
	}

	@Test
	public void testEvenementOrganisateur() {
		Organisateur org = new Organisateur();
		org.setNom("Organisateur XYZ");
		evenement.setOrganisateur(org);

		assertNotNull(evenement.getOrganisateur());
		assertEquals("Organisateur XYZ", evenement.getOrganisateur().getNom());
	}

/*	@Test
	public void testEvenementConstructor() {
		Evenement evt = new Evenement("Concert", LocalDate.of(2024, 7, 20));
		assertEquals("Concert", evt.getNom());
		assertEquals(LocalDate.of(2024, 7, 20), evt.getDate());
	}*/

	@Test
	public void testEvenementCapaciteValidation() {
		evenement.setCapacite(0L);
		assertEquals(0L, evenement.getCapacite().longValue());

		evenement.setCapacite(50000L);
		assertEquals(50000L, evenement.getCapacite().longValue());
	}

	@Test
	public void testEvenementDateFuture() {
		LocalDate dateFuture = LocalDate.now().plusMonths(3);
		evenement.setDate(dateFuture);
		assertTrue(evenement.getDate().isAfter(LocalDate.now()));
	}

	@Test
	public void testEvenementGenres() {
		String[] genres = { "Rock", "Pop", "Jazz", "Classique", "Électronique" };
		for (String genre : genres) {
			evenement.setGenre(genre);
			assertEquals(genre, evenement.getGenre());
		}
	}

	@Test
	public void testEvenementToString() {
		evenement.setNom("Festival");
		String str = evenement.toString();
		assertNotNull(str);
	}
}

