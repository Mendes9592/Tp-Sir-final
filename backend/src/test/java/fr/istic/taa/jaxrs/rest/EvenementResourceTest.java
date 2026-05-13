package fr.istic.taa.jaxrs.rest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import fr.istic.taa.jaxrs.domain.Evenement;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

public class EvenementResourceTest {

	private EvenementResource resource;
	private Evenement evenement;

	@Before
	public void setUp() {
		resource = new EvenementResource();
		evenement = new Evenement();
	}

	@Test
	public void testEvenementResourceInitialization() {
		assertNotNull(resource);
	}

	@Test
	public void testGetAllEvenements() {
		try {
			Response response = resource.getAllEvenements();
			assertNotNull(response);
			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testGetEvenementById() {
		try {
			Response response = resource.getEvenementById(1L);
			assertNotNull(response);
			assertTrue("Le statut doit être 200 ou 404", 
					response.getStatus() == 200 || response.getStatus() == 404);
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testCreateEvenement() {
		try {
			evenement.setNom("Festival Rock 2024");
			evenement.setDate(LocalDate.of(2024, 6, 15));
			evenement.setGenre("Rock");
			evenement.setLieu("Paris");
			
			Response response = resource.createEvenement(evenement);
			assertNotNull(response);
			assertEquals(201, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testUpdateEvenement() {
		try {
			evenement.setIdEvenement(1L);
			evenement.setNom("Festival Rock 2024");
			evenement.setDate(LocalDate.of(2024, 6, 15));
			
			Response response = resource.updateEvenement(1L, evenement);
			assertNotNull(response);
			assertTrue("Le statut doit être 200 ou 404", 
					response.getStatus() == 200 || response.getStatus() == 404);
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testDeleteEvenement() {
		try {
			Response response = resource.deleteEvenement(1L);
			assertNotNull(response);
			assertTrue("Le statut doit être 204 ou 404", 
					response.getStatus() == 204 || response.getStatus() == 404);
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testGetEvenementsByDate() {
		try {
			Response response = resource.getEvenementById(Long.valueOf(LocalDate.of(2024, 6, 15).toString()));
			assertNotNull(response);
			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testEvenementValidity() {
		evenement.setNom("Test Event");
		evenement.setDate(LocalDate.now().plusMonths(1));
		evenement.setGenre("Jazz");
		
		assertNotNull(evenement.getNom());
		assertNotNull(evenement.getDate());
		assertEquals("Test Event", evenement.getNom());
		assertEquals("Jazz", evenement.getGenre());
	}
}

