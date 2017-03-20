package lnu.models;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "book")
@XmlType(propOrder = { "author", "title", "genre", "price", "publish_date", "description" })
public class book {

    private String id;
    private String title;
    private String author;
    private String price;
    private String genre;
    private String publish_date;
    private String description;

    public book() {
        id = "";
        title = "";
        author = "";
        price = "";
        genre = "";
        publish_date = "";
        description = "";
    }

    public book(String id, String title, String author, String description, String genre, String price,
            String publish_date) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.genre = genre;
        this.publish_date = publish_date;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Title: " + title + ", Author: " + author + ", Genre: " + genre + ", Price: " + price
                + ", Description: " + description + ", publish_date: " + publish_date;
    }
}