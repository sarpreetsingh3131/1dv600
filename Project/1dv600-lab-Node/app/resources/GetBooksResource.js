(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');
    var Catalog = require('../models/Catalog');

    module.exports = function (callback, title) {
        LibraryDAO.readXMLFile(function (catalog) {
            if (title == undefined) {
                callback(catalog.toJson());
            }
            else {
                callback(catalog.searchByTitleAndAuthor(title));
            }
        })
    };
}());