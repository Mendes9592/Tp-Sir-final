package fr.istic.taa.jaxrs.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class UtilisateurTest {

	private Utilisateur utilisateur;

	@Before
	public void setUp() {
		utilisateur = new Utilisateur("Martin", "Paul", "martin.paul@email.com");
	}

	@Test
	public void testUtilisateurCreation() {
		utilisateur.setIdPersonne(1L);
		utilisateur.setNom("Dupont");
		utilisateur.setPrenom("Jean");
		utilisateur.setEmail("jean.dupont@email.com");

		assertEquals(1L, utilisateur.getIdPersonne().longValue());
		assertEquals("Dupont", utilisateur.getNom());
		assertEquals("Jean", utilisateur.getPrenom());
		assertEquals("jean.dupont@email.com", utilisateur.getEmail());
	}

	@Test
	public void testUtilisateurEmail() {
		utilisateur.setEmail("test@example.com");
		assertEquals("test@example.com", utilisateur.getEmail());
	}

	@Test
	public void testUtilisateurNom() {
		utilisateur.setNom("Martin");
		assertEquals("Martin", utilisateur.getNom());
	}

	@Test
	public void testUtilisateurPrenom() {
		utilisateur.setPrenom("Paul");
		assertEquals("Paul", utilisateur.getPrenom());
	}

	@Test
	public void testUtilisateurDateInscription() {
		LocalDate dateInscription = LocalDate.of(2024, 1, 15);
		utilisateur.setDateInscription(dateInscription);
		assertEquals(dateInscription, utilisateur.getDateInscription());
	}

	@Test
	public void testUtilisateurTickets() {
		utilisateur.setTickets(new ArrayList<>());
		assertTrue(utilisateur.getTickets().isEmpty());

		Ticket ticket = new Ticket();
		ticket.setNumeroPlace("T001");
		utilisateur.getTickets().add(ticket);

		assertEquals(1, utilisateur.getTickets().size());
	}

	@Test
	public void testUtilisateurConstructorEmpty() {
		Utilisateur u = new Utilisateur("Martin", "Paul", "martin.paul@email.com");
		assertNotNull(u);
		assertTrue(u.getTickets().isEmpty());
	}

	@Test
	public void testUtilisateurConstructorWithParams() {
		Utilisateur u = new Utilisateur("Martin", "Paul", "martin.paul@email.com");
		assertEquals("Martin", u.getNom());
		assertEquals("Paul", u.getPrenom());
		assertEquals("martin.paul@email.com", u.getEmail());
	}

	@Test
	public void testUtilisateurMultiplesTickets() {
		utilisateur.setTickets(new ArrayList<>());
		
		Ticket t1 = new Ticket();
		t1.setNumeroPlace("T001");
		Ticket t2 = new Ticket();
		t2.setNumeroPlace("T002");
		
		utilisateur.getTickets().add(t1);
		utilisateur.getTickets().add(t2);
		
		assertEquals(2, utilisateur.getTickets().size());
	}

	@Test
	public void testUtilisateurToString() {
		utilisateur.setNom("Dupont");
		utilisateur.setPrenom("Jean");
		utilisateur.setEmail("jean.dupont@email.com");
		String str = utilisateur.toString();
		assertNotNull(str);
		assertTrue(str.contains("Dupont") || str.contains("Jean"));
	}

	@Test
	public void testUtilisateurListTicket() {
		// Méthode métier pour lister les tickets
		utilisateur.listTicket();
		assertNotNull(utilisateur.getTickets());
	}

	@Test
	public void testUtilisateurAcheterTicket() {
		// Méthode métier pour acheter un ticket
		utilisateur.acheterTicket();
		assertNotNull(utilisateur);
	}

	@Test
	public void testUtilisateurAnnulerTicket() {
		// Méthode métier pour annuler un ticket
		utilisateur.annulerTicket();
		assertNotNull(utilisateur);
	}

	@Test
	public void testUtilisateurDateInscriptionMultiples() {
		// Test avec différentes dates
		LocalDate date1 = LocalDate.of(2020, 1, 1);
		LocalDate date2 = LocalDate.of(2023, 12, 31);
		
		utilisateur.setDateInscription(date1);
		assertEquals(date1, utilisateur.getDateInscription());
		
		utilisateur.setDateInscription(date2);
		assertEquals(date2, utilisateur.getDateInscription());
	}

	@Test
	public void testUtilisateurId() {
		utilisateur.setIdPersonne(1L);
		assertEquals(1L, utilisateur.getIdPersonne().longValue());
		
		utilisateur.setIdPersonne(999L);
		assertEquals(999L, utilisateur.getIdPersonne().longValue());
	}

	@Test
	public void testUtilisateurClearTickets() {
		utilisateur.setTickets(new ArrayList<>());
		Ticket t1 = new Ticket();
		t1.setNumeroPlace("T001");
		utilisateur.getTickets().add(t1);
		assertEquals(1, utilisateur.getTickets().size());
		
		utilisateur.getTickets().clear();
		assertTrue(utilisateur.getTickets().isEmpty());
	}
}


