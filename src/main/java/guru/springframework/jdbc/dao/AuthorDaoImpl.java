package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorDaoImpl implements AuthorDao {
    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public Author getByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author saved) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }
}
