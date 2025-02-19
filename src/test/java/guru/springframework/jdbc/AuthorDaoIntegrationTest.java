package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.domain.Author;
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
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetAuthor() {
        Author author = authorDao.getById(1L);
        assertThat(author.getId()).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.getByName("Craig", "Walls");
        assertThat(author).isNotNull();
    }

    @Test
    void testSaveAuthor() {
        Author author = new Author();
        author.setFirstName("Radek");
        author.setLastName("Provaznik");
        Author saved = authorDao.saveNewAuthor(author);

        System.out.println("New Id: " + saved.getId());

        assertThat(saved).isNotNull();
    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("Johny");
        author.setLastName("yt");

        Author saved = authorDao.saveNewAuthor(author);

        saved.setLastName("Tommy");
        Author updated = authorDao.updateAuthor(saved);

        assertThat(updated.getLastName()).isEqualTo("Tommy");
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("Hon");
        author.setLastName("Shun");

        Author saved = authorDao.saveNewAuthor(author);
        authorDao.deleteAuthorById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            authorDao.getById(saved.getId());
        });
    }

}
