var Book = function book(id, title, author, description, genre, price, publishDate) {
    var book = new Object();
    book.id = id;
    book.title = title;
    book.author = author;
    book.price = price;
    book.genre = genre;
    book.publishDate = publishDate;
    book.description = description;
    return book;
}
exports.newBook = Book;