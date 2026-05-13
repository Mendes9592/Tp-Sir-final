package fr.istic.taa.jaxrs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// Tests des entités (Domain)
import fr.istic.taa.jaxrs.domain.ArtisteTest;
import fr.istic.taa.jaxrs.domain.EvenementTest;
import fr.istic.taa.jaxrs.domain.TicketTest;
import fr.istic.taa.jaxrs.domain.UtilisateurTest;
import fr.istic.taa.jaxrs.domain.OrganisateurTest;

// Tests des DAOs
import fr.istic.taa.jaxrs.dao.ArtisteDaoTest;
import fr.istic.taa.jaxrs.dao.EvenementDaoTest;
import fr.istic.taa.jaxrs.dao.TicketDaoTest;

// Tests des Ressources REST
import fr.istic.taa.jaxrs.rest.ArtisteResourceTest;
import fr.istic.taa.jaxrs.rest.EvenementResourceTest;
import fr.istic.taa.jaxrs.rest.TicketResourceTest;
import fr.istic.taa.jaxrs.rest.OrganisateurResourceTest;

/**
 * Suite de tests complète pour le projet JAX-RS OpenAPI
 * Cette suite exécute tous les tests unitaires du projet
 */
@RunWith(Suite.class)
@SuiteClasses({
	// Tests des entités
	ArtisteTest.class,
	EvenementTest.class,
	TicketTest.class,
	UtilisateurTest.class,
	OrganisateurTest.class,
	
	// Tests des DAOs
	ArtisteDaoTest.class,
	EvenementDaoTest.class,
	TicketDaoTest.class,
	
	// Tests des ressources REST
	ArtisteResourceTest.class,
	EvenementResourceTest.class,
	TicketResourceTest.class,
	OrganisateurResourceTest.class
})
public class AllTests {
	// Cette classe reste vide, elle contient seulement les annotations
	// qui spécifient les classes de test à exécuter en suite
}

