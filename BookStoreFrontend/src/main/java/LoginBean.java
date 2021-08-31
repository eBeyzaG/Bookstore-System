import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.ibm.websphere.security.jwt.Claims;
import com.ibm.websphere.security.jwt.JwtBuilder;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
@Named
public class LoginBean {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static HttpSession getSession(){
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }
    public static HttpServletRequest getRequest(){
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }
    public static String getJwtToken(){
        return (String) getSession().getAttribute("jwt");
    }

    public String doLogin() throws Exception{

        HttpServletRequest req = getRequest();

        try {
            req.logout();
            req.login(username, password);
        }catch (ServletException ex){
            return "error.jsf";
        }

        String remoteUsr = req.getRemoteUser();
        if(remoteUsr != null && remoteUsr.equals(username)){
            HttpSession session = req.getSession();
            if(session == null){
                System.out.println("Session timed out.");
            }else{
                session.setAttribute("jwt", buildJwt(username, getRoles(req)));
            }
        }else{
            System.out.println("An error occurred while updating JWT in session.");
        }

        return "menu.xhtml?faces-redirect=true";


    }

    private String buildJwt(String username, Set<String> roles) throws Exception{
        return JwtBuilder.create("jwtBookStoreBuilder")
                .claim(Claims.SUBJECT, username)
                .claim("upn", username)
                .claim("groups", roles.toArray(new String[roles.size()]))
                .buildJwt()
                .compact();
    }

    public static Set<String> getRoles(HttpServletRequest request){

        Set<String> roles = new HashSet<>();
        System.out.println("Roles: ");
        if (request.isUserInRole("admin")){
            System.out.println("admin");
            roles.add("admin");
        }
        if (request.isUserInRole("user")){
            System.out.println("user");
            roles.add("user");
        }
        if(request.isUserInRole("mod")){
            System.out.println("mod");
            roles.add("mod");
        }


        return roles;
    }
}
