package co.edu.unbosque.Proyecto_William.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.Proyecto_William.model.Empleado;

import java.util.ArrayList;


public interface EmpleadoDAO extends CrudRepository<Empleado, Integer>{

	public Empleado findByCodigo(Long codigo);
	
	public void deleteByCodigo(Long codigo);
	
	public ArrayList<Empleado> findAll();
	
}
