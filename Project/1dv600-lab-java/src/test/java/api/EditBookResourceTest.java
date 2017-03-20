package api;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.mockito.Mockito.*;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import io.dropwizard.testing.junit.ResourceTestRule;
import lnu.dao.booksDAO;
import lnu.models.catalog;
import lnu.resources.*;
import static org.fest.assertions.Assertions.assertThat;

public class EditBookResourceTest {

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
        public void shouldUpdateTheBookAndReturnResponse200OK() throws Exception {
                String id = "\"1\"";
                String JSON = "{\"id\": " + id
                                + ",\"title\": \"Title 1\",\"author\": \"Author 1\",\"price\": \"100\",\"genre\": \"Genre 1\",\"publishDate\": \"2012-12-12\",\"description\": \"Description 1\"}";

                doNothing().when(mockedCatalog).updateBook(id, JSON);
                doNothing().when(mockedDAO).catalogToXML(mockedCatalog);

                assertThat(resources.client().target("/books/" + id).request()
                                .post(Entity.entity(JSON, MediaType.APPLICATION_JSON)).getStatus()).isEqualTo(200);

                verify(mockedDAO, atLeast(1)).XMLToCatalog();
                verify(mockedCatalog, times(1)).updateBook(id, JSON);
                verify(mockedDAO, atLeast(1)).catalogToXML(mockedCatalog);
        }

        @Test
        public void shouldReturnResponse404NotFoundWhenBookToBeUpdatedIsNotInTheList() throws Exception {
                String id = "100";
                String JSON = "{\"id\": " + id
                                + ",\"title\": \"Title 1\",\"author\": \"Author 1\",\"price\": \"100\",\"genre\": \"Genre 1\",\"publishDate\": \"2012-12-12\",\"description\": \"Description 1\"}";

                doThrow(new NotFoundException()).when(mockedCatalog).updateBook(id, JSON);

                assertThat(resources.client().target("/books/" + id).request()
                                .post(Entity.entity(JSON, MediaType.APPLICATION_JSON)).getStatus()).isEqualTo(404);

                verify(mockedDAO, atLeast(1)).XMLToCatalog();
                verify(mockedCatalog, times(1)).updateBook(id, JSON);
        }
}