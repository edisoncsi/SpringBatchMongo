package com.example.demo.mongo.model;

public class UserDetail {

	private String nombre;
	
	private String cedula;
	
	private String fecha_nac;

	public UserDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDetail(String nombre, String cedula, String fecha_nac) {
		super();
		this.nombre = nombre;
		this.cedula = cedula;
		this.fecha_nac = fecha_nac;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(String fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", cedula=" + cedula + ", fecha_nac=" + fecha_nac + "]";
	}
}
