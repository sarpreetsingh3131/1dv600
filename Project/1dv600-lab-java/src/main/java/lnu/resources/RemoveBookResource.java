package lnu.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lnu.dao.*;
import lnu.models.*;

@Produces(MediaType.APPLICATION_JSON)
@Path("/books")
public class RemoveBookResource {

    private booksDAO dao;

    public RemoveBookResource() {
        dao = new booksDAO();
    }

    /**
     * For testing purpose
     */
    public RemoveBookResource(booksDAO dao) {
        this.dao = dao;
    }

    /**
     * Get book_id and hand it to catalog, if book is found rewrite XML file and return 200 else 404
     */
    @DELETE
    @Path("{book_id}")
    public Response deleteBook(@PathParam("book_id") String book_id) {
        try {
            catalog catalog = dao.XMLToCatalog();
            catalog.deleteBook(book_id);
            dao.catalogToXML(catalog);
            return Response.ok().build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}