import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/logs")
@RequestScoped
public class Log {

    @GET
    @RolesAllowed({"admin"})
    public String getHistory(){
        return Utils.readLogFile();
    }

    @PUT
    @RolesAllowed({"admin", "mod", "user"})
    public void addNewLog(String log){
        // TO-DO
        Utils.addLog(log);

    }

}
