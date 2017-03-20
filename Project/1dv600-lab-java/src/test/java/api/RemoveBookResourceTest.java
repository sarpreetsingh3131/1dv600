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

public class RemoveBookResourceTest {

    //Mock the dependencies
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
    public void shouldDeleteTheBookAndReturnResponse200OK() throws Exception {
        String id = "1";

        doNothing().when(mockedCatalog).deleteBook(id);
        doNothing().when(mockedDAO).catalogToXML(mockedCatalog);

        assertEquals(200, resources.client().target("/books/" + id).request().delete().getStatus());

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(1)).deleteBook(id);
        verify(mockedDAO, atLeast(1)).catalogToXML(mockedCatalog);
    }

    @Test
    public void shouldReturnResponse404IfBookToBeDeletedIsNotFound() throws Exception {
        String id = "100";

        doThrow(new NotFoundException()).when(mockedCatalog).deleteBook(id);

        assertEquals(404, resources.client().target("/books/" + id).request().delete().getStatus());

        verify(mockedDAO, atLeast(1)).XMLToCatalog();
        verify(mockedCatalog, times(1)).deleteBook(id);
    }
}