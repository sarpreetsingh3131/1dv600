package lnu.resources;

import lnu.models.*;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lnu.dao.*;

@Produces(MediaType.APPLICATION_JSON)
@Path("/books")
public class EditBookResource {

    private booksDAO dao;

    /**
     * For testing purpose
     */
    public EditBookResource() {
        dao = new booksDAO();
    }

    public EditBookResource(booksDAO dao) {
        this.dao = dao;
    }

    /**
     * Get JSON string and book_id and hand it to catalog, if updating is successfull rewrite XML file return 200 else 404
     */
    @POST
    @Path("{book_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("book_id") String book_id, String jsonBook) {
        try {
            catalog catalog = dao.XMLToCatalog();
            catalog.updateBook(book_id, jsonBook);
            dao.catalogToXML(catalog);
            return Response.ok().build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}