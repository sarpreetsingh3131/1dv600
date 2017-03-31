(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');

    module.exports = function (id, callback) {
        LibraryDAO.readXMLFile(function (catalog) {
            catalog.deleteBook(id);
            LibraryDAO.writeXMLFile(catalog);
            callback(catalog.toJson());
        })
    };
}());