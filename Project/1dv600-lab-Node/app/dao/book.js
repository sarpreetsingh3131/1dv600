var book = (function () {
    function book(id, title, author, description, genre, price, publish_date) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.genre = genre;
        this.publish_date = publish_date;
        this.description = description;
    }
    book.prototype.getId = function () {
        return this.id;
    };
    book.prototype.setId = function (id) {
        this.id = id;
    };
    book.prototype.getTitle = function () {
        return this.title;
    };
    book.prototype.setTitle = function (title) {
        this.title = title;
    };
    book.prototype.getAuthor = function () {
        return this.author;
    };
    book.prototype.setAuthor = function (author) {
        this.author = author;
    };
    book.prototype.getPrice = function () {
        return this.price;
    };
    book.prototype.setPrice = function (price) {
        this.price = price;
    };
    book.prototype.getGenre = function () {
        return this.genre;
    };
    book.prototype.setGenre = function (genre) {
        this.genre = genre;
    };
    book.prototype.getPublish_date = function () {
        return this.publish_date;
    };
    book.prototype.setPublish_date = function (publish_date) {
        this.publish_date = publish_date;
    };
    book.prototype.getDescription = function () {
        return this.description;
    };
    book.prototype.setDescription = function (description) {
        this.description = description;
    };
    book.prototype.toString = function () {
        return "Id: " + this.id + ", Title: " + this.title + ", Author: " + this.author + ", Genre: " + this.genre + ", Price: " + this.price + ", Description: " + this.description + ", publish_date: " + this.publish_date;
    };
    return book;
}());
