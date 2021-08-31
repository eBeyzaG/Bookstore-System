package client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;

@RegisterRestClient(baseUri = "https://localhost:44301/storageLog")
@RequestScoped
@Path("/logs")
public interface StrgClient extends AutoCloseable{

    @GET
    String getHistory(@HeaderParam("Authorization") String auth);

    @POST
    void addNewLog(@HeaderParam("Authorization") String auth, String log);
}
