package com.example.demo.mongo.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection= "user")
public class User {

	@Id
    private String id;
	private String nombre;
	private String cedula;
	private String fecha_nac;
}
