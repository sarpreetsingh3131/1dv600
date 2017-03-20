package useCases;

import lnu.resources.GetBookResource;
import org.junit.Test;
import org.junit.Before;
import org.junit.ClassRule;
import static org.junit.Assert.*;
import io.dropwizard.testing.junit.ResourceTestRule;
import static org.mockito.Mockito.*;
import java.io.IOException;
import javax.ws.rs.NotFoundException;
import lnu.dao.*;
import lnu.models.*;

public class ViewABookTest {

    /* Mock the dependencies */
    private static booksDAO mockedDAO = mock(booksDAO.class);
    private catalog mockedCatalog = mock(catalog.class);
    private static GetBookResource sut = new GetBookResource(mockedDAO);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(sut).build();

    @Before
    public void setUp() throws Exception {
        when(mockedDAO.XMLToCatalog()).thenReturn(mockedCatalog);
    }

    @Test
    public void shouldViewABook() throws IOException {
        String JSON = "{\"id\":\"1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"price\":\"100\",\"genre\":\"Genre 1\",\"publishDate\":\"2012-12-12\",\"description\":\"Description 1\"}";

        when(mockedCatalog.searchBookById("1")).thenReturn(JSON);

        assertEquals(JSON, resources.client().target("/books/1").request().get(String.class));
        assertEquals(200, resources.client().target("/books/1").request().get().getStatus());
    }

    @Test
    public void shouldNotViewABook() throws Exception {
        when(mockedCatalog.searchBookById("100")).thenThrow(new NotFoundException());

        assertEquals(404, resources.client().target("/books/100").request().get().getStatus());
    }
}