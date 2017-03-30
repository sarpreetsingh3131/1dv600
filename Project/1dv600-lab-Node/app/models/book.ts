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

    public getId(): string {
        return this.id;
    }

    public setId(id: string): void {
        this.id = id;
    }

    public getTitle(): string {
        return this.title;
    }

    public setTitle(title: string): void {
        this.title = title;
    }

    public getAuthor(): string {
        return this.author;
    }

    public setAuthor(author: string): void {
        this.author = author;
    }

    public getPrice(): string {
        return this.price;
    }

    public setPrice(price: string): void {
        this.price = price;
    }

    public getGenre(): string {
        return this.genre;
    }

    public setGenre(genre: string): void {
        this.genre = genre;
    }

    public getPublish_date(): string {
        return this.publish_date;
    }

    public setPublish_date(publish_date: string): void {
        this.publish_date = publish_date;
    }

    public getDescription(): string {
        return this.description;
    }

    public setDescription(description: string): void {
        this.description = description;
    }

    public toString(): string {
        return "Id: " + this.id + ", Title: " + this.title + ", Author: " + this.author + ", Genre: " + this.genre + ", Price: " + this.price + ", Description: " + this.description + ", publish_date: " + this.publish_date;
    }
}