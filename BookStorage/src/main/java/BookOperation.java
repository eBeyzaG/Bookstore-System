import model.Book;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Path("/books")
public class BookOperation {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "mod", "user"})
    public List<Book> getBooks(){
        return Book.bookList;
    }

    @POST
    @RolesAllowed({"admin","mod", "user"})
    public Response addBook(Book book){
        Book.bookList.add(book);
        FileUtils.writeBookToFile();
        return Response.status(Response.Status.OK)
                .build();
    }

    @DELETE
    @RolesAllowed({"admin", "mod"})
    public Response deleteBookWithID(int bookID){

        boolean found = false;

        for(int i = 0; i < Book.bookList.size(); i++){
            if(Book.bookList.get(i).getBookID() == bookID){
                Book.bookList.remove(Book.bookList.get(i));
                found = true;
                FileUtils.writeBookToFile();
            }

        }
        if(found){
            return Response.status(Response.Status.OK)
                    .entity("Successfully deleted")
                    .build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No book with given ID")
                    .build();
        }

    }



}
