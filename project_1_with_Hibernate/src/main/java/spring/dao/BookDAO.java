package spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.models.Book;
import spring.models.Person;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setAuthor(updatedBook.getAuthor());
        book.setName(updatedBook.getName());
        book.setYear(updatedBook.getYear());
        book.setOwner(updatedBook.getOwner());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));
    }
    @Transactional
    public Optional<Person> getBookOwner(int id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Book.class, id).getOwner());
    }
    @Transactional
    public void release(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.get(Book.class, id).setOwner(null);
    }
    @Transactional
    public void assign(int id, Person selectedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(selectedPerson);
        Person person = session.get(Person.class, selectedPerson.getId());
        person.addBook(book);
        System.out.println(person.getBooks());
    }
}