package co.edu.unbosque.Proyecto_William.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Novedad {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "codigo_empleado", referencedColumnName = "codigo")
	private Empleado empleado;

	@Column(nullable = true)
	private String novedadIncapacidad;

	@Column(nullable = true)
	private String novedadVacaciones;

	@Column(nullable = true)
	private int diasTrabajados;

	@Column(nullable = true)
	private int diasIncapacidad;

	@Column(nullable = true)
	private int diasVacaciones;

	@Column(nullable = true)
	private String inicioVacaciones;

	@Column(nullable = true)
	private String finVacaciones;

	@Column(nullable = true)
	private String inicioIncapacidad;

	@Column(nullable = true)
	private String finIncapacidad;

	@Column(nullable = true)
	private int bonificacion;

	@Column(nullable = true)
	private int transporte;

	public Novedad() {
		// TODO Auto-generated constructor stub
	}

	public Novedad(Integer id, Empleado empleado, String novedadIncapacidad, String novedadVacaciones,
			int diasTrabajados, int diasIncapacidad, int diasVacaciones, String inicioVacaciones, String finVacaciones,
			String inicioIncapacidad, String finIncapacidad, int bonificacion, int transporte) {
		super();
		this.id = id;
		this.empleado = empleado;
		this.novedadIncapacidad = novedadIncapacidad;
		this.novedadVacaciones = novedadVacaciones;
		this.diasTrabajados = diasTrabajados;
		this.diasIncapacidad = diasIncapacidad;
		this.diasVacaciones = diasVacaciones;
		this.inicioVacaciones = inicioVacaciones;
		this.finVacaciones = finVacaciones;
		this.inicioIncapacidad = inicioIncapacidad;
		this.finIncapacidad = finIncapacidad;
		this.bonificacion = bonificacion;
		this.transporte = transporte;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public String getNovedadIncapacidad() {
		return novedadIncapacidad;
	}

	public void setNovedadIncapacidad(String novedadIncapacidad) {
		this.novedadIncapacidad = novedadIncapacidad;
	}

	public String getNovedadVacaciones() {
		return novedadVacaciones;
	}

	public void setNovedadVacaciones(String novedadVacaciones) {
		this.novedadVacaciones = novedadVacaciones;
	}

	public int getDiasTrabajados() {
		return diasTrabajados;
	}

	public void setDiasTrabajados(int diasTrabajados) {
		this.diasTrabajados = diasTrabajados;
	}

	public int getDiasIncapacidad() {
		return diasIncapacidad;
	}

	public void setDiasIncapacidad(int diasIncapacidad) {
		this.diasIncapacidad = diasIncapacidad;
	}

	public int getDiasVacaciones() {
		return diasVacaciones;
	}

	public void setDiasVacaciones(int diasVacaciones) {
		this.diasVacaciones = diasVacaciones;
	}

	public String getInicioVacaciones() {
		return inicioVacaciones;
	}

	public void setInicioVacaciones(String inicioVacaciones) {
		this.inicioVacaciones = inicioVacaciones;
	}

	public String getFinVacaciones() {
		return finVacaciones;
	}

	public void setFinVacaciones(String finVacaciones) {
		this.finVacaciones = finVacaciones;
	}

	public String getInicioIncapacidad() {
		return inicioIncapacidad;
	}

	public void setInicioIncapacidad(String inicioIncapacidad) {
		this.inicioIncapacidad = inicioIncapacidad;
	}

	public String getFinIncapacidad() {
		return finIncapacidad;
	}

	public void setFinIncapacidad(String finIncapacidad) {
		this.finIncapacidad = finIncapacidad;
	}

	public int getBonificacion() {
		return bonificacion;
	}

	public void setBonificacion(int bonificacion) {
		this.bonificacion = bonificacion;
	}

	public int getTransporte() {
		return transporte;
	}

	public void setTransporte(int transporte) {
		this.transporte = transporte;
	}

	@Override
	public String toString() {
		return "Novedad [id=" + id + ", empleado=" + empleado + ", novedadIncapacidad=" + novedadIncapacidad
				+ ", novedadVacaciones=" + novedadVacaciones + ", diasTrabajados=" + diasTrabajados
				+ ", diasIncapacidad=" + diasIncapacidad + ", diasVacaciones=" + diasVacaciones + ", inicioVacaciones="
				+ inicioVacaciones + ", finVacaciones=" + finVacaciones + ", inicioIncapacidad=" + inicioIncapacidad
				+ ", finIncapacidad=" + finIncapacidad + ", bonificacion=" + bonificacion + ", transporte=" + transporte
				+ "]";
	}

}
