package client;

import model.Book;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RegisterRestClient(baseUri = "https://localhost:8443/bookStorage")
@Path("/books")
@RequestScoped
public interface AppClient extends AutoCloseable{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Book> getBooks(@HeaderParam("Authorization") String authHeader);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Response addBook(@HeaderParam("Authorization") String authHeader, Book book);

    @DELETE
    Response deleteBookWithID(@HeaderParam("Authorization") String authHeader, int bookID);
}
