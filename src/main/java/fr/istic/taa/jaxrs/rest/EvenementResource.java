package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Evenement;
import fr.istic.taa.jaxrs.dto.EvenementDto;
import fr.istic.taa.jaxrs.service.EvenementService;
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

import java.time.LocalDate;
import java.util.List;

@Path("evenements")
@Produces({"application/json"})
@Tag(name = "Événements", description = "API complète pour gérer les événements")
public class EvenementResource {

    private final EvenementService service = new EvenementService();

    // Recuperer tous les evenements
    @GET
    @Operation(summary = "Récupérer tous les événements")
    public Response getAllEvenements() {
        try {
            List<Evenement> evenements = service.getAllEvenements();
            return Response.ok(evenements).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // 🔹 GET BY ID
    @GET
    @Path("/{id}")
    @Operation(summary = "Récupérer un événement par ID")
    public Response getEvenementById(@PathParam("id") Long id) {
        try {
            Evenement evenement = service.getEvenementById(id);

            if (evenement == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Événement non trouvé")
                        .build();
            }

            return Response.ok(evenement).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Creer un evenement
    @POST
    @Consumes("application/json")
    @Operation(summary = "Créer un événement")
    public Response createEvenement(Evenement evenement) {
        try {
            Evenement created = service.createEvenement(evenement);

            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Mettre a jour un evenement
    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Operation(summary = "Mettre à jour un événement")
    public Response updateEvenement(@PathParam("id") Long id, Evenement evenement) {
        try {
            Evenement updated = service.updateEvenement(id, evenement);
            return Response.ok(updated).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Supprimer un evenement
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprimer un événement")
    public Response deleteEvenement(@PathParam("id") Long id) {
        try {
            service.deleteEvenement(id);
            return Response.noContent().build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Rercherche avancée
    @GET
    @Path("/search")
    @Operation(summary = "Recherche avancée")
    public Response searchEvenements(@Context UriInfo info) {
        try {
            EvenementDto dto = new EvenementDto(info.getQueryParameters());

            List<Evenement> result = service.search(
                    dto.getNom(),
                    dto.getGenre(),
                    dto.getLieu()
            );

            return Response.ok(result).build();

        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    //Evenements par date
    @GET
    @Path("/by-date/{date}")
    @Operation(summary = "Événements par date")
    public Response getByDate(@PathParam("date") String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            List<Evenement> result = service.getByDate(parsedDate);

            return Response.ok(result).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Format de date invalide")
                    .build();
        }
    }

    // Événements populaires
    @GET
    @Path("/populaires/{seuil}")
    @Operation(summary = "Événements populaires")
    public Response getPopular(@PathParam("seuil") Float seuil) {
        try {
            List<Evenement> result = service.getPopular(seuil);
            return Response.ok(result).build();

        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Evenements avec artistes
    @GET
    @Path("/with-artistes")
    @Operation(summary = "Événements avec artistes")
    public Response getWithArtistes() {
        try {
            List<Evenement> result = service.getWithArtistes();
            return Response.ok(result).build();

        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}