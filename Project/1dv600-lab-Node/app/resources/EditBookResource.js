(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');

    module.exports = function (id, data, callback) {
        LibraryDAO.readXMLFile(function (catalog) {
            catalog.updateBook(id, data);
            LibraryDAO.writeXMLFile(catalog);
            callback(catalog.toJson());
        })
    };
}());