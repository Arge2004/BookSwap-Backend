package BookSwap.controller;

import BookSwap.model.entity.Book;
import BookSwap.service.IBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class BookController {
    @Autowired
    private IBook bookService;

    @PostMapping(path = "book")
    public Book create(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping(path = "book")
    public Book update(@RequestBody Book book) {
        return bookService.save(book);
    }

    @DeleteMapping(path = "book/{id}")
    public void delete(@PathVariable Integer id) {
        bookService.delete(bookService.findById(id));
    }

    @GetMapping(path = "book/{id}")
    public Book findById(@PathVariable Integer id) {
        return bookService.findById(id);
    }

    @GetMapping(path = "books")
    public List<Book> findAll(){
        return bookService.findAll();
    }

    @GetMapping(path = "books/author/{id_author}")
    public List<Book> findByAuthor(@PathVariable Integer id_author){
        return bookService.findByAuthor(id_author);
    }

    @GetMapping(path = "books/user/{title}")
    public List<Book> findByTitle(@PathVariable String title){
        return bookService.findByTitle(title);
    }

    @GetMapping(path = "books/genre/{id_genre}")
    public List<Book> findByGenre(@PathVariable Integer id_genre){
        return bookService.findByGenre(id_genre);
    }

}
