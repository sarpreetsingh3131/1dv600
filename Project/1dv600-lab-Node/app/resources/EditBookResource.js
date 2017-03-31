(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');
    var Book = require('../models/Book')

    module.exports = function (id, data, callback) {
        LibraryDAO.readXMLFile(function (catalog) {
            catalog.updateBook(id, data);
            LibraryDAO.writeXMLFile(catalog);
            callback(catalog.toJson());
        })
    };
}());
