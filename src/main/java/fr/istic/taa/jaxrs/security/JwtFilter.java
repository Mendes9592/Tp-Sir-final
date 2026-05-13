package fr.istic.taa.jaxrs.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String path = requestContext.getUriInfo().getPath();

        // Routes publiques
        if (
                path.startsWith("auth/login") ||
                        path.startsWith("auth/register") ||
                        path.startsWith("evenements") ||
                        path.startsWith("artistes") ||
                        path.startsWith("tickets")
        ) {
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Token manquant")
                            .build()
            );
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try {
            JwtUtil.validateToken(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Token invalide")
                            .build()
            );
        }
    }
}