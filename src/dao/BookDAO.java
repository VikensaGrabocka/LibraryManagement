package dao;

import models.Book;

import java.util.List;

public interface BookDAO {
    int addBook(Book book);
    Book getBookTitle(String title);
    Book getBookCategory(String category);
    Book getBookAuthor(String author);
    Book getBook(int id);
    List<Book> getAllBooks();
    void deleteBook(int id);
}

