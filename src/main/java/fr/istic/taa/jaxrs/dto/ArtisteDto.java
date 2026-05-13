package fr.istic.taa.jaxrs.dto;

import jakarta.ws.rs.core.MultivaluedMap;

public class ArtisteDto {

    private String styleArtistique;
    private String nom;
    private String prenom;
    private String nationalite;
    private Integer popularite;

    // Constructeur vide (nécessaire pour la sérialisation)
    public ArtisteDto() {
    }

    // Constructeur avec MultivaluedMap (depuis QueryParameters)
    public ArtisteDto(MultivaluedMap<String, String> queryParameters) {
        if (queryParameters != null) {

            this.styleArtistique = queryParameters.getFirst("styleArtistique");
            this.nom = queryParameters.getFirst("nom");
            this.prenom = queryParameters.getFirst("prenom");
            this.nationalite = queryParameters.getFirst("nationalite");

            try {
                String popStr = queryParameters.getFirst("popularite");
                if (popStr != null && !popStr.isEmpty()) {
                    this.popularite = Integer.parseInt(popStr);
                }
            } catch (NumberFormatException e) {
                this.popularite = null;
            }
        }
    }

    public String getStyleArtistique() {
        return styleArtistique;
    }

    public void setStyleArtistique(String styleArtistique) {
        this.styleArtistique = styleArtistique;
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

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public Integer getPopularite() {
        return popularite;
    }

    public void setPopularite(Integer popularite) {
        this.popularite = popularite;
    }
}