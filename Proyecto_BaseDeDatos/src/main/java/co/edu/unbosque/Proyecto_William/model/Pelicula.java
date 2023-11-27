package co.edu.unbosque.Proyecto_William.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pelicula {

	@Id
	private Integer idPelicula;
	
	@Column(nullable = false)
	private String nombrePelicula;
	
	@Column(nullable = false)
	private String genero;
	
	public Pelicula() {
		// TODO Auto-generated constructor stub
	}

	public Pelicula(Integer idPelicula, String nombrePelicula, String genero) {
		super();
		this.idPelicula = idPelicula;
		this.nombrePelicula = nombrePelicula;
		this.genero = genero;
	}

	public Integer getIdPelicula() {
		return idPelicula;
	}

	public void setIdPelicula(Integer idPelicula) {
		this.idPelicula = idPelicula;
	}

	public String getNombrePelicula() {
		return nombrePelicula;
	}

	public void setNombrePelicula(String nombrePelicula) {
		this.nombrePelicula = nombrePelicula;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
	
}
