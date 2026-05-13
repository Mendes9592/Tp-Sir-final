package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.generic.OrganisateurDao;
import fr.istic.taa.jaxrs.dao.generic.UtilisateurDao;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.dto.LoginRequest;
import fr.istic.taa.jaxrs.dto.LoginResponse;
import fr.istic.taa.jaxrs.dto.UtilisateurDto;
import fr.istic.taa.jaxrs.security.JwtUtil;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final UtilisateurDao utilisateurDao = new UtilisateurDao();
    private final OrganisateurDao organisateurDao = new OrganisateurDao();

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {

        // Admin fixe
        if ("admin@gmail.com".equals(request.email)
                && "admin123".equals(request.password)) {

            UtilisateurDto admin = new UtilisateurDto();
            admin.setIdPersonne(1L);
            admin.setNom("Admin");
            admin.setPrenom("System");
            admin.setEmail(request.email);
            admin.setRole("ADMIN");

            String token = JwtUtil.generateToken(admin.getEmail(), admin.getRole());
            return Response.ok(new LoginResponse(token, admin)).build();
        }

        // Utilisateur inscrit
        Utilisateur utilisateur = utilisateurDao.findByEmail(request.email);

        if (utilisateur != null
                && request.password.equals(utilisateur.getPassword())) {

            UtilisateurDto user = new UtilisateurDto();
            user.setIdPersonne(utilisateur.getIdPersonne());
            user.setNom(utilisateur.getNom());
            user.setPrenom(utilisateur.getPrenom());
            user.setEmail(utilisateur.getEmail());
            user.setRole("UTILISATEUR");

            String token = JwtUtil.generateToken(user.getEmail(), user.getRole());
            return Response.ok(new LoginResponse(token, user)).build();
        }

        // Organisateur inscrit
        Organisateur organisateur = organisateurDao.findByEmail(request.email);

        if (organisateur != null
                && request.password.equals(organisateur.getPassword())) {

            UtilisateurDto user = new UtilisateurDto();
            user.setIdPersonne(organisateur.getIdPersonne());
            user.setNom(organisateur.getNom());
            user.setPrenom(organisateur.getPrenom());
            user.setEmail(organisateur.getEmail());
            user.setRole("ORGANISATEUR");

            String token = JwtUtil.generateToken(user.getEmail(), user.getRole());
            return Response.ok(new LoginResponse(token, user)).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"erreur\":\"Email ou mot de passe incorrect\"}")
                .build();
    }
}