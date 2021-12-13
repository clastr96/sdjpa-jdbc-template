package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;

public interface BookDao {
    Book getById(Long id);
    Book getByTitle(String title);
    Book saveNewBook(Book author);
    Book updateBook(Book saved);
    void deleteBookById(Long id);
}
