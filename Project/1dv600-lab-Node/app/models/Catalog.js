"use strict";

var Book = require('../models/Book');

module.exports = class Catalog {

    constructor(books) {
        this.bookList = books;
    }

    deleteBook(id) {
        this.bookList.catalog.book.splice(this.getIndex(id), 1);
    }

    updateBook(id, jsonBook) {
        this.bookList.catalog.book[this.getIndex(id)].title = jsonBook.title;
        this.bookList.catalog.book[this.getIndex(id)].author = jsonBook.author;
        this.bookList.catalog.book[this.getIndex(id)].description = jsonBook.description;
        this.bookList.catalog.book[this.getIndex(id)].genre = jsonBook.genre;
        this.bookList.catalog.book[this.getIndex(id)].price = jsonBook.price;
        this.bookList.catalog.book[this.getIndex(id)].publish_date = jsonBook.publishDate;
    }

    addBook(jsonBook) {
        var book = {
            '$': { id: this.getMaxId() },
            author: [jsonBook.author],
            title: [jsonBook.title],
            genre: [jsonBook.genre],
            price: [jsonBook.price],
            publish_date: [jsonBook.publish_date],
            description: [jsonBook.description]
        };
        this.bookList.catalog.book.push(book);
    }

    toJson() {
        var jsonBooks = [];
        for (var i = 0; i < this.bookList.catalog.book.length; i++) {
            jsonBooks.push(this.jsonToBook(this.bookList.catalog.book[i]));
        }
        return jsonBooks;
    }

    searchBookById(id) {
        return "[" + this.jsonToBook(this.bookList.catalog.book[this.getIndex(id)]) + "]";
    }

    searchByTitleAndAuthor(titleOrAuthor) {
        var foundBooks = [];
        for (var i = 0; i < this.bookList.catalog.book.length; i++) {
            console.log(titleOrAuthor);
            var title = "" + this.bookList.catalog.book[i].title;
            var author = "" + this.bookList.catalog.book[i].author;
            if (title.toLowerCase().includes(titleOrAuthor.toLowerCase()) || author.toLowerCase().includes(titleOrAuthor.toLowerCase())) {
                foundBooks.push(this.jsonToBook(this.bookList.catalog.book[i]));
            }
        }
        return foundBooks;
    }

    jsonToBook(json) {
        return new Book(json.$.id, json.title, json.author, json.description, json.genre, json.price, json.publish_date);
    }

    getIndex(id) {
        for (var i = 0; i < this.bookList.catalog.book.length; i++) {
            if (this.bookList.catalog.book[i].$.id == id) {
                return i;
            }
        }
    }

    getMaxId() {
        if (this.bookList.catalog.book.length == 0) {
            return "1";
        }
        this.bookList.catalog.book.sort(function (a, b) {
            return parseInt(a.$.id) - parseInt(b.$.id);
        })
        return "" + (parseInt(this.bookList.catalog.book[this.bookList.catalog.book.length - 1].$.id) + 1);
    }
}