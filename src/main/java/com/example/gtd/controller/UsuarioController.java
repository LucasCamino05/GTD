package com.example.gtd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gtd.model.Usuario;
import com.example.gtd.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  @Autowired private UsuarioRepository repo;
  @Autowired private PasswordEncoder encoder;

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody Usuario usuario) {
    usuario.setContrasena(encoder.encode(usuario.getPassword()));
    repo.save(usuario);
    return ResponseEntity.ok("Usuario registrado");
  }

  @GetMapping("/{id}")
  @PreAuthorize("#id == authentication.principal.id")
  public ResponseEntity<?> obtener(@PathVariable Long id) {
    return ResponseEntity.ok(repo.findById(id));
  }

  @PutMapping("/{id}")
  @PreAuthorize("#id == authentication.principal.id")
  public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Usuario actualizacion) {
    Usuario u = repo.findById(id).orElseThrow();
    u.setContrasena(encoder.encode(actualizacion.getPassword()));
    repo.save(u);
    return ResponseEntity.ok("Actualizado");
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("#id == authentication.principal.id")
  public ResponseEntity<?> eliminar(@PathVariable Long id) {
    repo.deleteById(id);
    return ResponseEntity.ok("Eliminado");
  }
}
