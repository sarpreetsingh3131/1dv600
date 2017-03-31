(function () {
    "use strict";

    var fs = require('fs');
    var book = require('../models/book')

    // https://github.com/Leonidas-from-XIV/node-xml2js
    var xml2js = require('xml2js');

    var LibraryDAO = {

        readXMLFile: function (callback) {
            var parser = new xml2js.Parser();
            fs.readFile(__dirname + '/books.xml', function (err, data) {
                parser.parseString(data, function (err, result) {
                    callback(result);
                });
            });
        },

        writeXMLFile: function (data) {
            var builder = new xml2js.Builder();
            var xml = builder.buildObject(data);
            fs.writeFile(__dirname + "/books.xml", xml, function (err) {

            });
        }
    };

    module.exports = LibraryDAO;
}());