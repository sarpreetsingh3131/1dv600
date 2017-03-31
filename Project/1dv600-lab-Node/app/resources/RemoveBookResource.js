(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');

    module.exports = function (id, callback) {
        LibraryDAO.readXMLFile(function (bookList) {
            for (var i = 0; i < bookList.catalog.book.length; i++) {
                if (bookList.catalog.book[i].$.id == id) {
                    bookList.catalog.book.splice(i, 1);
                    break;
                }
            }
            LibraryDAO.writeXMLFile(bookList);
            callback(bookList);
        })
    };
}());