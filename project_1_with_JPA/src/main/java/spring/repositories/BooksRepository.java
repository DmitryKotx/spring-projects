package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.models.Book;
import spring.models.Person;

public interface BooksRepository extends JpaRepository<Book, Integer> {
}
