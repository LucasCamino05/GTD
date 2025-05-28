package com.example.gtd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GtdApplication {
	public static void main(String[] args) {
		SpringApplication.run(GtdApplication.class, args);
    /*
    # crear usuario
    curl -X POST http://localhost:8080/api/usuarios \
    -H "Content-Type: application/json" \
    -d '{"nombre": "juan_perez", "contrasena": "clave", "rol": "DUENO"}'
    
    # crear perfil al usuario
    curl -u usuario1:clave -X POST http://localhost:8080/api/perfiles/dueno \
    -H "Content-Type: application/json" \
    -d '{
      "nombre": "Juan",
      "apellido": "Perez",
      "telefono": "123456789",
      "correo": "juan@example.com",
      "cvu": "1234567890123456789012"
    }'
     */
	}

}
