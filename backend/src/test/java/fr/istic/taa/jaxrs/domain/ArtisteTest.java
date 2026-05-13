package fr.istic.taa.jaxrs.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArtisteTest {

	private Artiste artiste;

	@Before
	public void setUp() {
		artiste = new Artiste();
	}

	@Test
	public void testArtisteCreation() {
		artiste.setIdArtiste(1L);
		artiste.setNom("Dupont");
		artiste.setPrenom("Jean");
		artiste.setStyleArtistique("Rock");
		artiste.setNationalite("Français");
		artiste.setDateNaissance(LocalDate.of(1990, 1, 15));
		artiste.setPopularite(75);

		assertEquals(1L, artiste.getIdArtiste().longValue());
		assertEquals("Dupont", artiste.getNom());
		assertEquals("Jean", artiste.getPrenom());
		assertEquals("Rock", artiste.getStyleArtistique());
		assertEquals("Français", artiste.getNationalite());
		assertEquals(LocalDate.of(1990, 1, 15), artiste.getDateNaissance());
		assertEquals(75, artiste.getPopularite().intValue());
	}

	@Test
	public void testArtisteDescription() {
		String description = "Artiste talentueux avec 20 ans d'expérience";
		artiste.setDescription(description);
		assertEquals(description, artiste.getDescription());
	}

	@Test
	public void testArtisteSiteWeb() {
		String siteWeb = "https://www.artiste.com";
		artiste.setSiteWeb(siteWeb);
		assertEquals(siteWeb, artiste.getSiteWeb());
	}

	@Test
	public void testArtisteEvenements() {
		artiste.setEvenements(new ArrayList<>());
		assertTrue(artiste.getEvenements().isEmpty());

		Evenement evt = new Evenement();
		evt.setIdEvenement(1L);
		evt.setNom("Concert");
		artiste.getEvenements().add(evt);

		assertEquals(1, artiste.getEvenements().size());
		assertEquals("Concert", artiste.getEvenements().get(0).getNom());
	}

	@Test
	public void testArtisteConstructorWithParams() {
		Artiste a = new Artiste("Martin", "Luc");
		assertEquals("Martin", a.getNom());
		assertEquals("Luc", a.getPrenom());
	}

	@Test
	public void testArtisteToString() {
		artiste.setNom("Dupont");
		artiste.setPrenom("Jean");
		String str = artiste.toString();
		assertNotNull(str);
		assertTrue(str.contains("Dupont") || str.contains("Jean"));
	}

	@Test
	public void testArtistePopulariteMinimale() {
		artiste.setPopularite(0);
		assertEquals(0, artiste.getPopularite().intValue());
	}

	@Test
	public void testArtistePopulariteMaximale() {
		artiste.setPopularite(100);
		assertEquals(100, artiste.getPopularite().intValue());
	}
}

