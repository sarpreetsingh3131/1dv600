package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import lnu.models.*;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.util.ArrayList;
import io.dropwizard.jackson.Jackson;
import javax.ws.rs.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class catalogTest {

    //Mock the dependencies
    @SuppressWarnings("unchecked")
    private ArrayList<book> mockedBookList = mock(ArrayList.class);
    private book mockedBook = mock(book.class);
    private ObjectMapper mockedMapper = mock(Jackson.newObjectMapper().getClass());
    private final int BOOKLIST_SIZE = 5;
    private final String JSON = "{\"id\": \"1\",\"title\": \"Title 1\",\"author\": \"Author 1\",\"genre\": \"Genre 1\",\"price\": \"100\",\"publish_date\": \"2012-12-12\",\"description\": \"Description 1\"}";

    private catalog sut;

    @Before
    public void setUp() throws IOException {
        sut = new catalog(mockedBookList, mockedMapper);

        when(mockedBookList.size()).thenReturn(BOOKLIST_SIZE);
        when(mockedBookList.get(any(Integer.class))).thenReturn(mockedBook);
        when(mockedMapper.readValue(JSON, book.class)).thenReturn(mockedBook);
        when(mockedMapper.writeValueAsString(any())).thenReturn(JSON);
    }

    @Test
    public void shouldDeleteTheBook() {
        when(mockedBook.getId()).thenReturn("1");
        when(mockedBookList.remove(mockedBook)).thenReturn(true);

        sut.deleteBook("1");

        verify(mockedBook, times(1)).getId();
        verify(mockedBookList, times(1)).size();
        verify(mockedBookList, times(2)).get(any(Integer.class));
        verify(mockedBookList, times(1)).remove(mockedBook);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenBookToBeDeletedNotFound() {
        when(mockedBook.getId()).thenReturn("100");

        sut.deleteBook("1");

        verify(mockedBook, times(BOOKLIST_SIZE)).getId();
        verify(mockedBookList, times(1)).size();
        verify(mockedBookList, times(BOOKLIST_SIZE)).get(any(Integer.class));
    }

    @Test
    public void shouldAddTheBook() throws IOException {
        when(mockedBookList.isEmpty()).thenReturn(true);
        doNothing().when(mockedBook).setId(anyString());
        doReturn(true).when(mockedBookList).add(mockedBook);

        sut.addBook(JSON);

        verify(mockedMapper, atLeast(1)).readValue(JSON, book.class);
        verify(mockedBookList, times(1)).isEmpty();
        verify(mockedBook).setId(anyString());
        verify(mockedBookList, times(1)).add(mockedBook);
    }

    @Test(expected = IOException.class)
    public void shouldNotAddTheBookAndThrowException() throws IOException {
        when(mockedMapper.readValue(JSON, book.class)).thenThrow(new IOException());

        sut.addBook(JSON);

        verify(mockedMapper, times(2)).readValue(JSON, book.class);
    }

    @Test
    public void shouldUpdateTheBook() throws IOException {
        when(mockedBook.getId()).thenReturn("1");

        sut.updateBook("1", JSON);

        verify(mockedMapper, atLeast(1)).readValue(JSON, book.class);
        verify(mockedBookList, times(1)).size();
        verify(mockedBookList, times(2)).get(anyInt());
        verify(mockedBook, times(1)).getId();
    }

    @Test(expected = NotFoundException.class)
    public void shouldNotUpdateTheBookWhenNotInListAndThrowException() throws IOException, NotFoundException {
        when(mockedBook.getId()).thenReturn("100");

        sut.updateBook("1", JSON);

        verify(mockedMapper, atLeast(1)).readValue(JSON, book.class);
        verify(mockedBookList, times(1)).size();
        verify(mockedBookList, times(BOOKLIST_SIZE)).get(anyInt());
        verify(mockedBook, times(BOOKLIST_SIZE)).getId();
    }

    @Test
    public void shouldReturnJSONArrayBooksBasedOnSearchedAuthor() throws NotFoundException, IOException {
        when(mockedBookList.size()).thenReturn(1);
        when(mockedBook.getAuthor()).thenReturn("Author 1");

        assertEquals(JSON.replace("publish_date", "publishDate"), sut.searchByAuthorOrTitle("Author 1"));

        verify(mockedBookList, times(2)).size();
        verify(mockedBookList, times(1)).get(anyInt());
        verify(mockedBook, times(1)).getAuthor();
        verify(mockedMapper, times(1)).writeValueAsString(anyList());
    }

    @Test
    public void shouldReturnJSONArrayBooksBasedOnSearchedTitle() throws NotFoundException, IOException {
        when(mockedBookList.size()).thenReturn(1);
        when(mockedBook.getAuthor()).thenReturn("Author 1");
        when(mockedBook.getTitle()).thenReturn("Title 1");

        assertEquals(JSON.replace("publish_date", "publishDate"), sut.searchByAuthorOrTitle("Title 1"));

        verify(mockedBookList, times(2)).size();
        verify(mockedBookList, times(1)).get(anyInt());
        verify(mockedBook, times(1)).getTitle();
        verify(mockedBook, times(1)).getAuthor();
        verify(mockedMapper, times(1)).writeValueAsString(anyList());
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenSearchedTitleNotFound() throws NotFoundException, IOException {
        when(mockedBookList.size()).thenReturn(1);
        when(mockedBook.getAuthor()).thenReturn("Author 1");
        when(mockedBook.getTitle()).thenReturn("Java");

        sut.searchByAuthorOrTitle("Title 1");

        verify(mockedBookList, times(2)).size();
        verify(mockedBookList, times(1)).get(anyInt());
        verify(mockedBook, times(1)).getTitle();
        verify(mockedBook, times(1)).getAuthor();
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenSearchedAuthorNotFound() throws NotFoundException, IOException {
        when(mockedBookList.size()).thenReturn(1);
        when(mockedBook.getTitle()).thenReturn("Java");
        when(mockedBook.getAuthor()).thenReturn("Java");

        sut.searchByAuthorOrTitle("Author");

        verify(mockedBookList, times(2)).size();
        verify(mockedBookList, times(1)).get(anyInt());
        verify(mockedBook, times(1)).getTitle();
        verify(mockedBook, times(1)).getAuthor();
    }

    @Test
    public void shouldReturnJSONBookWhenSearchedById() throws IOException, NotFoundException {
        when(mockedBookList.size()).thenReturn(1);
        when(mockedBook.getId()).thenReturn("1");

        assertEquals(JSON.replace("publish_date", "publishDate"), sut.searchBookById("1"));

        verify(mockedBookList, times(1)).size();
        verify(mockedBook, times(1)).getId();
        verify(mockedMapper, times(1)).writeValueAsString(mockedBook);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenSearchedByIdNotFound() throws IOException, NotFoundException {
        when(mockedBookList.size()).thenReturn(1);
        when(mockedBook.getId()).thenReturn("100");

        sut.searchBookById("1");

        verify(mockedBookList, times(1)).size();
        verify(mockedBook, times(1)).getId();
    }
}