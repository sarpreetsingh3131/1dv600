package useCases;

import lnu.resources.GetBooksResource;
import org.junit.Test;
import org.junit.Before;
import org.junit.ClassRule;
import static org.junit.Assert.*;
import io.dropwizard.testing.junit.ResourceTestRule;
import lnu.dao.*;
import lnu.models.*;
import static org.mockito.Mockito.*;
import javax.ws.rs.NotFoundException;

public class SearchBooksTest {

    /* Mock the dependencies */
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
    public void shouldSearchBooks() throws Exception {
        String JSON = "[{\"id\":\"23\",\"title\":\"Title\",\"author\":\"Author\",\"price\":\"100\",\"genre\":\"Genre\",\"publishDate\":\"2008-05-01\",\"description\":\"Description\"}, {\"id\":\"24\",\"title\":\"Title\",\"author\":\"Author\",\"price\":\"100\",\"genre\":\"Genre\",\"publishDate\":\"2008-05-01\",\"description\":\"Description\"}]";

        when(mockedCatalog.searchByAuthorOrTitle("title")).thenReturn(JSON);

        assertEquals(JSON, resources.client().target("/books/?title=title").request().get(String.class));
        assertEquals(200, resources.client().target("/books/?title=title").request().get().getStatus());

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(2)).searchByAuthorOrTitle("title");
    }

    @Test
    public void shouldGetNoSearchResult() throws Exception {
        when(mockedCatalog.searchByAuthorOrTitle("abc")).thenThrow(new NotFoundException());

        assertEquals(404, resources.client().target("/books/?title=abc").request().get().getStatus());

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(1)).searchByAuthorOrTitle("abc");
    }
}
