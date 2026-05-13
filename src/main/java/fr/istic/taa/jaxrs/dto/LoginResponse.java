package fr.istic.taa.jaxrs.dto;

public class LoginResponse {

    public String token;
    public UtilisateurDto user;

    public LoginResponse() {}

    public LoginResponse(String token, UtilisateurDto user) {
        this.token = token;
        this.user = user;
    }
}