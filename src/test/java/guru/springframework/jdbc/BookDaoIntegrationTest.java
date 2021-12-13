package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;

    @Test
    void testGetBook() {
        Book book = bookDao.getById(1L);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    void testGetBookByName() {
        Book book = bookDao.getByTitle("Spring in Action, 5th Edition");
        assertThat(book).isNotNull();
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("123456798");
        book.setTitle("TestTitle");
        book.setPublisher("TestPublisher");
        book.setAuthorId(1L);
        Book saved = bookDao.saveNewBook(book);

        System.out.println("New Id: " + saved.getId());

        assertThat(saved).isNotNull();
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setIsbn("123456798");
        book.setTitle("TestTitle");
        book.setPublisher("TestPublisher");
        book.setAuthorId(1L);

        Book saved = bookDao.saveNewBook(book);

        saved.setPublisher("GeorgePublisher");
        Book updated = bookDao.updateBook(saved);

        assertThat(updated.getPublisher()).isEqualTo("GeorgePublisher");
    }

    @Test
    void testDeleteAuthor() {
        Book book = new Book();
        book.setIsbn("123456798");
        book.setTitle("TestTitle");
        book.setPublisher("TestPublisher");
        book.setAuthorId(1L);

        Book saved = bookDao.saveNewBook(book);
        bookDao.deleteBookById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookDao.getById(saved.getId());
        });
    }

}
