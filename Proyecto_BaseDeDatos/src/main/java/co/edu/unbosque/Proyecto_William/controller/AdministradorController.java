package co.edu.unbosque.Proyecto_William.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.Proyecto_William.model.Administrador;
import co.edu.unbosque.Proyecto_William.repository.AdministradorDAO;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/AdministradorController")
@Transactional
public class AdministradorController {

	@Autowired
	AdministradorDAO adDAO;

	@PostMapping(path = "/addAdministrador")
	public ResponseEntity<String> addAdmin(@RequestParam String name, @RequestParam String password,
			@RequestParam String correo) {
		Administrador aux = adDAO.findByCorreo(correo);
		Administrador aux2 = adDAO.findByName(name);

		if (correo.contains("@") && correo.contains(".com")) {
			if (aux == null) {
				if (aux2 == null) {
					Administrador add = new Administrador(null, name, password, correo);
					adDAO.save(add);
					return ResponseEntity.status(HttpStatus.CREATED).body("Creado");
				} else {
					return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Nombre ya se encuentra en uso");
				}
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Correo ya se encuentra en uso");
			}
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Correo no valido");
		}
	}

	@GetMapping("/getAdministrador/{name}")
	public Boolean getAdmin(@RequestParam String password, @PathVariable String name) {
		ArrayList<Administrador> lst = adDAO.findAll();

		for (int i = 0; i < lst.size(); i++) {
			if (lst.get(i).getName().equals(name) && lst.get(i).getPassword().equals(password)) {
				return true;
			}
		}

		return false;
	}

}
