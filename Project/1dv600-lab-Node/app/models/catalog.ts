class catalog {

    private bookList: Array<book>;

    constructor() {
        this.bookList = new Array<book>();
    }

    public deleteBook(id: string): void {
        this.bookList.splice(this.bookList.indexOf(this.getBook(id)), 1);
    }

    public addBook(json: string): void {
        let book = this.JSONToBook(json);
        book.setId("" + this.getMaxId());
        this.bookList.push(book);
    }

    public updateBook(id: string, jsonBook: string): void {
        let updatedBook = this.JSONToBook(jsonBook);
        let oldBook = this.getBook(id);

        oldBook.setAuthor(updatedBook.getAuthor());
        oldBook.setDescription(updatedBook.getDescription());
        oldBook.setGenre(updatedBook.getGenre());
        oldBook.setPrice(updatedBook.getPrice());
        oldBook.setPublish_date(updatedBook.getPublish_date());
        oldBook.setTitle(updatedBook.getTitle());
    }

    public searchByAuthorOrTitle(titleOrAuthor: string): string {
        let foundBooks: Array<book> = new Array<book>();

        for (var book of this.bookList) {
            if (book.getAuthor().toLowerCase().indexOf(titleOrAuthor.toLowerCase()) >= 0 || book.getTitle().toLowerCase().indexOf(titleOrAuthor.toLowerCase()) >= 0) {
                foundBooks.push(book);
            }
        }

        if (foundBooks.length == 0) {
            throw new Error("Not Found");
        }

        return this.modifyJSON(JSON.stringify(foundBooks));
    }

    public searchBookById(id: string): string {
        return this.modifyJSON(JSON.stringify(this.getBook(id)));
    }

    public toJSON(): string {
        return JSON.stringify(this.bookList);
    }

    private getBook(id: string): book {
        for (var book of this.bookList) {
            if (book.getId() == id) {
                return book;
            }
        }
        throw new Error("Not Found")
    }

    private getMaxId(): number {
        if (this.bookList.length == 0) {
            return 1;
        }
        this.bookList.sort((first, second) => +first.getId - (+second.getId));
        return +this.bookList[this.bookList.length - 1].getId + 1;
    }

    private modifyJSON(json: string): string {
        return json.split("publish_date").join("publishDate");
    }

    private JSONToBook(json: string): book {
        let book = JSON.parse(json.split("publishDate").join("publish_date"));
        return book;
    }
}