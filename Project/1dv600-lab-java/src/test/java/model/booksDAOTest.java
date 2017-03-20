package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import lnu.dao.booksDAO;
import lnu.models.catalog;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class booksDAOTest {

    private booksDAO sut;
    private catalog catalog;

    private File file = new File("src/main/java/lnu/dao/test.xml");

    private final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><catalog><book id=\"1\"><author>Author 1</author><title>Title 1</title><genre>Genre 1</genre><price>100</price><publish_date>2012-12-12</publish_date><description>Description 1</description></book></catalog>";

    private final String JSON = "[{\"id\":\"1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"price\":\"100\",\"genre\":\"Genre 1\",\"publishDate\":\"2012-12-12\",\"description\":\"Description 1\"}]";

    @Before
    public void setUp() throws Exception {
        sut = new booksDAO(file);

        writeFile(XML);
        catalog = sut.XMLToCatalog();
    }

    @Test
    public void shouldConvertXMLToCatalog() throws Exception {
        assertNotNull(catalog);
        assertEquals(JSON, catalog.toJSON());
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenFileDataIsNotCorrect() throws Exception {
        writeFile("ABC");
        sut.XMLToCatalog();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenFileDoesNotExist() throws Exception {
        file.delete();
        sut.XMLToCatalog();
    }

    @Test
    public void shouldConvertCatalogToXML() throws Exception {
        file.delete();
        assertFalse(file.exists());

        sut.catalogToXML(catalog);

        assertTrue(file.exists());
        assertEquals(XML, readFile());
    }

    private void writeFile(String XML) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.write(XML);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile() {
        String content = "";
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                content += scan.nextLine().trim();
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}