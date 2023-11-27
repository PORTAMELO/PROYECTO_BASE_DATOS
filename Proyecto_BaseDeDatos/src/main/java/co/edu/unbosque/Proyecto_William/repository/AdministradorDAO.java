package co.edu.unbosque.Proyecto_William.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.Proyecto_William.model.Administrador;
import java.util.List;


public interface AdministradorDAO extends CrudRepository<Administrador, Integer> {
	
	Administrador findByName(String name);
	Administrador findByCorreo(String correo);
	public ArrayList<Administrador> findAll();
	
}
