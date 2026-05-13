package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.dto.UtilisateurDto;
import fr.istic.taa.jaxrs.service.UtilisateurService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

/**
 * REST API pour la gestion des utilisateurs
 * Fournit les opérations CRUD et des endpoints métier pour les utilisateurs
 */
@Path("utilisateurs")
@Consumes("application/json")
@Produces({"application/json"})
@Tag(name = "Utilisateurs", description = "API pour gérer les utilisateurs")
public class UtilisateurResource {

    private final UtilisateurService service = new UtilisateurService();

    /**
     * Récupère la liste de tous les utilisateurs
     * @return Liste de tous les utilisateurs
     */
    @GET
    @Operation(
            summary = "Récupérer tous les utilisateurs",
            description = "Retourne la liste complète de tous les utilisateurs"
    )
    public Response getAllUtilisateurs() {
        try {
            List<Utilisateur> utilisateurs = service.getAllUtilisateurs();
            return Response.ok(utilisateurs).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Récupère un utilisateur par son ID
     */
    @GET
    @Path("/{id}")
    public Response getUtilisateurById(@PathParam("id") Long id) {
        try {
            Utilisateur utilisateur = service.getUtilisateurById(id);
            if (utilisateur == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erreur\": \"Utilisateur non trouvé\"}")
                        .build();
            }
            return Response.ok(utilisateur).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crée un nouvel utilisateur
     */
    @POST
    public Response createUtilisateur(Utilisateur utilisateur) {
        try {
            Utilisateur created = service.createUtilisateur(utilisateur);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Met à jour un utilisateur existant
     */
    @PUT
    @Path("/{id}")
    public Response updateUtilisateur(@PathParam("id") Long id, Utilisateur utilisateur) {
        try {
            Utilisateur updated = service.updateUtilisateur(id, utilisateur);
            if (updated == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erreur\": \"Utilisateur non trouvé\"}")
                        .build();
            }
            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Supprime un utilisateur
     */
    @DELETE
    @Path("/{id}")
    public Response deleteUtilisateur(@PathParam("id") Long id) {
        try {
            boolean deleted = service.deleteUtilisateur(id);
            if (!deleted) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erreur\": \"Utilisateur non trouvé\"}")
                        .build();
            }
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Recherche avancée d'utilisateurs (endpoint métier)
     */
    @GET
    @Path("/search")
    public Response searchUtilisateurs(@Context UriInfo info) {
        try {
            UtilisateurDto dto = new UtilisateurDto(info.getQueryParameters());
            List<Utilisateur> utilisateurs = service.searchUtilisateurs(
                    dto.getNom(),
                    dto.getPrenom(),
                    dto.getEmail()
            );
            return Response.ok(utilisateurs).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupère un utilisateur par email (endpoint métier)
     */
    @GET
    @Path("/by-email/{email}")
    public Response getUtilisateurByEmail(@PathParam("email") String email) {
        try {
            Utilisateur utilisateur = service.getUtilisateurByEmail(email);
            if (utilisateur == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erreur\": \"Utilisateur non trouvé\"}")
                        .build();
            }
            return Response.ok(utilisateur).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Compte les tickets d'un utilisateur (endpoint métier)
     */
    @GET
    @Path("/tickets-count/{utilisateurId}")
    public Response countTickets(@PathParam("utilisateurId") Long utilisateurId) {
        try {
            Long count = service.countTicketsUtilisateur(utilisateurId);
            return Response.ok("{\"nombre_tickets\": " + count + "}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}