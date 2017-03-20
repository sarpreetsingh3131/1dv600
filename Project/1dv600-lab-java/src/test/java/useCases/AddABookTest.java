package useCases;

import lnu.resources.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.ClassRule;
import static org.junit.Assert.*;
import io.dropwizard.testing.junit.ResourceTestRule;
import java.io.IOException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import static org.mockito.Mockito.*;
import lnu.dao.*;
import lnu.models.*;

public class AddABookTest {

    /* Mock the dependencies */
    private static booksDAO mockedDAO = mock(booksDAO.class);
    private catalog mockedCatalog = mock(catalog.class);
    private static AddBookResource sut = new AddBookResource(mockedDAO);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(sut).build();

    @Before
    public void setUp() throws Exception {
        when(mockedDAO.XMLToCatalog()).thenReturn(mockedCatalog);
    }

    @Test
    public void shouldAddABook() throws Exception {
        String body = "{\"id\":\"1\",\"title\":\"Title\",\"author\":\"Author\",\"price\":\"100\",\"genre\":\"Genre\",\"publishDate\":\"2008-05-01\",\"description\":\"Description\"}";

        doNothing().when(mockedCatalog).addBook(body);
        doNothing().when(mockedDAO).catalogToXML(mockedCatalog);

        assertEquals(200, resources.client().target("/books").request()
                .put(Entity.entity(body, MediaType.APPLICATION_JSON)).getStatus());

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(1)).addBook(body);
        verify(mockedDAO, atLeast(1)).catalogToXML(mockedCatalog);
    }

    @Test
    public void shouldNotAddABook() throws Exception {
        doThrow(new IOException()).when(mockedCatalog).addBook("");

        assertEquals(404, resources.client().target("/books").request()
                .put(Entity.entity("", MediaType.APPLICATION_JSON)).getStatus());

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(1)).addBook("");
    }
}