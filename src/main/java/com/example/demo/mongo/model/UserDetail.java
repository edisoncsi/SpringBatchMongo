package com.example.demo.mongo.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class UserDetail {

	private String nombre;
	private String cedula;
	private String fecha_nac;
}
