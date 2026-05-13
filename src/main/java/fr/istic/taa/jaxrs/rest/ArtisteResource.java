package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.dto.ArtisteDto;
import fr.istic.taa.jaxrs.service.ArtisteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;

@Path("artistes")
@Produces("application/json")
@Consumes("application/json")
public class ArtisteResource {

    private final ArtisteService service = new ArtisteService();

    // Récupérer tous les artistes
    @GET
    @Operation(summary = "Récupérer tous les artistes")
    public Response getAllArtistes() {
        try {
            return Response.ok(service.getAllArtistes()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Récupérer un artiste par ID
    @GET
    @Path("/{id}")
    @Operation(summary = "Récupérer un artiste par ID")
    public Response getArtisteById(@PathParam("id") Long id) {
        try {
            Artiste artiste = service.getArtisteById(id);

            if (artiste == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Artiste non trouvé")
                        .build();
            }

            return Response.ok(artiste).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Créer un nouvel artiste
    @POST
    @Operation(summary = "Créer un artiste")
    public Response createArtiste(Artiste artiste) {
        try {
            Artiste created = service.createArtiste(artiste);

            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Mettre à jour un artiste existant
    @PUT
    @Path("/{id}")
    @Operation(summary = "Mettre à jour un artiste")
    public Response updateArtiste(@PathParam("id") Long id, Artiste artiste) {
        try {
            Artiste updated = service.updateArtiste(id, artiste);
            return Response.ok(updated).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Supprimer un artiste
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprimer un artiste")
    public Response deleteArtiste(@PathParam("id") Long id) {
        try {
            service.deleteArtiste(id);
            return Response.noContent().build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Recherche avancée
    @GET
    @Path("/search")
    @Operation(summary = "Recherche avancée")
    public Response searchArtistes(@Context UriInfo info) {
        try {
            ArtisteDto dto = new ArtisteDto(info.getQueryParameters());
            List<Artiste> result = service.searchArtistes(dto);

            return Response.ok(result).build();

        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Recherche par style artistique
    @GET
    @Path("/by-style/{style}")
    public Response getByStyle(@PathParam("style") String style) {
        try {
            return Response.ok(service.getArtistesByStyle(style)).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    // Recherche par nationalité
    @GET
    @Path("/by-nationalite/{nationalite}")
    public Response getByNationalite(@PathParam("nationalite") String nationalite) {
        try {
            return Response.ok(service.getArtistesByNationalite(nationalite)).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}