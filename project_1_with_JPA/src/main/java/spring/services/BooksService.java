package spring.services;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.models.Book;
import spring.models.Person;
import spring.repositories.BooksRepository;
import spring.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
    @Transactional
    public Optional<Person> getBookOwner(int id) {
        return Optional.ofNullable(findOne(id).getOwner());
    }
    @Transactional
    public void release (int id) {
        findOne(id).setOwner(null);
    }
    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book book = findOne(id);
        book.setOwner(selectedPerson);
        selectedPerson.addBook(book);
    }
}
