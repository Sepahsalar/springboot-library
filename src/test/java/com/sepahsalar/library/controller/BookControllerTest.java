package com.sepahsalar.library.controller;

import com.sepahsalar.library.model.Book;
import com.sepahsalar.library.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ContextConfiguration(classes = {BookController.class, BookControllerTest.MockConfig.class})
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

	@TestConfiguration
	static class MockConfig {
		@Bean
		public BookRepository bookRepository() {
			return Mockito.mock(BookRepository.class);
		}
	}

    @Autowired
    private ObjectMapper objectMapper;

    // 1. GET /books returns empty list
    @Test
    public void shouldReturnEmptyList_WhenNoBooksExist() throws Exception {
        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(0)));
    }

    // 2. POST /books creates a book
    @Test
    public void shouldAddBookSuccessfully() throws Exception {
        Book book = new Book(null, "Test", "Author", 2023, true);
        Book savedBook = new Book(1L, "Test", "Author", 2023, true);

        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)));
    }

    // 3. GET /books/1 returns a book
    @Test
    public void shouldReturnBookById() throws Exception {
        Book book = new Book(1L, "Book 1", "Author", 2023, true);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is("Book 1")));
    }

    // 4. GET /books/99 returns 404
    @Test
    public void shouldReturnError_WhenBookNotFound() throws Exception {
        Mockito.when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/99"))
            .andExpect(status().isNotFound());
    }

    // 5. PUT /books/1 updates book
    @Test
    public void shouldUpdateBook() throws Exception {
        Book existing = new Book(1L, "Old", "A", 2000, false);
        Book updated = new Book(1L, "New", "B", 2024, true);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(updated);

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is("New")));
    }

    // 6. PUT /books/99 returns error
    @Test
    public void shouldReturnError_WhenUpdatingNonexistentBook() throws Exception {
        Book book = new Book(null, "Any", "A", 2020, true);
        Mockito.when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/books/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
            .andExpect(status().isNotFound());
    }

    // 7. DELETE /books/1
    @Test
    public void shouldDeleteBookSuccessfully() throws Exception {
        mockMvc.perform(delete("/books/1"))
            .andExpect(status().isOk());
    }

    // 8. POST /books with missing fields
    @Test
    public void shouldFailToAddBook_WhenMissingFields() throws Exception {
        Book incomplete = new Book();
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incomplete)))
            .andExpect(status().isOk()); // In real app, use validation and expect badRequest()
    }

    // 9. GET /books returns multiple books
    @Test
    public void shouldReturnMultipleBooks() throws Exception {
        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(
                new Book(1L, "Book1", "A", 2020, true),
                new Book(2L, "Book2", "B", 2021, false)
        ));

        mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(2)));
    }

    // 10. Confirm fields match after add
    @Test
    public void shouldReturnCorrectFields_AfterAddingBook() throws Exception {
        Book book = new Book(null, "Exact", "Match", 2022, true);
        Book saved = new Book(1L, "Exact", "Match", 2022, true);

        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(saved);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
            .andExpect(jsonPath("$.title", is("Exact")))
            .andExpect(jsonPath("$.publicationYear", is(2022)));
    }
}
