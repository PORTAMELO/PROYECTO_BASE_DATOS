package co.edu.unbosque.Proyecto_William.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.Proyecto_William.model.Novedad;
import java.util.List;
import java.util.Optional;


public interface NovedadDAO extends CrudRepository<Novedad, Integer>{

	public ArrayList<Novedad> findAll();
	
	List<Novedad> findByEmpleadoCodigo(Long codigoEmpleado);
	
	public void deleteByEmpleadoCodigo(Long codigoEmpleado);
	
}
