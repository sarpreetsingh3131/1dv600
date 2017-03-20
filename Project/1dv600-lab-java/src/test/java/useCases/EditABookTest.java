package useCases;

import lnu.resources.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.ClassRule;
import static org.junit.Assert.*;
import io.dropwizard.testing.junit.ResourceTestRule;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import static org.mockito.Mockito.*;
import lnu.dao.*;
import lnu.models.*;

public class EditABookTest {

    /* Mock the dependencies */
    private static booksDAO mockedDAO = mock(booksDAO.class);
    private catalog mockedCatalog = mock(catalog.class);
    private static EditBookResource sut = new EditBookResource(mockedDAO);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(sut).build();

    @Before
    public void setUp() throws Exception {
        when(mockedDAO.XMLToCatalog()).thenReturn(mockedCatalog);
    }

    @Test
    public void shouldEditABook() throws Exception {
        String body = "{\"id\":\"11\",\"title\":\"Title\",\"author\":\"Author\",\"price\":\"100\",\"genre\":\"Genre\",\"publishDate\":\"2008-05-01\",\"description\":\"Description\"}";

        doNothing().when(mockedDAO).catalogToXML(mockedCatalog);
        doNothing().when(mockedCatalog).updateBook("11", body);
        doNothing().when(mockedDAO).catalogToXML(mockedCatalog);

        assertEquals(200, resources.client().target("/books/11").request()
                .post(Entity.entity(body, MediaType.APPLICATION_JSON)).getStatus());

        verify(mockedCatalog, times(1)).updateBook("11", body);
        verify(mockedDAO, atLeast(1)).catalogToXML(mockedCatalog);
        verify(mockedDAO, atLeast(1)).XMLToCatalog();
    }

    @Test
    public void shouldNotEditABookWhenRequestBodyIsInvalid() throws Exception {
        String body = "{\"id\":\"11\",\"bookTitle\":\"Title\",\"author\":\"Author\",\"price\":\"100\",\"genre\":\"Genre\",\"publishDate\":\"2008-05-01\",\"description\":\"Description\"}";

        doThrow(new NotFoundException()).when(mockedCatalog).updateBook("11", body);

        assertEquals(404, resources.client().target("/books/11").request()
                .post(Entity.entity(body, MediaType.APPLICATION_JSON)).getStatus());
    }

    @Test
    public void shouldNotEditABook() throws Exception {
        String body = "{\"id\":\"100\",\"title\":\"Title\",\"author\":\"Author\",\"price\":\"100\",\"genre\":\"Genre\",\"publishDate\":\"2008-05-01\",\"description\":\"Description\"}";

        doThrow(new NotFoundException()).when(mockedCatalog).updateBook("100", body);

        assertEquals(404, resources.client().target("/books/100").request()
                .post(Entity.entity(body, MediaType.APPLICATION_JSON)).getStatus());

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(1)).updateBook("100", body);
    }

}