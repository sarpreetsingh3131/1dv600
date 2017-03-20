package useCases;

import lnu.resources.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.ClassRule;
import static org.junit.Assert.*;
import io.dropwizard.testing.junit.ResourceTestRule;
import javax.ws.rs.NotFoundException;
import static org.mockito.Mockito.*;
import lnu.dao.*;
import lnu.models.*;

public class DeleteABookTest {

    /* Mock the dependencies */
    private static booksDAO mockedDAO = mock(booksDAO.class);
    private catalog mockedCatalog = mock(catalog.class);
    private static RemoveBookResource sut = new RemoveBookResource(mockedDAO);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(sut).build();

    @Before
    public void setUp() throws Exception {
        when(mockedDAO.XMLToCatalog()).thenReturn(mockedCatalog);
    }

    @Test
    public void shouldDeleteABook() throws Exception {
        doNothing().when(mockedCatalog).deleteBook("1");
        doNothing().when(mockedDAO).catalogToXML(mockedCatalog);

        assertEquals(200, resources.client().target("/books/1").request().delete().getStatus());

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(1)).deleteBook("1");
        verify(mockedDAO, atLeast(1)).catalogToXML(mockedCatalog);
    }

    @Test
    public void shouldNotDeleteABook() throws Exception {
        doThrow(new NotFoundException()).when(mockedCatalog).deleteBook("100");

        assertEquals(404, resources.client().target("/books/100").request().delete().getStatus());

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(1)).deleteBook("100");
    }
}