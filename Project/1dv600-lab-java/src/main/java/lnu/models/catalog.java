package lnu.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.xml.bind.annotation.*;
import javax.ws.rs.NotFoundException;
import java.io.*;
import io.dropwizard.jackson.Jackson;
import com.fasterxml.jackson.databind.ObjectMapper;

@XmlRootElement(name = "catalog")
@XmlAccessorType(XmlAccessType.FIELD)
public class catalog {

    @XmlElement(name = "book")
    private ArrayList<book> bookList;
    private static ObjectMapper mapper;

    public catalog() {
        bookList = new ArrayList<book>();
        catalog.mapper = Jackson.newObjectMapper();
    }

    /**
     * This constructor is for testing purpose such as mocking ArrayList and ObjectMapper
     */
    public catalog(ArrayList<book> bookList, ObjectMapper mapper) {
        this.bookList = bookList;
        catalog.mapper = mapper;
    }

    /**
     * Find and delete book, else throw exception
     */
    public void deleteBook(String id) throws NotFoundException {
        bookList.remove(this.getBook(id));
    }

    /**
     * Add book, if JSON string not valid throw exception
     */
    public void addBook(String json) throws IOException {
        book book = this.JSONToBook(json);
        book.setId(this.getMaxId());
        bookList.add(book);
    }

    /**
     * Update book, if not found or JSON string not valid throw exception
     */
    public void updateBook(String id, String jsonBook) throws NotFoundException, IOException {
        book updatedBook = this.JSONToBook(jsonBook);
        book oldBook = this.getBook(id);

        oldBook.setAuthor(updatedBook.getAuthor());
        oldBook.setDescription(updatedBook.getDescription());
        oldBook.setGenre(updatedBook.getGenre());
        oldBook.setPrice(updatedBook.getPrice());
        oldBook.setPublish_date(updatedBook.getPublish_date());
        oldBook.setTitle(updatedBook.getTitle());
    }

    /**
     * Find book/s , if no book found throw exception. In this I assume that if any book in the booklist contains searched string then I add it as found. I can also use only 'equals' method however I prefer contains more because in case client is unsure about the title then system will shows all the relevant option
     */
    public String searchByAuthorOrTitle(String titleOrAuthor) throws NotFoundException, IOException {
        ArrayList<book> foundBooks = new ArrayList<book>();

        for (int i = 0; i < bookList.size(); i++) {
            book book = bookList.get(i);

            if (book.getAuthor().toLowerCase().contains(titleOrAuthor.toLowerCase())
                    || book.getTitle().toLowerCase().contains(titleOrAuthor.toLowerCase())) {
                foundBooks.add(book);
            }
        }

        if (foundBooks.isEmpty()) {
            throw new NotFoundException();
        }
        return modifyJSON(catalog.mapper.writeValueAsString(foundBooks));
    }

    /**
     * Find book and convert into JSON, if not found throw exception
     */
    public String searchBookById(String id) throws IOException, NotFoundException {
        return modifyJSON(catalog.mapper.writeValueAsString(this.getBook(id)));
    }

    /**
     * Convert books to JSON array, throws generic IOException
     */
    public String toJSON() throws IOException {
        return modifyJSON(catalog.mapper.writeValueAsString(this.bookList));
    }

    /**
     * Find book, if not found throw exception
     */
    private book getBook(String id) throws NotFoundException {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getId().equals(id)) {
                return bookList.get(i);
            }
        }
        throw new NotFoundException();
    }

    /**
     * Find maxId based on book's id present in the list, MaxId = 1 when list is empty
     */
    private String getMaxId() {
        Collections.sort(bookList, new Comparator<book>() {
            @Override
            public int compare(book first, book second) {
                return Integer.parseInt(first.getId()) - Integer.parseInt(second.getId());
            }
        });
        return bookList.isEmpty() ? "1" : "" + (Integer.parseInt(bookList.get(bookList.size() - 1).getId()) + 1);
    }

    /**
     * Below methods helps to solve the publishDate attribute problem. I have found that front end use 
     * 'publishDate' whereas back end and XML use 'publish_date'. So I simply replace them according to needs
     */
    private String modifyJSON(String json) {
        return json.replaceAll("publish_date", "publishDate");
    }

    private book JSONToBook(String json) throws IOException {
        return catalog.mapper.readValue(json.replaceAll("publishDate", "publish_date"), book.class);
    }
}