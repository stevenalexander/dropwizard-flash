package com.example.resources;

import com.example.core.User;
import com.example.views.UsersView;
import io.dropwizard.jersey.sessions.Flash;
import io.dropwizard.jersey.sessions.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

@Path("/user")
@Produces(MediaType.TEXT_HTML)
public class UserResource {

    @GET
    public UsersView getAll(@Session Flash<String> message){

        List<User> users = new LinkedList<>();
        users.add(
            new User()
                .setUsername("user1 " + message.get().or(""))
                .setDisplayName("User 1")
                .setDisplayRole("Admin")
        );
        users.add(
            new User()
                .setUsername("user2")
                .setDisplayName("User 2")
                .setDisplayRole("DBA")
        );

        return new UsersView(users);
    }

    @POST
    @Path("/flash")
    public  UsersView flash(@Session Flash<String> message) throws URISyntaxException {

        if (!message.get().isPresent()) {
            message.set("Flash aaahhhh! He saved everyone of us!");
        }

        URI uri = new URI("/user");
        Response response = Response.seeOther(uri).build();
        throw new WebApplicationException(response);
    }
}
