package models;

public class Loan {
        private int id;
        private int bookId;
        private String borrowerName;
        private String borrowerSurname;
        private String startDate;
        private String returnDate;

    public Loan() {}
    public Loan(int bookId, String borrowerName, String borrowerSurname, String startDate, String returnDate) {
        this.bookId = bookId;
        this.borrowerName = borrowerName;
        this.borrowerSurname = borrowerSurname;
        this.startDate = startDate;
        this.returnDate = returnDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBorrowerSurname() {
        return borrowerSurname;
    }

    public void setBorrowerSurname(String borrowerSurname) {
        this.borrowerSurname = borrowerSurname;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

