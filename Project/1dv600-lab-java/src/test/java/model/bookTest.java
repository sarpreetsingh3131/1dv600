package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import lnu.models.*;

public class bookTest {

    private book sut;

    @Before
    public void setUp() {
        sut = new book();
    }

    @Test
    public void shouldReturn1() {
        sut.setId("1");
        assertEquals("1", sut.getId());
    }

    @Test
    public void shouldReturnTitle() {
        sut.setTitle("Title");
        assertEquals("Title", sut.getTitle());
    }

    @Test
    public void shouldReturnAuthor() {
        sut.setAuthor("Author");
        assertEquals("Author", sut.getAuthor());
    }

    @Test
    public void shouldReturnDescription() {
        sut.setDescription("Description");
        assertEquals("Description", sut.getDescription());
    }

    @Test
    public void shouldReturnGenre() {
        sut.setGenre("Genre");
        assertEquals("Genre", sut.getGenre());
    }

    @Test
    public void shouldReturn100() {
        sut.setPrice("100");
        assertEquals("100", sut.getPrice());
    }

    @Test
    public void shouldReturn2012_12_12() {
        sut.setPublish_date("2012-12-12");
        assertEquals("2012-12-12", sut.getPublish_date());
    }

    @Test
    public void shouldReturnBookDetails() {
        sut = new book("1", "Title", "Author", "Description", "Genre", "100", "2012-12-12");
        assertEquals(
                "Id: 1, Title: Title, Author: Author, Genre: Genre, Price: 100, Description: Description, publish_date: 2012-12-12",
                sut.toString());
    }
}