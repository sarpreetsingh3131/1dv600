package api;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.mockito.Mockito.*;
import javax.ws.rs.NotFoundException;
import io.dropwizard.testing.junit.ResourceTestRule;
import lnu.dao.booksDAO;
import lnu.models.catalog;
import lnu.resources.GetBooksResource;
import static org.junit.Assert.*;

public class GetBooksResourceTest {

    //Mock the dependencies
    private static booksDAO mockedDAO = mock(booksDAO.class);
    private catalog mockedCatalog = mock(catalog.class);
    private static GetBooksResource sut = new GetBooksResource(mockedDAO);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(sut).build();

    @Before
    public void setUp() throws Exception {
        when(mockedDAO.XMLToCatalog()).thenReturn(mockedCatalog);
    }

    @Test
    public void shouldReturnAllBooksInJSONArrayAndResponse200OK() throws Exception {
        String JSON = "[{\"id\": \"1\",\"title\": \"Title 1\",\"author\": \"Author 1\",\"price\": \"100\",\"genre\": \"Genre 1\",\"publishDate\": \"2012-12-12\",\"description\": \"Description 1\"}]";

        when(mockedCatalog.toJSON()).thenReturn(JSON);

        assertEquals(JSON, resources.client().target("/books").request().get(String.class));
        assertEquals(200, resources.client().target("/books").request().get().getStatus());

        verify(mockedDAO, atLeast(2)).XMLToCatalog();
        verify(mockedCatalog, times(2)).toJSON();
    }

    @Test
    public void shouldReturnAnEmptyJSONArrayWithResponse200OK() throws Exception {
        String JSON = "[]";

        when(mockedCatalog.toJSON()).thenReturn(JSON);

        assertEquals(JSON, resources.client().target("/books").request().get(String.class));
        assertEquals(200, resources.client().target("/books").request().get().getStatus());

        verify(mockedDAO, atLeast(2)).XMLToCatalog();
        verify(mockedCatalog, times(2)).toJSON();
    }

    @Test
    public void shouldReturnBooksAccordingToTitleInJSONArrayAndResponse200OK() throws Exception {
        String title = "Title";
        String JSON = "[{\"id\": \"1\",\"title\": " + "\"" + title + "\""
                + ",\"author\": \"Author 1\",\"price\": \"100\",\"genre\": \"Genre 1\",\"publishDate\": \"2012-12-12\",\"description\": \"Description 1\"}]";

        when(mockedCatalog.searchByAuthorOrTitle(title)).thenReturn(JSON);

        assertEquals(JSON, resources.client().target("/books?title=" + title).request().get(String.class));
        assertEquals(200, resources.client().target("/books?title=" + title).request().get().getStatus());

        verify(mockedDAO, atLeast(2)).XMLToCatalog();
        verify(mockedCatalog, times(2)).searchByAuthorOrTitle(title);
    }

    @Test
    public void shouldReturnBooksAccordingToAuthorInJSONArrayAndResponse200OK() throws Exception {
        String author = "Author";
        String JSON = "[{\"id\": \"1\",\"title\": \"Title 1\",\"author\": " + "\"" + author + "\""
                + ",\"price\": \"100\",\"genre\": \"Genre 1\",\"publishDate\": \"2012-12-12\",\"description\": \"Description 1\"}]";

        when(mockedCatalog.searchByAuthorOrTitle(author)).thenReturn(JSON);

        assertEquals(JSON, resources.client().target("/books?title=" + author).request().get(String.class));
        assertEquals(200, resources.client().target("/books?title=" + author).request().get().getStatus());

        verify(mockedDAO, atLeast(2)).XMLToCatalog();
        verify(mockedCatalog, times(2)).searchByAuthorOrTitle(author);
    }

    @Test
    public void shouldReturnResponse404NotFoundWhenSearchedBookByTitleIsNotFound() throws Exception {
        String title = "1DV600";

        when(mockedCatalog.searchByAuthorOrTitle(title)).thenThrow(new NotFoundException());

        assertEquals(404, resources.client().target("/books?title=" + title).request().get().getStatus());

        verify(mockedDAO, atLeast(2)).XMLToCatalog();
        verify(mockedCatalog, times(1)).searchByAuthorOrTitle(title);
    }

    @Test
    public void shouldReturnResponse404NotFoundWhenSearchedBookByAuthorIsNotFound() throws Exception {
        String author = "1DV600";

        when(mockedCatalog.searchByAuthorOrTitle(author)).thenThrow(new NotFoundException());

        assertEquals(404, resources.client().target("/books?title=" + author).request().get().getStatus());

        verify(mockedDAO, atLeast(2)).XMLToCatalog();
        verify(mockedCatalog, times(1)).searchByAuthorOrTitle(author);
    }
}