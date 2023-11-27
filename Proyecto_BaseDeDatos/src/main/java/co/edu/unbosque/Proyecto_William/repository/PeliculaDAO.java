package co.edu.unbosque.Proyecto_William.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.Proyecto_William.model.Pelicula;

public interface PeliculaDAO extends CrudRepository<Pelicula, Integer>{

	public ArrayList<Pelicula> findAll();
	
}
