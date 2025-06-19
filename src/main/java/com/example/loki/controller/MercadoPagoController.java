package com.example.loki.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.loki.model.entities.Producto;
import com.example.loki.model.entities.Vendedor;
import com.example.loki.repository.VendedorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mp")
@RequiredArgsConstructor
public class MercadoPagoController {

  @Autowired
  private final VendedorRepository vendedorRepository;

  // estan en la seccion credenciales produccion
  private final String CLIENT_ID = "<client secret>";
  private final String CLIENT_SECRET = "<client secret>";

  // parece que no funciona con localhost por eso se usa render.com
  private final String REDIRECT_URI = "https://loki-6etb.onrender.com/api/oauth-callback";
  private final String CODE_API = "https://loki-6etb.onrender.com/api/oauth-code";

  // devuelve url donde el vendedor otorga los permisos para recibir pagos
  @GetMapping("/auth-url/{vendedorId}")
  public ResponseEntity<String> getAuthUrl(@PathVariable Long vendedorId) {
    String url = "https://auth.mercadopago.com.ar/authorization"
        + "?client_id=" + CLIENT_ID
        + "&response_type=code"
        + "&platform_id=mp"
        + "&redirect_uri=" + REDIRECT_URI
        + "&state=" + vendedorId
    return ResponseEntity.ok(url);
    /*
     notar que en el parametro 'state' se guarda el id del vendedor
     */
  }
}
