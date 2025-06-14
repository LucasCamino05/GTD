package com.example.loki.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.loki.controller.ClienteController;
import com.example.loki.controller.VendedorController;

@ControllerAdvice(basePackageClasses = {VendedorController.class, ClienteController.class})
public class PerfilExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<String> manejadorPerfilNotFound(PerfilNotFound ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
