import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@RequestScoped
@Path("/logs")
public class StorageHistory {
    @GET
    @RolesAllowed({"admin"})
    public String getHistory(){
        return FileUtils.readLogFile();
    }
    @POST
    @RolesAllowed({"admin", "mod", "user"})
    public void addNewLog(String log){
        FileUtils.addLog(log);
    }

}
