package com.example;

import com.example.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.jersey.sessions.FlashProvider;
import io.dropwizard.jersey.sessions.HttpSessionProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.server.session.SessionHandler;

public class ExampleApplication extends Application<ExampleConfiguration> {
    public static void main(String[] args) throws Exception {
        new ExampleApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard-flash";
    }

    @Override
    public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(ExampleConfiguration configuration, Environment environment) {
        final UserResource userResource = new UserResource();

        environment.jersey().register(userResource);

        environment.jersey().register(FlashProvider.class);
        environment.servlets().setSessionHandler(new SessionHandler());
    }
}