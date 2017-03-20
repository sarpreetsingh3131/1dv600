package api;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.mockito.Mockito.*;
import java.io.IOException;
import io.dropwizard.testing.junit.ResourceTestRule;
import lnu.dao.booksDAO;
import lnu.models.catalog;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import lnu.resources.*;
import static org.fest.assertions.Assertions.assertThat;

public class AddBookResourceTest {

    //Mock the dependencies
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
    public void shouldAddABookAndReturnResponse200OK() throws Exception {
        String JSON = "{\"title\": \"Title 1\",\"author\": \"Author 1\",\"price\": \"100\",\"genre\": \"Genre 1\",\"publishDate\": \"2012-12-12\",\"description\": \"Description 1\"}";

        doNothing().when(mockedCatalog).addBook(JSON);
        doNothing().when(mockedDAO).catalogToXML(mockedCatalog);

        assertThat(resources.client().target("/books").request().put(Entity.entity(JSON, MediaType.APPLICATION_JSON))
                .getStatus()).isEqualTo(200);

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(1)).addBook(JSON);
        verify(mockedDAO, atLeast(1)).catalogToXML(mockedCatalog);
    }

    @Test
    public void shouldReturnResponse404WhenRequestBodyIsInvalid() throws Exception {
        String JSON = "{\"BookTitle\": \"Title 1\",\"BookAuthor\": \"Author 1\",\"BookPrice\": \"100\",\"BookGenre\": \"Genre 1\",\"BookPublishDate\": \"2012-12-12\",\"BookDescription\": \"Description 1\"}";

        doThrow(new IOException()).when(mockedCatalog).addBook(JSON);

        assertThat(resources.client().target("/books").request().put(Entity.entity(JSON, MediaType.APPLICATION_JSON))
                .getStatus()).isEqualTo(404);

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(1)).addBook(JSON);
    }
}