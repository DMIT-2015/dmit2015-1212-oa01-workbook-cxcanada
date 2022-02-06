package dmit2015.resource;

import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.InputStream;

/**
 * Curl command to test webapi
 * -k: it is required if we are using https, certificate is not signed by a third party
 * -i: we want the information back on the header (or somewhere else)..
 * -X: optional for GET, what type of request you are sending to server
 *
 * curl -k -i -X GET http://localhost:8080/dmit2015-1212-jaxrs-demo/webapi/helloworld -H 'Accept: text/plain'
 * curl -k -i -X GET http://localhost:8080/dmit2015-1212-jaxrs-demo/webapi/helloworld -H 'Accept: text/html'
 * curl -k -i -X GET http://localhost:8080/dmit2015-1212-jaxrs-demo/webapi/helloworld -H 'Accept: application/json'
 *
 * curl -k -i -X GET http://localhost:8080/dmit2015-1212-jaxrs-demo/webapi/helloworld/image
 */

// RequestScoped indicates that this resource is created for one request. After the response comes back from the server,
// this object is disposed.
@RequestScoped
@Path("/helloworld")
public class HelloWorldResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN) //@Produces("text/plain")  // content type
    // This sends back HTTP request.
    public Response helloWorldText() {
        String message = "Hello World from JAX-RS!";
        return Response.ok(message).build();
    }

    // This is the default content type.
    @GET
    @Produces(MediaType.TEXT_HTML) // "text/html"
    public Response helloWorldHtml() {
        String message = "<p>Hello World from <strong>JAX-RS</strong>";
        return Response.ok(message).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)   // "application/json"
    public Response helloWorldJson() {
        String message = "{\"message\":\"Hello World from JAX-RS\"}";
        return Response.ok(message).build();
    }

    @Path("/image")
    @GET
    @Produces("image/png")
    public Response helloImage(@Context HttpServletRequest request) {
        InputStream is = getClass().getResourceAsStream("/images/hello_world.png");
        return Response.ok(is)
                .header("Content-Disposition", "attachment; filename=hello_world.png")
                .build();

//        // This File class accesses external file with absolute address. Relative address does not work here.
//        File imageFile = new File("/home/user2015/Pictures/hello_world.png");
//        return Response
//                .ok(imageFile)
//                .header("Content-Disposition","attachment; filename=hello_world.png")
//                .build();
    }
}