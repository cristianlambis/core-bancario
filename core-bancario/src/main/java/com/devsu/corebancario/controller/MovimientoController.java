package com.devsu.corebancario.controller;

import com.devsu.corebancario.dto.MovimientoDTO;
import com.devsu.corebancario.model.Account;
import com.devsu.corebancario.model.Movement;
import com.devsu.corebancario.service.ICuentaService;
import com.devsu.corebancario.service.IMovimientoService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

  private final IMovimientoService movimientoService;
  private final ICuentaService cuentaService;

  public MovimientoController(IMovimientoService movimientoService, ICuentaService cuentaService) {
    this.movimientoService = movimientoService;
    this.cuentaService = cuentaService;
  }

  @PostMapping
  public ResponseEntity<Object> crearMovimiento(@RequestBody MovimientoDTO movimientoDTO) {

    final Movement movimiento = new Movement();
    movimiento.setMovementType(movimientoDTO.getTipoMovimiento());
    movimiento.setDate(movimientoDTO.getFecha());
    movimiento.setValor(movimientoDTO.getValor());

    final Optional<Account> cuenta = cuentaService.obtenerCuentaPorNumeroCuenta(
        movimientoDTO.getCuenta().getNumeroCuenta());

    if (cuenta.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    movimiento.setCuenta(cuenta.get());

    Optional<Object> movimientoCreado = movimientoService.crearMovimiento(movimiento);

    if (movimientoCreado.isEmpty() && movimientoCreado.get() instanceof Movement) {
      return ResponseEntity.created(
              URI.create("/movimientos/" + ((Movement) movimientoCreado.get()).getId()))
          .body(movimientoCreado);
    }
    return ResponseEntity.ok(movimientoCreado.get());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Movement> obtenerMovimientoPorId(@PathVariable Long id) {
    Movement movimiento = movimientoService.obtenerMovimientoPorId(id);
    if (movimiento == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(movimiento);
  }

  @GetMapping
  public ResponseEntity<List<Movement>> obtenerTodosLosMovimientos() {
    List<Movement> movimientos = movimientoService.obtenerMovimientos();
    if (movimientos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(movimientos);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Movement> actualizarParcialmenteMovimiento(@PathVariable Long id,
      @RequestBody Map<String, Object> campos) {
    Movement movimientoActualizado = movimientoService.actualizarParcialmenteMovimiento(id,
        campos);
    if (movimientoActualizado == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(movimientoActualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
    movimientoService.eliminarMovimiento(id);
    return ResponseEntity.noContent().build();
  }
}
