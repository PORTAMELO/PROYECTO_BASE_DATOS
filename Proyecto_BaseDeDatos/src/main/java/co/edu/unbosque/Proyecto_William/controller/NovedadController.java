package co.edu.unbosque.Proyecto_William.controller;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.Proyecto_William.model.Empleado;
import co.edu.unbosque.Proyecto_William.model.Novedad;
import co.edu.unbosque.Proyecto_William.repository.EmpleadoDAO;
import co.edu.unbosque.Proyecto_William.repository.NovedadDAO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/NovedadesController")
@Transactional
public class NovedadController {

	@Autowired
	NovedadDAO novedadDAO;

	@Autowired
	EmpleadoDAO empleadoDAO;

	@PostMapping(path = "/AddNovedad")
	public ResponseEntity<String> addNovedad(@RequestParam Long codigoEmpleado, @RequestParam String novedadIncapacidad,
			@RequestParam String novedadVacaciones, @RequestParam int diasTrabajados, @RequestParam int diasIncapacidad,
			@RequestParam int diasVacaciones, @RequestParam String inicioVacaciones, @RequestParam String finVacaciones,
			@RequestParam String inicioIncapacidad, @RequestParam String finIncapacidad, @RequestParam int bonificacion,
			@RequestParam int transporte) {
		Empleado empleado = empleadoDAO.findByCodigo(codigoEmpleado);
		if (empleado == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado.");
		} else {
			Novedad novedad = new Novedad(null, empleado, novedadIncapacidad, novedadVacaciones, diasTrabajados,
					diasIncapacidad, diasVacaciones, inicioVacaciones, finVacaciones, inicioIncapacidad, finIncapacidad,
					bonificacion, transporte);
			novedadDAO.save(novedad);
			return ResponseEntity.status(HttpStatus.CREATED).body("Novedad creada con éxito.");
		}
	}

	@DeleteMapping("/DeleteNovedad/{id}")
	public ResponseEntity<String> deleteNovedad(@PathVariable Integer id) {
		Novedad novedad = novedadDAO.findById(id).orElse(null);
		if (novedad != null) {
			novedadDAO.delete(novedad);
			return ResponseEntity.ok("Novedad eliminada con éxito.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Novedad con ID " + id + " no encontrada.");
		}
	}

	@GetMapping(path = "/GetAllNovedad")
	public ResponseEntity<Iterable<Novedad>> getAllNovedad() {
		Iterable<Novedad> lst = novedadDAO.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(lst);
	}

}
