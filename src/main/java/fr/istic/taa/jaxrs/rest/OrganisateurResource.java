package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.dto.OrganisateurDto;
import fr.istic.taa.jaxrs.service.OrganisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;

/**
 * REST API pour la gestion des organisateurs
 * Fournit les opérations CRUD et des endpoints métier pour les organisateurs
 */
@Path("organisateurs")
@Produces({"application/json"})
@Consumes({"application/json"})
@Tag(name = "Organisateurs", description = "API pour gérer les organisateurs d'événements")
public class OrganisateurResource {

    private final OrganisateurService service = new OrganisateurService();

    /**
     * Récupère la liste de tous les organisateurs
     * @return Liste de tous les organisateurs
     */
    @GET
    @Operation(
            summary = "Récupérer tous les organisateurs",
            description = "Retourne la liste complète de tous les organisateurs"
    )
    @ApiResponse(responseCode = "200", description = "Liste des organisateurs",
            content = @Content(schema = @Schema(implementation = Organisateur.class)))
    public Response getAllOrganisateurs() {
        try {
            return Response.ok(service.findAll()).build();
        } catch (Exception e) {
            return Response.serverError().entity(error(e)).build();
        }
    }

    /**
     * Récupère un organisateur par son ID
     * @param id L'identifiant unique de l'organisateur
     * @return L'organisateur correspondant ou 404 si non trouvé
     */
    @GET
    @Path("/{id}")
    @Operation(
            summary = "Récupérer un organisateur par ID",
            description = "Retourne les détails d'un organisateur spécifique"
    )
    public Response getOrganisateurById(@PathParam("id") Long id) {
        if (invalidId(id)) return badRequest("ID invalide");

        try {
            Organisateur o = service.findById(id);
            if (o == null) return notFound("Organisateur non trouvé");
            return Response.ok(o).build();
        } catch (Exception e) {
            return Response.serverError().entity(error(e)).build();
        }
    }

    /**
     * Crée un nouvel organisateur
     * @param organisateur L'organisateur à créer
     * @return Réponse avec statut de création
     */
    @POST
    @Operation(
            summary = "Créer un nouvel organisateur",
            description = "Crée un organisateur avec les données fournies"
    )
    public Response createOrganisateur(Organisateur organisateur) {
        if (organisateur == null || organisateur.getNomStructure() == null) {
            return badRequest("Données invalides");
        }

        try {
            service.create(organisateur);
            return Response.status(Response.Status.CREATED).entity(organisateur).build();
        } catch (Exception e) {
            return Response.serverError().entity(error(e)).build();
        }
    }

    /**
     * Met à jour un organisateur existant
     */
    @PUT
    @Path("/{id}")
    public Response updateOrganisateur(@PathParam("id") Long id, Organisateur organisateur) {
        if (invalidId(id) || organisateur == null) {
            return badRequest("Données invalides");
        }

        try {
            Organisateur existing = service.findById(id);
            if (existing == null) return notFound("Organisateur non trouvé");

            organisateur.setIdPersonne(id);
            service.update(organisateur);
            return Response.ok(organisateur).build();
        } catch (Exception e) {
            return Response.serverError().entity(error(e)).build();
        }
    }

    /**
     * Supprime un organisateur
     */
    @DELETE
    @Path("/{id}")
    public Response deleteOrganisateur(@PathParam("id") Long id) {
        if (invalidId(id)) return badRequest("ID invalide");

        try {
            Organisateur o = service.findById(id);
            if (o == null) return notFound("Organisateur non trouvé");

            service.delete(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.serverError().entity(error(e)).build();
        }
    }

    /**
     * Recherche avancée d'organisateurs (endpoint métier)
     */
    @GET
    @Path("/search")
    public Response searchOrganisateurs(@Context UriInfo info) {
        try {
            OrganisateurDto dto = new OrganisateurDto(info.getQueryParameters());
            return Response.ok(service.search(dto)).build();
        } catch (Exception e) {
            return Response.serverError().entity(error(e)).build();
        }
    }

    /**
     * Récupère un organisateur par SIRET (endpoint métier)
     */
    @GET
    @Path("/by-siret/{siret}")
    public Response getOrganisateurBySiret(@PathParam("siret") String siret) {
        try {
            Organisateur o = service.findBySiret(siret);
            if (o == null) return notFound("Organisateur non trouvé");
            return Response.ok(o).build();
        } catch (Exception e) {
            return Response.serverError().entity(error(e)).build();
        }
    }

    /**
     * Compte les événements d'un organisateur (endpoint métier)
     */
    @GET
    @Path("/count-evenements/{organisateurId}")
    public Response countEvenements(@PathParam("organisateurId") Long id) {
        try {
            Long count = service.countEvenements(id);
            return Response.ok("{\"nombre_evenements\": " + count + "}").build();
        } catch (Exception e) {
            return Response.serverError().entity(error(e)).build();
        }
    }

    // ================= UTILS =================

    private boolean invalidId(Long id) {
        return id == null || id <= 0;
    }

    private Response badRequest(String msg) {
        return Response.status(Response.Status.BAD_REQUEST).entity("{\"erreur\": \"" + msg + "\"}").build();
    }

    private Response notFound(String msg) {
        return Response.status(Response.Status.NOT_FOUND).entity("{\"erreur\": \"" + msg + "\"}").build();
    }

    private String error(Exception e) {
        return "{\"erreur\": \"" + e.getMessage() + "\"}";
    }
}