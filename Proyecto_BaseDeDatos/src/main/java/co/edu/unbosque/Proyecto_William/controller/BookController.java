package co.edu.unbosque.Proyecto_William.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.unbosque.Proyecto_William.model.Book;
import co.edu.unbosque.Proyecto_William.repository.BookDAO;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/BookController")
@Transactional
public class BookController {

	@Autowired
	BookDAO bookDAO;

	@PostMapping(path = "/AddBook")
	public ResponseEntity<String> addBook(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Por favor, seleccione un archivo.");
		}
		try {
			byte[] fileBytes = file.getBytes();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode[] bookNodes = objectMapper.readValue(fileBytes, JsonNode[].class);
			for (int i = 0; i < bookNodes.length; i++) {
				try {
					int bookID = Integer.parseInt(bookNodes[i].get("bookID").asText());
					String title = bookNodes[i].get("title").asText().substring(0,
							Math.min(bookNodes[i].get("title").asText().length(), 450));
					String authors = bookNodes[i].get("authors").asText().substring(0,
							Math.min(bookNodes[i].get("authors").asText().length(), 450));
					String average_rating = bookNodes[i].get("average_rating").asText().substring(0,
							Math.min(bookNodes[i].get("average_rating").asText().length(), 450));
					String isbn = bookNodes[i].get("isbn").asText().substring(0,
							Math.min(bookNodes[i].get("isbn").asText().length(), 450));
					String isbn13 = bookNodes[i].get("isbn13").asText().substring(0,
							Math.min(bookNodes[i].get("isbn13").asText().length(), 450));
					String language_code = bookNodes[i].get("language_code").asText();
					Integer num_pages = Integer.parseInt(bookNodes[i].get("num_pages").asText());
					Long ratings_count = Long.parseLong(bookNodes[i].get("ratings_count").asText());
					Integer text_reviews_count = Integer.parseInt(bookNodes[i].get("text_reviews_count").asText());
					String publication_date = bookNodes[i].get("publication_date").asText();
					String publisher = bookNodes[i].get("publisher").asText();
					String FIELD13 = bookNodes[i].get("FIELD13").asText();

					Book newBook = new Book(bookID, title, authors, average_rating, isbn, isbn13, language_code,
							num_pages, ratings_count, text_reviews_count, publication_date, publisher, FIELD13);
					bookDAO.save(newBook);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return ResponseEntity.status(HttpStatus.CREATED).body("SZ");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo JSON.");
		}
	}

	@DeleteMapping("/DeleteBook/{bookID}")
	public ResponseEntity<String> deleteEmpleado(@PathVariable Integer bookID) {
		Book book = bookDAO.findByBookID(bookID);
		if (book != null) {
			bookDAO.delete(book);
			return ResponseEntity.ok("Libro eliminado con éxito.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Libro con código " + bookID + " no encontrado.");
		}
	}

	@GetMapping(path = "/GetAllBook")
	public ResponseEntity<Iterable<Book>> getAllBook() {
		Iterable<Book> lst = bookDAO.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(lst);
	}

}
