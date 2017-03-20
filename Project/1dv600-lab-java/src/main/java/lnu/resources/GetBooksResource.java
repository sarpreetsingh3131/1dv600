package lnu.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lnu.dao.*;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;

@Produces(MediaType.APPLICATION_JSON)
@Path("/books")
public class GetBooksResource {

	private booksDAO dao;

	public GetBooksResource() {
		dao = new booksDAO();
	}

	/**
	 * For testing purpose
	 */
	public GetBooksResource(booksDAO dao) {
		this.dao = dao;
	}

	/**
	 * Get String from frontend and check its value. By default its 'All' means all the books else searched book/s only. Return 404 if searched book/s not found else 200
	 */
	@GET
	public Response getBooks(@DefaultValue("All") @QueryParam("title") String titleOrAuthor) {
		try {
			switch (titleOrAuthor) {
			case "All":
				return Response.ok(dao.XMLToCatalog().toJSON(), MediaType.APPLICATION_JSON).build();
			default:
				return Response.ok(dao.XMLToCatalog().searchByAuthorOrTitle(titleOrAuthor), MediaType.APPLICATION_JSON)
						.build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}