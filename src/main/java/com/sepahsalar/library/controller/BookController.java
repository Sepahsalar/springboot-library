package com.sepahsalar.library.controller;

import com.sepahsalar.library.model.Book;
import com.sepahsalar.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository	bookRepository;

	@GetMapping
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/{id}")
	public Book getBookById(@PathVariable Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
	}

	@PostMapping
	public Book addBook(@RequestBody Book book) {
		return bookRepository.save(book);
	}

	@PutMapping("/{id}")
	public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        
		if (book != null) {
			book.setTitle(bookDetails.getTitle());
			book.setAuthor(bookDetails.getAuthor());
			book.setPublicationYear(bookDetails.getPublicationYear());

			book.setAvailable(bookDetails.isAvailable());
			return bookRepository.save(book);
		}
		return null;
	}

	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable Long id) {
		bookRepository.deleteById(id);
	}

	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        if (ex.getMessage().equals("Book not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error");
    }
}
