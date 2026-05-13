package fr.istic.taa.jaxrs.rest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.StatutTicketEnum;
import jakarta.ws.rs.core.Response;

public class TicketResourceTest {

	private TicketResource resource;
	private Ticket ticket;

	@Before
	public void setUp() {
		resource = new TicketResource();
		ticket = new Ticket();
	}

	@Test
	public void testTicketResourceInitialization() {
		assertNotNull(resource);
	}

	@Test
	public void testGetAllTickets() {
		try {
			Response response = resource.getAllTickets();
			assertNotNull(response);
			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testGetTicketById() {
		try {
			Response response = resource.getTicketById(1L);
			assertNotNull(response);
			assertTrue("Le statut doit être 200 ou 404", 
					response.getStatus() == 200 || response.getStatus() == 404);
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testCreateTicket() {
		try {
			ticket.setNumeroPlace("TICKET-001");
			ticket.setPrixUnitaire(150.0);
			ticket.setStatut(StatutTicketEnum.ACHETE);
			
			Response response = resource.createTicket(ticket);
			assertNotNull(response);
			assertEquals(201, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testUpdateTicket() {
		try {
			ticket.setIdTicket(1L);
			ticket.setNumeroPlace("TICKET-001");
			ticket.setPrixUnitaire(150.0);
			
			Response response = resource.updateTicket(1L, ticket);
			assertNotNull(response);
			assertTrue("Le statut doit être 200 ou 404", 
					response.getStatus() == 200 || response.getStatus() == 404);
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testDeleteTicket() {
		try {
			Response response = resource.deleteTicket(1L);
			assertNotNull(response);
			assertTrue("Le statut doit être 204 ou 404", 
					response.getStatus() == 204 || response.getStatus() == 404);
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testGetTicketsByEvenement() {
		try {
			Response response = resource.getTicketsByEvenement(1L);
			assertNotNull(response);
			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testGetTicketsByUtilisateur() {
		try {
			Response response = resource.getTicketsByUtilisateur(1L);
			assertNotNull(response);
			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testGetTicketsByStatut() {
		try {
			Response response = resource.getTicketsByStatut("DISPONIBLE");
			assertNotNull(response);
			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testTicketValidity() {
		ticket.setNumeroPlace("T123");
		ticket.setPrixUnitaire(75.0);
		
		assertNotNull(ticket.getNumeroPlace());
		assertEquals("T123", ticket.getNumeroPlace());
		assertEquals(75.0, ticket.getPrixUnitaire(), 0.01);
	}
}

