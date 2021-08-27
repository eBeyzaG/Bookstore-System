import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
@Named
public class ApplicationBean {

    @Inject
    @RestClient
    private AppClient restClient;

    public String getJwt(){
        return "Bearer " + LoginBean.getJwtToken();
    }

    public String getBooks(){

        String books;

        try {
            books = restClient.getBooks(getJwt()).toString();
        }catch (Exception e){
            return e.toString();
        }

        return books;

    }

    public void addBook(String  bookId,String bookName, String authorName, String publisherName){
       try{
           restClient.addBook(getJwt(),
                new Book(Integer.valueOf(bookId), bookName, authorName, publisherName));}
       catch (Exception e){
           System.out.println(e);
       }
    }


}
