import client.AppClient;
import client.StrgClient;
import model.Book;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Named
public class ApplicationBean {

    @Inject
    @RestClient
    private AppClient restClient;

    @Inject
    @RestClient
    private StrgClient strgClient;

    private String deleteMessage = "";

    public void setDeleteMessage(String deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public String getDeleteMessage() {
        return deleteMessage;
    }

    public String getJwt(){
        return "Bearer " + LoginBean.getJwtToken();
    }

    public String getRoles(){
       return LoginBean.getRoles(LoginBean.getRequest()).toString();
    }

    public String getUsername(){return LoginBean.getRequest().getRemoteUser(); }

    public List<Book> getBooks(){

        List<Book> books;

        try {
            books = restClient.getBooks(getJwt());
        }catch (Exception e){
            return null;
        }

        return books;

    }

    public String addBook(String  bookId,String bookName, String authorName, String publisherName){
       try{
           restClient.addBook(getJwt(),
                   new Book(Integer.valueOf(bookId), bookName, authorName, publisherName));

           Date d = new Date();

           String log = getUsername() + " with roles " + getRoles() + " added the book with ID " + bookId + " at " + d + "\n";
           this.addLog(log);
           return "menu.jsf?faces-redirect=true";

       }
       catch (Exception e){
           System.out.println(e);
           return "application.jsf?faces-redirect=true";
       }
    }

    public String getLogHistory(){
        String str;
        try{
            str = strgClient.getHistory(getJwt());
        }catch (Exception e){
            str = "No authorization to see log history";
        }
        return str;

    }

    public void addLog(String log){
        strgClient.addNewLog(getJwt(), log);
    }

    public void deleteBookwithID(String id){
        int bookId = Integer.valueOf(id);

        Response res = null;
        try{
          res = restClient.deleteBookWithID(getJwt(), bookId);

          Date d = new Date();
          String log = getUsername() + " with roles " + getRoles() + " deleted the book with ID " + bookId + " at " + d +"\n";
          this.addLog(log);

          setDeleteMessage(res.readEntity(String.class));

        }
        catch(Exception e){
            setDeleteMessage(e.getMessage());
        }



    }

    public String backFromDelete(){
        setDeleteMessage("");
        return "menu.jsf?faces-redirect=true";
    }
}
