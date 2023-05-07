package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.models.Book;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameStartingWith(String str);
}
