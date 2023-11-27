package co.edu.unbosque.Proyecto_William.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {

	@Id
	private Integer bookID;
	
	@Column(nullable = false, length = 500)
	private String title;
	
	@Column(nullable = false, length = 500)
	private String authors;

	@Column(nullable = false, length = 500)
	private String average_rating;
	
	@Column(nullable = false, unique = true, length = 500)
	private String isbn;

	@Column(nullable = false, unique = true, length = 500)
	private String isbn13;
	
	@Column(nullable = false, length = 500)
	private String language_code;
	
	@Column(nullable = false)
	private Integer num_pages;
	
	@Column(nullable = false)
	private Long ratings_count;
	
	@Column(nullable = false)
	private Integer text_reviews_count;
	
	@Column(nullable = false, length = 500)
	private String publication_date;
	
	@Column(nullable = false, length = 500)
	private String publisher;
	
	@Column(nullable = true)
	private String FIELD13;
	
	public Book() {
	}

	public Book(Integer bookID, String title, String authors, String average_rating, String isbn, String isbn13,
			String language_code, Integer num_pages, Long ratings_count, Integer text_reviews_count,
			String publication_date, String publisher, String fIELD13) {
		super();
		this.bookID = bookID;
		this.title = title;
		this.authors = authors;
		this.average_rating = average_rating;
		this.isbn = isbn;
		this.isbn13 = isbn13;
		this.language_code = language_code;
		this.num_pages = num_pages;
		this.ratings_count = ratings_count;
		this.text_reviews_count = text_reviews_count;
		this.publication_date = publication_date;
		this.publisher = publisher;
		FIELD13 = fIELD13;
	}

	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getAverage_rating() {
		return average_rating;
	}

	public void setAverage_rating(String average_rating) {
		this.average_rating = average_rating;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public String getLanguage_code() {
		return language_code;
	}

	public void setLanguage_code(String language_code) {
		this.language_code = language_code;
	}

	public Integer getNum_pages() {
		return num_pages;
	}

	public void setNum_pages(Integer num_pages) {
		this.num_pages = num_pages;
	}

	public Long getRatings_count() {
		return ratings_count;
	}

	public void setRatings_count(Long ratings_count) {
		this.ratings_count = ratings_count;
	}

	public Integer getText_reviews_count() {
		return text_reviews_count;
	}

	public void setText_reviews_count(Integer text_reviews_count) {
		this.text_reviews_count = text_reviews_count;
	}

	public String getPublication_date() {
		return publication_date;
	}

	public void setPublication_date(String publication_date) {
		this.publication_date = publication_date;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getFIELD13() {
		return FIELD13;
	}

	public void setFIELD13(String fIELD13) {
		FIELD13 = fIELD13;
	}

	@Override
	public String toString() {
		return "Book [bookID=" + bookID + ", title=" + title + ", authors=" + authors + ", average_rating="
				+ average_rating + ", isbn=" + isbn + ", isbn13=" + isbn13 + ", language_code=" + language_code
				+ ", num_pages=" + num_pages + ", ratings_count=" + ratings_count + ", text_reviews_count="
				+ text_reviews_count + ", publication_date=" + publication_date + ", publisher=" + publisher
				+ ", field13=" + FIELD13 + "]";
	}
	
	
	
}
