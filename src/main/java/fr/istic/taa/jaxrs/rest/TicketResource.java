package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.domain.StatutTicketEnum;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * REST API pour la gestion des tickets
 * Fournit les opérations CRUD et des endpoints métier pour les tickets
 */
@Path("tickets")
@Produces({"application/json"})
@Consumes("application/json")
@Tag(name = "Tickets", description = "API pour gérer les tickets d'événements")
public class TicketResource {

    private final TicketService service = new TicketService();

    /**
     * Récupère la liste de tous les tickets
     */
    @GET
    @Operation(summary = "Récupérer tous les tickets")
    public Response getAllTickets() {
        try {
            List<Ticket> tickets = service.getAllTickets();
            return Response.ok(tickets).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Récupère un ticket par son ID
     */
    @GET
    @Path("/{id}")
    @Operation(summary = "Récupérer un ticket par ID")
    public Response getTicketById(@PathParam("id") Long id) {
        try {
            Ticket ticket = service.getTicketById(id);

            if (ticket == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erreur\": \"Ticket non trouvé\"}")
                        .build();
            }

            return Response.ok(ticket).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Crée un nouveau ticket
     */
    @POST
    @Operation(summary = "Créer un ticket")
    public Response createTicket(Ticket ticket) {
        try {
            Ticket created = service.createTicket(ticket);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Met à jour un ticket
     */
    @PUT
    @Path("/{id}")
    @Operation(summary = "Mettre à jour un ticket")
    public Response updateTicket(@PathParam("id") Long id, Ticket ticket) {
        try {
            Ticket updated = service.updateTicket(id, ticket);

            if (updated == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erreur\": \"Ticket non trouvé\"}")
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
     * Supprime un ticket
     */
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprimer un ticket")
    public Response deleteTicket(@PathParam("id") Long id) {

        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"ID invalide\"}")
                    .build();
        }

        try {
            Ticket ticket = service.findOne(id);

            if (ticket == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erreur\": \"Ticket non trouvé\"}")
                        .build();
            }

            service.delete(ticket);

            return Response.noContent().build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"erreur\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Récupère les tickets par statut
     */
    @GET
    @Path("/by-statut/{statut}")
    public Response getTicketsByStatut(@PathParam("statut") String statut) {
        try {
            StatutTicketEnum statutEnum = StatutTicketEnum.valueOf(statut.toUpperCase());
            return Response.ok(service.getTicketsByStatut(statutEnum)).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erreur\": \"Statut invalide\"}")
                    .build();
        }
    }

    /**
     * Tickets d’un utilisateur
     */
    @GET
    @Path("/utilisateur/{id}")
    public Response getTicketsByUtilisateur(@PathParam("id") Long id) {
        try {
            return Response.ok(service.getTicketsByUtilisateur(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Tickets d’un événement
     */
    @GET
    @Path("/evenement/{id}")
    public Response getTicketsByEvenement(@PathParam("id") Long id) {
        try {
            return Response.ok(service.getTicketsByEvenement(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Vérifie un ticket à partir de son ID.
     * Utilisé pour contrôler le QR code à l'entrée.
     */
    @GET
    @Path("/verify/{id}")
    @Produces("application/json")
    public Response verifyTicket(@PathParam("id") Long id) {
        try {
            Ticket ticket = service.getTicketById(id);

            if (ticket == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"valid\": false, \"message\": \"Ticket introuvable\"}")
                        .build();
            }

            if (ticket.getStatut() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"valid\": false, \"message\": \"Statut du ticket invalide\"}")
                        .build();
            }

            if (!ticket.getStatut().name().equals("ACHETE")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"valid\": false, \"message\": \"Ticket déjà utilisé ou annulé\"}")
                        .build();
            }

            return Response.ok(ticket).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"valid\": false, \"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Marque un ticket comme utilisé après contrôle.
     */
    @PUT
    @Path("/validate/{id}")
    @Produces("application/json")
    public Response validateTicket(@PathParam("id") Long id) {
        try {
            Ticket ticket = service.getTicketById(id);

            if (ticket == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Ticket introuvable\"}")
                        .build();
            }

            if (!ticket.getStatut().name().equals("ACHETE")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"Ticket déjà utilisé ou annulé\"}")
                        .build();
            }

            ticket.setStatut(StatutTicketEnum.UTILISE);

            Ticket updated = service.updateTicket(id, ticket);

            return Response.ok(updated).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Chiffre d’affaires d’un événement
     */
    @GET
    @Path("/chiffre-affaires/{id}")
    public Response getChiffreAffaires(@PathParam("id") Long id) {
        try {
            Double ca = service.getChiffreAffaires(id);
            return Response.ok("{\"chiffre_affaires\": " + ca + "}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Places restantes
     */
    @GET
    @Path("/places-restantes/{id}")
    public Response getPlacesRestantes(@PathParam("id") Long id) {
        try {
            Long places = service.getPlacesRestantes(id);
            return Response.ok("{\"places_restantes\": " + places + "}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}