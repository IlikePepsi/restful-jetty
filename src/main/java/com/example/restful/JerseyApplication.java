package com.example.restful;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;

import java.lang.management.ManagementFactory;
import java.net.URL;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class JerseyApplication {

    private static final Logger logger = LoggerFactory.getLogger(JerseyApplication.class);

    private static WebAppContext webAppContext() {

        final WebAppContext webAppContext = new WebAppContext();
        final URL warUrl = Thread.currentThread().getContextClassLoader().getResource("webapp");
        final String warUrlString = warUrl.toExternalForm();

        String[] welcomeFiles = {"index.html"};

        webAppContext.setContextPath("/petshop");
        webAppContext.setWar(warUrlString);
        webAppContext.setWelcomeFiles(welcomeFiles);
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

        Server server = new Server(12345);

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
