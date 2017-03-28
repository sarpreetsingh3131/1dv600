var catalog = (function () {
    function catalog() {
        this.bookList = new Array();
    }
    catalog.prototype.deleteBook = function (id) {
        this.bookList.splice(this.bookList.indexOf(this.getBook(id)), 1);
    };
    catalog.prototype.addBook = function (json) {
        var book = this.JSONToBook(json);
        book.setId(this.getMaxId());
        this.bookList.push(book);
    };
    catalog.prototype.updateBook = function (id, jsonBook) {
        var updatedBook = this.JSONToBook(jsonBook);
        var oldBook = this.getBook(id);
        oldBook.setAuthor(updatedBook.getAuthor());
        oldBook.setDescription(updatedBook.getDescription());
        oldBook.setGenre(updatedBook.getGenre());
        oldBook.setPrice(updatedBook.getPrice());
        oldBook.setPublish_date(updatedBook.getPublish_date());
        oldBook.setTitle(updatedBook.getTitle());
    };
    catalog.prototype.searchByAuthorOrTitle = function (titleOrAuthor) {
        var foundBooks = new Array();
        for (var _i = 0, _a = this.bookList; _i < _a.length; _i++) {
            var book = _a[_i];
            if (book.getAuthor().toLowerCase().indexOf(titleOrAuthor.toLowerCase()) >= 0 || book.getTitle().toLowerCase().indexOf(titleOrAuthor.toLowerCase()) >= 0) {
                foundBooks.push(book);
            }
        }
        if (foundBooks.length == 0) {
            throw new Error("Not Found");
        }
        return this.modifyJSON(JSON.stringify(foundBooks));
    };
    catalog.prototype.searchBookById = function (id) {
        return this.modifyJSON(JSON.stringify(this.getBook(id)));
    };
    catalog.prototype.toJSON = function () {
        return JSON.stringify(this.bookList);
    };
    catalog.prototype.getBook = function (id) {
        for (var _i = 0, _a = this.bookList; _i < _a.length; _i++) {
            var book = _a[_i];
            if (book.getId() == id) {
                return book;
            }
        }
        throw new Error("Not Found");
    };
    catalog.prototype.getMaxId = function () {
        if (this.bookList.length == 0) {
            return 1;
        }
        this.bookList.sort(function (first, second) { return +first.getId - (+second.getId); });
        return +this.bookList[this.bookList.length - 1].getId + 1;
    };
    catalog.prototype.modifyJSON = function (json) {
        return json.split("publish_date").join("publishDate");
    };
    catalog.prototype.JSONToBook = function (json) {
        var book = JSON.parse(json.split("publishDate").join("publish_date"));
        return book;
    };
    return catalog;
}());
