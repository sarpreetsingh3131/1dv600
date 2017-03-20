package api;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.mockito.Mockito.*;
import javax.ws.rs.NotFoundException;
import io.dropwizard.testing.junit.ResourceTestRule;
import lnu.dao.booksDAO;
import lnu.models.catalog;
import lnu.resources.*;
import static org.junit.Assert.*;

public class GetBookResourceTest {

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
    public void shouldReturnJSONBookAndResponse200OK() throws Exception {
        String id = "\"1\"";
        String JSON = "{\"id\": " + id
                + ",\"title\": \"Title 1\",\"author\": \"Author 1\",\"price\": \"100\",\"genre\": \"Genre 1\",\"publishDate\": \"2012-12-12\",\"description\": \"Description 1\"}";

        when(mockedCatalog.searchBookById(id)).thenReturn(JSON);

        assertEquals(JSON, resources.client().target("/books/" + id).request().get(String.class));
        assertEquals(200, resources.client().target("/books/" + id).request().get().getStatus());

        verify(mockedDAO, atLeast(2)).XMLToCatalog();
        verify(mockedCatalog, times(2)).searchBookById(id);
    }

    @Test
    public void shouldReturnResponse404NotFoundWhenBookIsNotFound() throws Exception {
        String id = "100";

        when(mockedCatalog.searchBookById(id)).thenThrow(new NotFoundException());

        assertEquals(404, resources.client().target("/books/" + id).request().get().getStatus());

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(1)).searchBookById(id);
    }
}