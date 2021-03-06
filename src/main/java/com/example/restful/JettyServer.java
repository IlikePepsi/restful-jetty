package com.example.restful;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class JettyServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    private static WebAppContext webAppContext() {

        final WebAppContext webAppContext = new WebAppContext();
        final URL warUrl = Thread.currentThread().getContextClassLoader().getResource("webapp");
        final String warUrlString = warUrl.toExternalForm();

        webAppContext.setContextPath("/petshop");
        webAppContext.setWar(warUrlString);
        webAppContext.setWelcomeFiles(new String[] {"index.html"});
        webAppContext.setDisplayName("Petshop App");
        

        return webAppContext;
    }

    private static ServletContextHandler restApiContext() {

        final ServletContextHandler servletContextHandler = new ServletContextHandler(NO_SESSIONS);
        servletContextHandler.setContextPath("/api");

        // Use the Jersey ServletContainer as context handler for matching paths
        ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/v1/*");
        servletHolder.setInitOrder(0);
        // init parameter 'jersey.config.server.provider.packages' designates packages to be searched for resources and providers
        servletHolder.setInitParameter(
                "jersey.config.server.provider.packages",
                "com.example.restful.api"
        );

        return servletContextHandler;
    }

    public static void main(String[] args) {

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(12345);
        server.addConnector(connector);

        HandlerList handlerList = new HandlerList();

        handlerList.addHandler(webAppContext());
        handlerList.addHandler(restApiContext());

//        handlerList.setHandlers(new Handler[] { webAppContext(), restApiContext() });

        server.setHandler(handlerList);

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            logger.error("Error occurred while starting Jetty", ex);
            System.exit(1);
        }

        finally {
            server.destroy();
        }
    }
}
