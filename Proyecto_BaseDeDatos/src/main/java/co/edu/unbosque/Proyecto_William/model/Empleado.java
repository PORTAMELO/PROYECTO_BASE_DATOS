package co.edu.unbosque.Proyecto_William.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false, unique = true)
	private Long codigo;

	@Column(nullable = false)
	private String nombreEmpleado;

	@Column(nullable = false)
	private String dependencia;

	@Column(nullable = false)
	private String cargo;
	
	@Column(nullable = false)
	private String fecha;

	@Column(nullable = false)
	private String eps;

	@Column(nullable = false)
	private String arl;

	@Column(nullable = false)
	private String pension;

	@Column(nullable = false)
	private Long sueldo;

	@OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Novedad> novedades;

	public Empleado() {
		// TODO Auto-generated constructor stub
	}

	public Empleado(Integer id, Long codigo, String nombreEmpleado, String dependencia, String cargo, String fecha,
			String eps, String arl, String pension, Long sueldo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombreEmpleado = nombreEmpleado;
		this.dependencia = dependencia;
		this.cargo = cargo;
		this.fecha = fecha;
		this.eps = eps;
		this.arl = arl;
		this.pension = pension;
		this.sueldo = sueldo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEps() {
		return eps;
	}

	public void setEps(String eps) {
		this.eps = eps;
	}

	public String getArl() {
		return arl;
	}

	public void setArl(String arl) {
		this.arl = arl;
	}

	public String getPension() {
		return pension;
	}

	public void setPension(String pension) {
		this.pension = pension;
	}

	public Long getSueldo() {
		return sueldo;
	}

	public void setSueldo(Long sueldo) {
		this.sueldo = sueldo;
	}

	public List<Novedad> getNovedades() {
		return novedades;
	}

	public void setNovedades(List<Novedad> novedades) {
		this.novedades = novedades;
	}

	@Override
	public String toString() {
		return "Empleados [id=" + id + ", codigo=" + codigo + ", nombreEmpleado=" + nombreEmpleado + ", dependecia="
				+ dependencia + ", fecha=" + fecha + ", eps=" + eps + ", arl=" + arl + ", pension=" + pension
				+ ", sueldo=" + sueldo + "]";
	}

}
