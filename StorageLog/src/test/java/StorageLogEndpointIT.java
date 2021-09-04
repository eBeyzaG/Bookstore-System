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

public class StorageLogEndpointIT {

    static String authHeaderAdmin;
    static String authHeaderMod;
    static String authHeaderUser;
    static String urlLogs;

    @BeforeAll
    private static void setup() throws Exception{
        urlLogs = "http://" + System.getProperty("hostname")
                + ":" + System.getProperty("http.port") + "/storageLog/logs";

        authHeaderAdmin = "Bearer " + new JwtUtils().createAdminJwt("testUser");
        authHeaderMod = "Bearer " + new JwtUtils().createModJwt("testUser");
        authHeaderUser = "Bearer " + new JwtUtils().createUserJwt("testUser");
    }

    @Test
    public void testGetMethod(){
        Response response = makeGetRequest(urlLogs, authHeaderAdmin);
        assertEquals(200, response.getStatus(),
                "Incorrect response code from " + urlLogs + " GET method for Admin.");

        response = makeGetRequest(urlLogs, authHeaderMod);
        assertEquals(403, response.getStatus(),
                "Incorrect response code from " + urlLogs + " GET method for Mod.");

        response = makeGetRequest(urlLogs, authHeaderUser);
        assertEquals(403, response.getStatus(),
                "Incorrect response code from " + urlLogs + " GET method for User.");

        response.close();
    }


    @Test
    public void testPostMethod(){
        Response res = makePostRequest(urlLogs, authHeaderAdmin);
        assertEquals(204, res.getStatus(),
                "Incorrect response code from " + urlLogs + " POST method for Admin.");

        res = makePostRequest(urlLogs, authHeaderMod);
        assertEquals(204, res.getStatus(),
                "Incorrect response code from " + urlLogs + " POST method for Mod.");

        res = makePostRequest(urlLogs, authHeaderUser);
        assertEquals(204, res.getStatus(),
                "Incorrect response code from " + urlLogs + " POST method for User.");

        res.close();
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
        Response res = builder.post(Entity.json("Deneme"));
        return res;

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
}
