package com.example.restful;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class JerseyApplication {

    private static final Logger logger = LoggerFactory.getLogger(JerseyApplication.class);

    private WebAppContext webAppContext(HandlerList handlerList) {

        WebAppContext webAppContext = new WebAppContext();

        webAppContext.setContextPath("/petshop");
        handlerList.addHandler(webAppContext);


        return webAppContext;
    }

    private ServletContextHandler servletContext(HandlerList handlerList) {

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/api");

        // Add Jersey ServletContainer as servlet to the context handler
        ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/v1/*");
        servletHolder.setInitOrder(0);
        // init parameter 'jersey.config.server.provider.packages' designates packages to be searched for resources and providers
        servletHolder.setInitParameter(
                "jersey.config.server.provider.packages",
                "com.example.restful.api"
        );

        // add the servlet context handler to the list of handlers
        handlerList.addHandler(servletContextHandler);


        return servletContextHandler;
    }

    public static void main(String[] args) {

        Server server = new Server(12345);

        ServletContextHandler servletContextHandler = new ServletContextHandler(NO_SESSIONS);

        servletContextHandler.setContextPath("/");
        server.setHandler(servletContextHandler);

        ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/api/*");
        servletHolder.setInitOrder(0);
        servletHolder.setInitParameter(
                "jersey.config.server.provider.packages",
                "com.example.restful.api"
        );

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
