package com.devsu.corebancario.controller;

import com.devsu.corebancario.dto.CuentaDTO;
import com.devsu.corebancario.model.Cliente;
import com.devsu.corebancario.model.Cuenta;
import com.devsu.corebancario.service.IClienteService;
import com.devsu.corebancario.service.ICuentaService;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {


  private final ICuentaService cuentaService;
  private final IClienteService clienteService;

  public CuentaController(ICuentaService cuentaService, IClienteService clienteService) {
    this.cuentaService = cuentaService;
    this.clienteService = clienteService;
  }


  @GetMapping
  public ResponseEntity<List<Cuenta>> obtenerCuentas() {
    List<Cuenta> cuentas = cuentaService.obtenerCuentas();
    if (cuentas.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(cuentas);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cuenta> obtenerCuentaPorId(@PathVariable("id") Long id) {
    Optional<Cuenta> cuentaOptional = cuentaService.obtenerCuentaPorId(id);
    return cuentaOptional.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Cuenta> crearCuenta(@RequestBody CuentaDTO cuentaDTO) {

    final Cuenta cuenta = new Cuenta();
    cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
    cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
    cuenta.setEstado(cuentaDTO.getEstado());
    cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());

    final Cliente cliente = clienteService.obtenerClientePorClienteId(
        cuentaDTO.getCliente().getClienteId());

    if (cliente == null) {
      return ResponseEntity.notFound().build();
    }

    cuenta.setCliente(cliente);
    Cuenta cuentaCreada = cuentaService.crearCuenta(cuenta);
    return ResponseEntity.created(URI.create("/cuentas/" + cuentaCreada.getId()))
        .body(cuentaCreada);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable("id") Long id,
      @RequestBody CuentaDTO cuentaDTO) {
    final Cuenta cuenta = new Cuenta();
    cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
    cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
    cuenta.setEstado(cuentaDTO.getEstado());
    cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
    Cuenta cuentaActualizada = cuentaService.actualizarCuenta(id, cuenta);
    if (!Optional.ofNullable(cuentaActualizada).isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(cuentaActualizada);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarCuenta(@PathVariable("id") Long id) {
    cuentaService.eliminarCuenta(id);
    return ResponseEntity.noContent().build();
  }
}