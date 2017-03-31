(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');


    module.exports = function (id, callback) {
        LibraryDAO.readXMLFile(function (catalog) {
            callback(catalog.searchBookById(id));
        })
    };
}());
