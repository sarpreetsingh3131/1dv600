(function () {
    "use strict";

    var fs = require('fs');

    // https://github.com/Leonidas-from-XIV/node-xml2js
    var xml2js = require('xml2js');

    var LibraryDAO = {

        readXMLFile: function (callback) {
            var parser = new xml2js.Parser();
            fs.readFile(__dirname + '/books.xml', function (err, data) {
                parser.parseString(data, function (err, result) {
                    //console.dir(result);
                    console.log('Done');
                    callback(result);
                });
            });
        },

        writeXMLFile: function (data) {

        }
    };

    module.exports = LibraryDAO;
}());