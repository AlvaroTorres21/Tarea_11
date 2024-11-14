package ejercicio;

import java.util.Date;

public class Alumno {
	private String nombre, apellido1, apellido2, ciclo, grupo, curso;
	private int nia;
	private Date fecha_nac;
	private char genero;
	
	
	public Alumno(int nia, String nombre, String apellido1, String apellido2,  char genero, Date fecha_nac, String ciclo, String curso, String grupo
			) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.ciclo = ciclo;
		this.grupo = grupo;
		this.curso = curso;
		this.nia = nia;
		this.fecha_nac = fecha_nac;
		this.genero = genero;
	}
	
	public Alumno() {
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido1() {
		return apellido1;
	}


	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}


	public String getApellido2() {
		return apellido2;
	}


	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}


	public String getCiclo() {
		return ciclo;
	}


	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}


	public String getGrupo() {
		return grupo;
	}


	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}


	public String getCurso() {
		return curso;
	}


	public void setCurso(String curso) {
		this.curso = curso;
	}


	public int getNia() {
		return nia;
	}


	public void setNia(int nia) {
		this.nia = nia;
	}


	public Date getFecha_nac() {
		return fecha_nac;
	}


	public void setFecha_nac(Date fecha_nac) {
		this.fecha_nac = fecha_nac;
	}


	public char getGenero() {
		return genero;
	}


	public void setGenero(char genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return " [nia=" + nia +", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", genero=" + genero + ", fecha_nac=" + fecha_nac + ", ciclo=" + ciclo +", curso=" + curso +
				", grupo=" + grupo + "]";
	}

}
