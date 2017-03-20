// Use this file to write and read the xml file.
// http://www.journaldev.com/1234/jaxb-tutorial-example-to-convert-object-to-xml-and-xml-to-object
// javax.xml.bind is added as a part of the sdk from java7 and forward.

package lnu.dao;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import lnu.models.*;

public class booksDAO {

	private File file = new File("src/main/java/lnu/dao/books.xml");

	public booksDAO() {

	}

	/**
	 * This Constructor is for testing purpose
	 */
	public booksDAO(File file) {
		this.file = file;
	}

	/**
	 * Read XML file and return catalog object
	 */
	public catalog XMLToCatalog() throws Exception {
		JAXBContext context = JAXBContext.newInstance(catalog.class);
		Unmarshaller un = context.createUnmarshaller();
		return (catalog) un.unmarshal(file);
	}

	/**
	 * Save catalog into XML file 
	 */
	public void catalogToXML(catalog catalog) throws Exception {
		JAXBContext context = JAXBContext.newInstance(catalog.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(catalog, file);
	}
}