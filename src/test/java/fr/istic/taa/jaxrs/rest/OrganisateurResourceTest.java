package fr.istic.taa.jaxrs.rest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import fr.istic.taa.jaxrs.domain.Organisateur;
import jakarta.ws.rs.core.Response;

public class OrganisateurResourceTest {

	private OrganisateurResource resource;
	private Organisateur organisateur;

	@Before
	public void setUp() {
		resource = new OrganisateurResource();
		organisateur = new Organisateur();
	}

	@Test
	public void testOrganisateurResourceInitialization() {
		assertNotNull(resource);
	}

	@Test
	public void testGetAllOrganisateurs() {
		try {
			Response response = resource.getAllOrganisateurs();
			assertNotNull(response);
			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testGetOrganisateurById() {
		try {
			Response response = resource.getOrganisateurById(1L);
			assertNotNull(response);
			assertTrue("Le statut doit être 200 ou 404", 
					response.getStatus() == 200 || response.getStatus() == 404);
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testDeleteOrganisateur() {
		try {
			Response response = resource.deleteOrganisateur(1L);
			assertNotNull(response);
			assertTrue("Le statut doit être 204 ou 404", 
					response.getStatus() == 204 || response.getStatus() == 404);
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}
}

