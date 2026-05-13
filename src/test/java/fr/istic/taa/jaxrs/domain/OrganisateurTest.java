package fr.istic.taa.jaxrs.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class OrganisateurTest {

	private Organisateur organisateur;

	@Before
	public void setUp() {
		organisateur = new Organisateur();
	}


	@Test
	public void testOrganisateurEmail() {
		organisateur.setEmail("org@email.com");
		assertEquals("org@email.com", organisateur.getEmail());
	}


	@Test
	public void testOrganisateurEvenements() {
		organisateur.setEvenements(new ArrayList<>());
		assertTrue(organisateur.getEvenements().isEmpty());

		Evenement evt = new Evenement();
		evt.setNom("Festival");
		organisateur.getEvenements().add(evt);

		assertEquals(1, organisateur.getEvenements().size());
		assertEquals("Festival", organisateur.getEvenements().get(0).getNom());
	}


	@Test
	public void testOrganisateurMultiplesEvenements() {
		organisateur.setEvenements(new ArrayList<>());

		Evenement evt1 = new Evenement();
		evt1.setNom("Festival 1");
		Evenement evt2 = new Evenement();
		evt2.setNom("Festival 2");

		organisateur.getEvenements().add(evt1);
		organisateur.getEvenements().add(evt2);

		assertEquals(2, organisateur.getEvenements().size());
	}

	@Test
	public void testOrganisateurToString() {
		organisateur.setNom("EventPro");
		String str = organisateur.toString();
		assertNotNull(str);
	}
}

