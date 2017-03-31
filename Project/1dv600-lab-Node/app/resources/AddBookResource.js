(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');
    var Catalog = require('../models/Catalog');

    module.exports = function (data, callback) {
        LibraryDAO.readXMLFile(function (catalog) {
            catalog.addBook(data);
            LibraryDAO.writeXMLFile(catalog);
            callback(catalog.toJson());
        })
    };
}());
