class book {

    private id: string;
    private title: string;
    private author: string;
    private price: string;
    private genre: string;
    private publish_date: string;
    private description: string;

    public constructor(id: string, title: string, author: string, description: string, genre: string, price: string,
        publish_date: string) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.genre = genre;
        this.publish_date = publish_date;
        this.description = description;
    }
    public getId() {
        return this.id;
    }

    public setId(id: string) {
        this.id = id;
    }

    public getTitle() {
        return this.title;
    }

    public setTitle(title: string) {
        this.title = title;
    }

    public getAuthor() {
        return this.author;
    }

    public setAuthor(author: string) {
        this.author = author;
    }

    public getPrice() {
        return this.price;
    }

    public setPrice(price: string) {
        this.price = price;
    }

    public getGenre() {
        return this.genre;
    }

    public setGenre(genre: string) {
        this.genre = genre;
    }

    public getPublish_date() {
        return this.publish_date;
    }

    public setPublish_date(publish_date: string) {
        this.publish_date = publish_date;
    }

    public getDescription() {
        return this.description;
    }

    public setDescription(description: string) {
        this.description = description;
    }
    public toString() {
        return "Id: " + this.id + ", Title: " + this.title + ", Author: " + this.author + ", Genre: " + this.genre + ", Price: " + this.price + ", Description: " + this.description + ", publish_date: " + this.publish_date;
    }
}