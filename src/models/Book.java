package models;

public class Book {

        private int id;
        private String title;
        private String author;
        private int year;
        private String category;
        private int bookNumber;
        private int shelfNumber;
        private String shelfName;

        // Constructors
        public Book() {}

        public Book(String title, String author, int year, String category, int bookNumber, int shelfNumber, String shelfName) {
            this.title = title;
            this.author = author;
            this.year = year;
            this.category = category;
            this.bookNumber = bookNumber;
            this.shelfNumber = shelfNumber;
            this.shelfName = shelfName;

        }

        public Book(int id, String title, String author, int year, String category, int bookNumber, int shelfNumber, String shelfName) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.year = year;
            this.category = category;
            this.bookNumber = bookNumber;
            this.shelfNumber = shelfNumber;
            this.shelfName = shelfName;
        }

    // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }
}
