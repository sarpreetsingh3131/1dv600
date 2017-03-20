package lnu.resources;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lnu.dao.*;
import lnu.models.*;

@Consumes(MediaType.APPLICATION_JSON)
@Path("/books")
public class AddBookResource {

    private booksDAO dao;

    public AddBookResource() {
        dao = new booksDAO();
    }

    /**
     * For testing purpose
     */
    public AddBookResource(booksDAO dao) {
        this.dao = dao;
    }

    /**
     * Get JSON string and hand it to catalog, if adding is successfull rewrite XML file return 200 else 404
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(String jsonBook) {
        try {
            catalog catalog = dao.XMLToCatalog();
            catalog.addBook(jsonBook);
            dao.catalogToXML(catalog);
            return Response.ok().build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}