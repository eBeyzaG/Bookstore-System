import model.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookStorageEndpointIT {
    static String authHeaderAdmin;
    static String authHeaderMod;
    static String authHeaderUser;
    static String urlBooks;

    @BeforeAll
    private static void setup() throws Exception{
        urlBooks = "http://" + System.getProperty("hostname")
                + ":" + System.getProperty("http.port") + "/bookStorage/books";

        authHeaderAdmin = "Bearer " + new JwtUtils().createAdminJwt("testUser");
        authHeaderMod = "Bearer " + new JwtUtils().createModJwt("testUser");
        authHeaderUser = "Bearer " + new JwtUtils().createUserJwt("testUser");
    }

    @Test
    public void testGetMethod(){
        Response response = makeGetRequest(urlBooks, authHeaderAdmin);
        assertEquals(200, response.getStatus(),
                "Incorrect response code from " + urlBooks + " GET method for Admin.");

        response = makeGetRequest(urlBooks, authHeaderMod);
        assertEquals(200, response.getStatus(),
                "Incorrect response code from " + urlBooks + " GET method for Mod.");

        response = makeGetRequest(urlBooks, authHeaderUser);
        assertEquals(200, response.getStatus(),
                "Incorrect response code from " + urlBooks + " GET method for User.");

        response.close();
    }

    @Test
    public void testPostMethod(){
        Response res = makePostRequest(urlBooks, authHeaderAdmin);
        assertEquals(200, res.getStatus(),
                "Incorrect response code from " + urlBooks + " POST method for Admin.");

        res = makePostRequest(urlBooks, authHeaderMod);
        assertEquals(200, res.getStatus(),
                "Incorrect response code from " + urlBooks + " POST method for Mod.");
        res = makePostRequest(urlBooks, authHeaderUser);
        assertEquals(200, res.getStatus(),
                "Incorrect response code from " + urlBooks + " POST method for User.");

        res.close();
    }

    @Test
    public void testDeleteRequest(){
        Response res = makeDeleteRequest(urlBooks, authHeaderAdmin);
        assertEquals(200, res.getStatus(),
                "Incorrect response code from " + urlBooks + " DELETE method for Admin.");

        res = makeDeleteRequest(urlBooks, authHeaderMod);
        assertEquals(200, res.getStatus(),
                "Incorrect response code from " + urlBooks + " DELETE method for Mod.");

        res = makeDeleteRequest(urlBooks, authHeaderUser);
        assertEquals(403, res.getStatus(),
                "Incorrect response code from " + urlBooks + " DELETE method for User.");

    }

    private Response makeGetRequest(String url, String authHeader){
        Client client = ClientBuilder.newClient();
        Invocation.Builder builder = client.target(url).request();
        builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        if(authHeader != null){
            builder.header(HttpHeaders.AUTHORIZATION, authHeader);
        }
        Response res = builder.get();
        return res;
    }
    private Response makePostRequest(String url, String authHeader){
        Client client = ClientBuilder.newClient();
        Invocation.Builder builder = client
                .target(url)
                .request();
        builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);


        if (authHeader != null){
            builder.header(HttpHeaders.AUTHORIZATION, authHeader);
        }
        Response res = builder.accept(MediaType.APPLICATION_JSON).post(Entity.entity("null", MediaType.APPLICATION_JSON));
        return res;

    }
    private Response makeDeleteRequest(String url, String authHeader){
        Client client = ClientBuilder.newClient();
        Invocation.Builder builder = client
                .target(url)
                .request();
        builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        if(authHeader != null){
            builder.header(HttpHeaders.AUTHORIZATION, authHeader);
        }
        Response res = builder.accept(MediaType.APPLICATION_JSON).method("DELETE", Entity.entity("99", MediaType.APPLICATION_JSON));//Entity.entity("{\"bookID\":99}", MediaType.APPLICATION_JSON));
        System.out.println(res.readEntity(String.class));
        return res;
    }


}
