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

  /*
   * luego de que el vendedor otorga los permisos
   * la api de MP redirige a este endpoint
   * que recibe el codigo y el state y luego llama al metodo intercambiarToken
   */
  @GetMapping("/oauth-callback")
  public ResponseEntity<?> callbackOauth(@RequestParam String code, @RequestParam String state) {
    Long vendedorId = Long.parseLong(state);
    return intercambiarToken(code, vendedorId);
  }

  /*
   * este metodo recibe
   * el codigo y el vendedorId
   * usa esos datos para
   * obtener: access_token, refresh_token, user_id
   * y asignar los campos correspondientes al vendedor
   */
  private ResponseEntity<?> intercambiarToken(String code, Long vendedorId) {
    Map<String, Object> body = Map.of(
        "grant_type", "authorization_code",
        "client_id", CLIENT_ID,
        "client_secret", CLIENT_SECRET,
        "code", code,
        "redirect_uri", REDIRECT_URI);

    Map<String, Object> tokenResponse = WebClient.create()
        .post()
        .uri("https://api.mercadopago.com/oauth/token")
        .bodyValue(body)
        .retrieve()
        .bodyToMono(Map.class)
        .block();

    Vendedor vendedor = vendedorRepository.findById(vendedorId).orElseThrow();
    vendedor.setAccessToken((String) tokenResponse.get("access_token"));
    vendedor.setRefreshToken((String) tokenResponse.get("refresh_token"));
    vendedor.setMpUserId(Long.valueOf(tokenResponse.get("user_id").toString()));

    Long expiresIn = Long.valueOf(tokenResponse.get("expires_in").toString());
    vendedor.setTokenExpiresAt(System.currentTimeMillis() + expiresIn * 1000);

    vendedorRepository.save(vendedor);

    return ResponseEntity.ok("Tokens guardados correctamente");
  }

  /*
   * este endpoint recibe el id del vendedor como parametro
   * y utiliza el refresh_token para actualizar sus tokens
   */
  @PostMapping("/refresh-token/{vendedorId}")
  public ResponseEntity<?> refrescarToken(@PathVariable Long vendedorId) {
    Vendedor vendedor = vendedorRepository.findById(vendedorId).orElseThrow();

    Map<String, Object> body = Map.of(
        "grant_type", "refresh_token",
        "client_id", CLIENT_ID,
        "client_secret", CLIENT_SECRET,
        "refresh_token", vendedor.getRefreshToken());

    Map<String, Object> tokenResponse = WebClient.create()
        .post()
        .uri("https://api.mercadopago.com/oauth/token")
        .bodyValue(body)
        .retrieve()
        .bodyToMono(Map.class)
        .block();

    vendedor.setAccessToken((String) tokenResponse.get("access_token"));
    vendedor.setRefreshToken((String) tokenResponse.get("refresh_token"));
    vendedor.setMpUserId(Long.valueOf(tokenResponse.get("user_id").toString()));

    Long expiresIn = Long.valueOf(tokenResponse.get("expires_in").toString());
    vendedor.setTokenExpiresAt(System.currentTimeMillis() + expiresIn * 1000);

    vendedorRepository.save(vendedor);

    return ResponseEntity.ok("Tokens refrescados correctamente");
  }
}
