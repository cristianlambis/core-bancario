package com.devsu.corebancario.controller;

import com.devsu.corebancario.dto.MovimientoDTO;
import com.devsu.corebancario.model.Cuenta;
import com.devsu.corebancario.model.Movimiento;
import com.devsu.corebancario.service.ICuentaService;
import com.devsu.corebancario.service.IMovimientoService;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    final Movimiento movimiento = new Movimiento();
    movimiento.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
    movimiento.setFecha(movimientoDTO.getFecha());
    movimiento.setValor(movimientoDTO.getValor());

    final Optional<Cuenta> cuenta = cuentaService.obtenerCuentaPorNumeroCuenta(
        movimientoDTO.getCuenta().getNumeroCuenta());

    if (cuenta.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    movimiento.setCuenta(cuenta.get());

    Optional<Object> movimientoCreado = movimientoService.crearMovimiento(movimiento);

    if (movimientoCreado.isEmpty() && movimientoCreado.get() instanceof Movimiento) {
      return ResponseEntity.created(
              URI.create("/movimientos/" + ((Movimiento) movimientoCreado.get()).getId()))
          .body(movimientoCreado);
    }
    return ResponseEntity.ok(movimientoCreado.get());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Movimiento> obtenerMovimientoPorId(@PathVariable Long id) {
    Movimiento movimiento = movimientoService.obtenerMovimientoPorId(id);
    if (movimiento == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(movimiento);
  }

  @GetMapping
  public ResponseEntity<List<Movimiento>> obtenerTodosLosMovimientos() {
    List<Movimiento> movimientos = movimientoService.obtenerMovimientos();
    if (movimientos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(movimientos);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Movimiento> actualizarParcialmenteMovimiento(@PathVariable Long id,
      @RequestBody Map<String, Object> campos) {
    Movimiento movimientoActualizado = movimientoService.actualizarParcialmenteMovimiento(id,
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
