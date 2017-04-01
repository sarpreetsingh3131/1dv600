"use strict";

var Book = require('../models/Book');

module.exports = class Catalog {

    constructor(books) {
        this.bookList = books;
    }

    deleteBook(id) {
        try {
            this.bookList.catalog.book.splice(this.getIndex(id), 1);
        } catch (e) {
            throw new NotFoundException();
        }
    }

    updateBook(id, jsonBook) {
        try {
            this.bookList.catalog.book[this.getIndex(id)].title = jsonBook.title;
            this.bookList.catalog.book[this.getIndex(id)].author = jsonBook.author;
            this.bookList.catalog.book[this.getIndex(id)].description = jsonBook.description;
            this.bookList.catalog.book[this.getIndex(id)].genre = jsonBook.genre;
            this.bookList.catalog.book[this.getIndex(id)].price = jsonBook.price;
            this.bookList.catalog.book[this.getIndex(id)].publish_date = jsonBook.publishDate;
        } catch (e) {
            throw new NotFoundException();
        }
    }

    addBook(jsonBook) {
        try {
            var book = {
                '$': { id: jsonBook.id == undefined ? this.getMaxId() : jsonBook.id },
                author: [jsonBook.author],
                title: [jsonBook.title],
                genre: [jsonBook.genre],
                price: [jsonBook.price],
                publish_date: [jsonBook.publishDate == undefined ? jsonBook.publish_date : jsonBook.publishDate],
                description: [jsonBook.description]
            };
            this.bookList.catalog.book.push(book);
        } catch (e) {
            throw new NotFoundException();
        }
    }

    toJson() {
        var jsonBooks = [];
        for (var i = 0; i < this.bookList.catalog.book.length; i++) {
            jsonBooks.push(this.jsonToBook(this.bookList.catalog.book[i]));
        }
        return jsonBooks;
    }

    searchBookById(id) {
        try {
            return "[" + this.jsonToBook(this.bookList.catalog.book[this.getIndex(id)]) + "]";
        } catch (e) {
            throw new NotFoundException();
        }
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
        throw new NotFoundException();
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