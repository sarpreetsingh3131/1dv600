package lnu.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lnu.dao.*;

@Produces(MediaType.APPLICATION_JSON)
@Path("/books")
public class GetBookResource {

    private booksDAO dao;

    public GetBookResource() {
        dao = new booksDAO();
    }

    /**
     * For testing purpose
     */
    public GetBookResource(booksDAO dao) {
        this.dao = dao;
    }

    /**
     * Get book_id and hand it to catalog, if book is found return 200 else 404
     */
    @GET
    @Path("{book_id}")
    public Response getBook(@PathParam("book_id") String book_id) {
        try {
            return Response.ok(dao.XMLToCatalog().searchBookById(book_id), MediaType.APPLICATION_JSON).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}