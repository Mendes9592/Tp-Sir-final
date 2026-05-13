package fr.istic.taa.jaxrs.rest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import fr.istic.taa.jaxrs.domain.Artiste;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

public class ArtisteResourceTest {

	private ArtisteResource resource;
	private Artiste artiste;

	@Before
	public void setUp() {
		resource = new ArtisteResource();
		artiste = new Artiste();
	}

	@Test
	public void testArtisteResourceInitialization() {
		assertNotNull(resource);
	}

	@Test
	public void testGetAllArtistes() {
		try {
			Response response = resource.getAllArtistes();
			assertNotNull(response);
			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testGetArtisteById() {
		try {
			Response response = resource.getArtisteById(1L);
			assertNotNull(response);
			// Le statut peut être 200 (trouvé) ou 404 (non trouvé)
			assertTrue("Le statut doit être 200 ou 404", 
					response.getStatus() == 200 || response.getStatus() == 404);
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testCreateArtiste() {
		try {
			artiste.setNom("Dupont");
			artiste.setPrenom("Jean");
			artiste.setStyleArtistique("Rock");
			
			Response response = resource.createArtiste(artiste);
			assertNotNull(response);
			assertEquals(201, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testUpdateArtiste() {
		try {
			artiste.setIdArtiste(1L);
			artiste.setNom("Dupont");
			artiste.setPrenom("Jean");
			
			Response response = resource.updateArtiste(1L, artiste);
			assertNotNull(response);
			assertTrue("Le statut doit être 200 ou 404", 
					response.getStatus() == 200 || response.getStatus() == 404);
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}

	@Test
	public void testDeleteArtiste() {
		try {
			Response response = resource.deleteArtiste(1L);
			assertNotNull(response);
			assertTrue("Le statut doit être 204 ou 404", 
					response.getStatus() == 204 || response.getStatus() == 404);
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}


	@Test
	public void testGetArtistesByNationalite() {
		try {
			Response response = resource.getByNationalite("Français");
			assertNotNull(response);
			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			System.out.println("Test échoué: " + e.getMessage());
		}
	}


	@Test
	public void testArtisteValidity() {
		artiste.setNom("Test");
		artiste.setPrenom("Artiste");
		artiste.setStyleArtistique("Pop");
		
		assertNotNull(artiste.getNom());
		assertNotNull(artiste.getPrenom());
		assertEquals("Test", artiste.getNom());
		assertEquals("Artiste", artiste.getPrenom());
		assertEquals("Pop", artiste.getStyleArtistique());
	}
}

