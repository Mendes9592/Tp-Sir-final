package fr.istic.taa.jaxrs.dto;

import jakarta.ws.rs.core.MultivaluedMap;

/**
 * DTO utilisateur
 */
public class UtilisateurDto {

    private Long idPersonne;

    private String nom;
    private String prenom;
    private String email;

    private String role;

    public UtilisateurDto() {
    }

    public UtilisateurDto(MultivaluedMap<String, String> queryParameters) {
        if (queryParameters != null) {
            this.nom = queryParameters.getFirst("nom");
            this.prenom = queryParameters.getFirst("prenom");
            this.email = queryParameters.getFirst("email");
        }
    }

    public Long getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Long idPersonne) {
        this.idPersonne = idPersonne;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}