(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');
    var book = require('../models/book')

    module.exports = function (callback, title) {
        LibraryDAO.readXMLFile(function (result) {
            var bookList = [];
            for (var i = 0; i < result.catalog.book.length; i++) {
                bookList.push(book.newBook(result.catalog.book[i].$.id, result.catalog.book[i].title, result.catalog.book[i].author, result.catalog.book[i].description, result.catalog.book[i].genre, result.catalog.book[i].price, result.catalog.book[i].publish_date))
            }
            callback(bookList);
        })
    };
}());