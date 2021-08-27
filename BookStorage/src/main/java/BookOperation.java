import model.Book;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/books")
public class BookOperation {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "mod", "user"})
    public List<Book> getBooks(){
        return Book.bookList;
    }

    @POST
    @RolesAllowed({"admin, mod"})
    public Response addBook(Book book){
        Book.bookList.add(book);
        System.out.println(book.getBookName());
        return Response.status(Response.Status.OK)
                .build();
    }



}
