package co.edu.unbosque.Proyecto_William.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.Proyecto_William.model.Book;

public interface BookDAO extends CrudRepository<Book, Integer>{

	public void deleteByBookID(Integer bookID);
	
	public ArrayList<Book> findAll();
	
	public Book findByBookID(Integer bookID);
	
}
