(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');

    module.exports = function (callback, title) {
        LibraryDAO.readXMLFile(function (catalog) {
            try {
                callback("200", catalog.toJson());
            } catch (e) {
                callback("404", "Not Found");
            }
        })
    };
}());