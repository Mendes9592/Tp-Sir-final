package fr.istic.taa.jaxrs;

import io.undertow.Undertow;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

import java.util.logging.Logger;

public class RestServer {

    private static final Logger logger = Logger.getLogger(RestServer.class.getName());

    public static void main(String[] args) {

        UndertowJaxrsServer server = new UndertowJaxrsServer();

        // ✅ IMPORTANT : passer la classe, pas l'instance
        server.deploy(TestApplication.class);

        server.start(
                Undertow.builder()
                        .addHttpListener(8080, "localhost")
        );

        System.out.println("Server starting...");
        logger.info("JAX-RS based micro-service running!");
    }
}