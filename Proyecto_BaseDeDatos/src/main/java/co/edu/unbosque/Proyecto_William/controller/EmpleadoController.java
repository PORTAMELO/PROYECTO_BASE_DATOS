package co.edu.unbosque.Proyecto_William.controller;


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

import co.edu.unbosque.Proyecto_William.model.Empleado;
import co.edu.unbosque.Proyecto_William.repository.EmpleadoDAO;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/EmpleadoController")
@Transactional
public class EmpleadoController {

	@Autowired
	EmpleadoDAO empleadoDAO;

	@PostMapping(path = "/AddEmpleado")
	public ResponseEntity<String> addEmpleado(@RequestParam Long codigo, @RequestParam String nombreEmpleado,
			@RequestParam String dependencia,@RequestParam String cargo, @RequestParam String fecha, @RequestParam String eps,
			@RequestParam String arl, @RequestParam String pension, @RequestParam Long sueldo) {
		if (empleadoDAO.findByCodigo(codigo) != null) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
					.body("El código del empleado ya está registrado.");
		} else {
			Empleado empleado = new Empleado(null, codigo, nombreEmpleado, dependencia, cargo, fecha, eps, arl, pension,
					sueldo);
			empleadoDAO.save(empleado);
			return ResponseEntity.status(HttpStatus.CREATED).body("Empleado creado con éxito.");
		}
	}

	@DeleteMapping("/DeleteEmpleado/{codigo}")
	public ResponseEntity<String> deleteEmpleado(@PathVariable Long codigo) {
		Empleado empleado = empleadoDAO.findByCodigo(codigo);
		if (empleado != null) {
			empleadoDAO.delete(empleado);
			return ResponseEntity.ok("Empleado eliminado con éxito.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Empleado con código " + codigo + " no encontrado.");
		}
	}

	@GetMapping(path = "/GetAllEmpleado")
	public ResponseEntity<Iterable<Empleado>> getAllPerson() {
	    Iterable<Empleado> lst = empleadoDAO.findAll();
	    return ResponseEntity.status(HttpStatus.OK).body(lst);
	}

}
