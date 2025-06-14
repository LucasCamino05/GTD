package com.example.loki.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.loki.controller.ClienteController;
import com.example.loki.controller.VendedorController;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.MediaType;

@ControllerAdvice(basePackageClasses = {VendedorController.class, ClienteController.class})
// @ControllerAdvice // descomentar para que sea global
public class GlobalExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<?> manejadorArgumentoInvalido(MethodArgumentNotValidException e) {
    Map<String, Object> respuesta = new HashMap<>();
    Map<String, String> errores = new HashMap<>();

    e.getBindingResult().getAllErrors().forEach((error) -> {
      String atributo = ((FieldError) error).getField();
      String mensaje = error.getDefaultMessage();
      errores.put(atributo, mensaje);
    });

    respuesta.put("status", "error");
    respuesta.put("message", "datos invalidos");
    respuesta.put("errors", errores);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(respuesta);
  }

  @ExceptionHandler
  public ResponseEntity<?> manejadorErrorValidator(ConstraintViolationException e) {
    Map<String, Object> respuesta = new HashMap<>();
    Map<String, String> errores = new HashMap<>();

    e.getConstraintViolations().forEach(violation -> {
      String atributo = violation.getPropertyPath().toString();
      String mensaje = violation.getMessage();
      errores.put(atributo, mensaje);
    });

    respuesta.put("status", "error");
    respuesta.put("message", "datos invalidos");
    respuesta.put("errors", errores);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(respuesta);
  }
}
