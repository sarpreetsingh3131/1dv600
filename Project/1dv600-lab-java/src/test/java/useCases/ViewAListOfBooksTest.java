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
import java.io.IOException;

public class ViewAListOfBooksTest {

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
    public void shouldViewAListOfBooks() throws IOException {
        String JSON = "[{\"id\":\"1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"price\":\"100\",\"genre\":\"Genre 1\",\"publishDate\":\"2012-12-12\",\"description\":\"Description 1\"}]";

        when(mockedCatalog.toJSON()).thenReturn(JSON);

        assertEquals(JSON, resources.client().target("/books").request().get(String.class));
        assertEquals(200, resources.client().target("/books").request().get().getStatus());
    }

    @Test
    public void shouldViewAnEmptyListOfBooks() throws IOException {
        String JSON = "[]";

        when(mockedCatalog.toJSON()).thenReturn(JSON);

        assertEquals(JSON, resources.client().target("/books").request().get(String.class));
        assertEquals(200, resources.client().target("/books").request().get().getStatus());
    }
}