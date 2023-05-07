package spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.models.Book;
import spring.models.Person;
import spring.repositories.BooksRepository;

import java.util.Date;
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
    public List<Book> findAll(int page, int itemsPerPage) {
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }
    public List<Book> findAllOrderByYear() {
        return booksRepository.findAll(Sort.by("year"));
    }
    public List<Book> findByNameStartingWith(String str) {
        return booksRepository.findByNameStartingWith(str);
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
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            book.get().setOwner(null);
            book.get().setReceivedAt(null);
        }
    }
    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book book = findOne(id);
        book.setOwner(selectedPerson);
        book.setReceivedAt(new Date());
        selectedPerson.addBook(book);
    }
}
