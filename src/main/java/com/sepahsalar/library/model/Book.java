package com.sepahsalar.library.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	id;

	private String	title;
	private String	author;
	private int		publicationYear;

	private boolean	available = true;

	// @Column(nullable = false, length = 100)
	// private String title;

	// @Column(nullable = false, length = 100)
	// private String author;

	// @Column(nullable = false, length = 13, unique = true)
	// private String isbn;

	// @Column(nullable = false)
	// private int publicationYear;

	// @Column(nullable = false)
	// private boolean available;
}
