package fr.istic.taa.jaxrs.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicketTest {

	private Ticket ticket;

	@Before
	public void setUp() {
		ticket = new Ticket();
	}

	@Test
	public void testTicketCreation() {
		ticket.setIdTicket(1L);
		ticket.setNumeroPlace("TICKET-001");
		ticket.setPrixUnitaire(150.0);

		assertEquals(1L, ticket.getIdTicket().longValue());
		assertEquals("TICKET-001", ticket.getNumeroPlace());
		assertEquals(150.0, ticket.getPrixUnitaire(), 0.01);
	}

	@Test
	public void testTicketStatut() {
		ticket.setStatut(StatutTicketEnum.ACHETE);
		assertEquals(StatutTicketEnum.ACHETE, ticket.getStatut());

		ticket.setStatut(StatutTicketEnum.ANNULE);
		assertEquals(StatutTicketEnum.ANNULE, ticket.getStatut());

		ticket.setStatut(StatutTicketEnum.REMBOURSE);
		assertEquals(StatutTicketEnum.REMBOURSE, ticket.getStatut());
	}

	@Test
	public void testTicketEvenement() {
		Evenement evt = new Evenement();
		evt.setIdEvenement(1L);
		evt.setNom("Concert");
		ticket.setEvenement(evt);

		assertNotNull(ticket.getEvenement());
		assertEquals("Concert", ticket.getEvenement().getNom());
	}

	@Test
	public void testTicketUtilisateur() {
		Utilisateur user = new Utilisateur("Martin", "Paul", "martin.paul@email.com");
		user.setNom("Dupont");
		ticket.setUtilisateur(user);

		assertNotNull(ticket.getUtilisateur());
		assertEquals("Dupont", ticket.getUtilisateur().getNom());
	}


	@Test
	public void testTicketNumeroUnique() {
		ticket.setNumeroPlace("UNIQUE-123456");
		assertEquals("UNIQUE-123456", ticket.getNumeroPlace());
	}



	@Test
	public void testTicketToString() {
		ticket.setNumeroPlace("T001");
		String str = ticket.toString();
		assertNotNull(str);
	}
}

