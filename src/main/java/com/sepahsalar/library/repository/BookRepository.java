package com.sepahsalar.library.repository;

import com.sepahsalar.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {}

// public class BookRepository {
	
// }
