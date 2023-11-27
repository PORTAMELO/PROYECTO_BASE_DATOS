package co.edu.unbosque.Proyecto_William.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.Proyecto_William.model.Pelicula;
import co.edu.unbosque.Proyecto_William.repository.PeliculaDAO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/PeliculasController")
@Transactional
public class PeliculaController {

	@Autowired
	PeliculaDAO peliculaDAO;

	@PostMapping("/AgregarPelicula")
	public ResponseEntity<String> pdf(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Por favor, seleccione un archivo.");
		}

		try {
			java.io.InputStream inputStream = file.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split("::");
				if (data.length >= 3) {
					Integer idPelicula = Integer.parseInt(data[0]);
					String nombrePelicula = data[1];
					String generos[] = data[2].split("\\|");
					String genero = "";
					for (int i = 0; i < generos.length - 1; i++) {
						genero += generos[i] + "  -  ";
					}

					genero += generos[generos.length - 1];

					Pelicula nuevaPelicula = new Pelicula(idPelicula, nombrePelicula, genero);
					peliculaDAO.save(nuevaPelicula);

				}
			}

			reader.close();
			return ResponseEntity.status(HttpStatus.CREATED).body("SZ");
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo .dat.");
		}
	}
	
	@GetMapping("/GetAllPeliculas")
	public ResponseEntity<List<Pelicula>> getAllPeliculas() {
		Iterable<Pelicula> peliculas = peliculaDAO.findAll();
		List<Pelicula> listaPeliculas = new ArrayList<Pelicula>();
		peliculas.forEach(listaPeliculas::add);
		return ResponseEntity.status(HttpStatus.OK).body(listaPeliculas);
	}

}
