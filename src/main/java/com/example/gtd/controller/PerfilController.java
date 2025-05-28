package com.example.gtd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gtd.model.Perfil;
import com.example.gtd.model.PerfilAdmin;
import com.example.gtd.model.PerfilCliente;
import com.example.gtd.model.PerfilDueno;
import com.example.gtd.model.Usuario;
import com.example.gtd.repository.PerfilRepository;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

  @Autowired private PerfilRepository repo;

  @PostMapping("/cliente")
  public ResponseEntity<?> crearCliente(@RequestBody PerfilCliente perfilCliente, @AuthenticationPrincipal Usuario usuario) {
    perfilCliente.setUsuario(usuario);
    repo.save(perfilCliente);
    return ResponseEntity.ok("Perfil cliente creado");
  }

  @PostMapping("/dueno")
  public ResponseEntity<?> crearDueno(@RequestBody PerfilDueno perfilDueno, @AuthenticationPrincipal Usuario usuario) {
    perfilDueno.setUsuario(usuario);
    repo.save(perfilDueno);
    return ResponseEntity.ok("Perfil dueño creado");
  }

  @PostMapping("/admin")
  public ResponseEntity<?> crearAdmin(@RequestBody PerfilAdmin perfilAdmin, @AuthenticationPrincipal Usuario usuario) {
    perfilAdmin.setUsuario(usuario);
    repo.save(perfilAdmin);
    return ResponseEntity.ok("Perfil admin creado");
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> obtener(@PathVariable Long id) {
    Perfil perfil = repo.findById(id).orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    return ResponseEntity.ok(perfil);
  }

  //todo: metodo modificar

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminar(@PathVariable Long id) {
    repo.deleteById(id);
    return ResponseEntity.ok("Perfil eliminado");
  }

}
