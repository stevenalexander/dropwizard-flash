# Dropwizard Flash

Sample dropwizard application for displaying flash messages between requests using the new Dropwizard 7.1 flash
functionality. Uses Mustache for views and Gradle for builds.

## Run

```
./go
```

## Test

```
gradle test
```

## Build standalone jar

```
gradle oneJar
```

## Details

Created this as an example as I saw the flash/session function was added in v7 but the Dropwizard documentation doesn't
give any details about how to use it, just a small line in the release notes.

Turns out it's quite easy, just:

* Add the Dropwizard FlashProvider and the Jersey SessionHandler in your Application run method

    ```
    public class ExampleApplication extends Application<ExampleConfiguration> {
        ...
        @Override
        public void run(ExampleConfiguration configuration, Environment environment) {
            final UserResource userResource = new UserResource();

            environment.jersey().register(userResource);

            environment.jersey().register(FlashProvider.class);
            environment.servlets().setSessionHandler(new SessionHandler());
        }
    }
    ```

* Inject the flash object into your resource methods to get/set

    ```
    public class UserResource {

        @GET
        public UsersView getAll(@Session Flash<String> message){
            String flashMessage = message.get().or("");
            ...
        }

        @POST
        @Path("/flash")
        public  UsersView flash(@Session Flash<String> message) throws URISyntaxException {
            message.set("Flash aaahhhh! He saved everyone of us!");
            ...
        }
    }
    ```

If you get the error below it's because you forgot to add the FlashProvider/SessionHandler:

```
com.sun.jersey.spi.container.ContainerRequest: A message body reader for Java class io.dropwizard.jersey.sessions.Flash, and Java type io.dropwizard.jersey.sessions.Flash<java.lang.String>, and MIME media type application/octet-stream was not found.
```