package com.devsu.corebancario.service.Impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.devsu.corebancario.model.Account;
import com.devsu.corebancario.model.Movement;
import com.devsu.corebancario.repository.MovimientoRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceImplTest {

  @Mock
  private MovimientoRepository mockMovimientoRepository;

  private MovimientoServiceImpl movimientoServiceImplUnderTest;

  @BeforeEach
  void setUp() {
    movimientoServiceImplUnderTest = new MovimientoServiceImpl(mockMovimientoRepository);
  }

  @Test
  void testCrearMovimiento() {
    final Movement movimiento = new Movement();
    final Account cuenta = new Account();
    cuenta.setSaldoInicial(0.0);
    cuenta.setMovimientos(List.of(new Movement()));
    movimiento.setCuenta(cuenta);
    movimiento.setDate(LocalDate.of(2020, 1, 1));
    movimiento.setMovementType("Credito");
    movimiento.setValor(1000.0);
    movimiento.setSaldo(200.0);

    final Movement movimiento1 = new Movement();
    final Account cuenta1 = new Account();
    cuenta1.setSaldoInicial(500.0);
    cuenta1.setMovimientos(List.of(new Movement()));
    movimiento1.setCuenta(cuenta1);
    movimiento1.setDate(LocalDate.of(2020, 1, 1));
    movimiento1.setMovementType("Debito");
    movimiento1.setValor(300.0);
    movimiento1.setSaldo(400.0);
    when(mockMovimientoRepository.save(any(Movement.class))).thenReturn(movimiento1);

    final Optional<Object> result = movimientoServiceImplUnderTest.crearMovimiento(movimiento);

    assertTrue(result.isPresent());
    assertSame(movimiento1, result.get());

    verify(mockMovimientoRepository, times(1)).save(movimiento);

  }

  @Test
  void testObtenerMovimientoPorId() {
    final Movement movimiento1 = new Movement();
    final Account cuenta = new Account();
    cuenta.setSaldoInicial(600.0);
    cuenta.setMovimientos(List.of(new Movement()));
    movimiento1.setCuenta(cuenta);
    movimiento1.setDate(LocalDate.of(2020, 1, 1));
    movimiento1.setMovementType("Credito");
    movimiento1.setValor(500.0);
    movimiento1.setSaldo(700.0);
    final Optional<Movement> movimiento = Optional.of(movimiento1);
    when(mockMovimientoRepository.findById(0L)).thenReturn(movimiento);

    final Movement result = movimientoServiceImplUnderTest.obtenerMovimientoPorId(0L);

    assertNotNull(result);
    assertSame(movimiento1, result);

    verify(mockMovimientoRepository, times(1)).findById(0L);
  }

  @Test
  void testObtenerMovimientoPorId_MovimientoRepositoryReturnsAbsent() {
    when(mockMovimientoRepository.findById(0L)).thenReturn(Optional.empty());

    final Movement result = movimientoServiceImplUnderTest.obtenerMovimientoPorId(0L);

    assertThat(result).isNull();
  }

  @Test
  void testObtenerMovimientos() {
    final Movement movimiento = new Movement();
    final Account cuenta = new Account();
    cuenta.setSaldoInicial(100.0);
    cuenta.setMovimientos(List.of(new Movement()));
    movimiento.setCuenta(cuenta);
    movimiento.setDate(LocalDate.of(2020, 1, 1));
    movimiento.setMovementType("Credito");
    movimiento.setValor(2000.0);
    movimiento.setSaldo(100.0);
    final List<Movement> movimientoList = List.of(movimiento);
    when(mockMovimientoRepository.findAll()).thenReturn(movimientoList);

    final List<Movement> result = movimientoServiceImplUnderTest.obtenerMovimientos();

    assertThat(result).isEqualTo(movimientoList);
    verify(mockMovimientoRepository, times(1)).findAll();
  }

  @Test
  void testObtenerMovimientos_MovimientoRepositoryReturnsNoItems() {
    when(mockMovimientoRepository.findAll()).thenReturn(Collections.emptyList());

    final List<Movement> result = movimientoServiceImplUnderTest.obtenerMovimientos();

    assertThat(result).isEqualTo(Collections.emptyList());
  }
  

  @Test
  void testActualizarParcialmenteMovimiento() {
    assertThat(movimientoServiceImplUnderTest.actualizarParcialmenteMovimiento(0L,
        Map.ofEntries(Map.entry("value", "value")))).isNull();
  }

  @Test
  void testEliminarMovimiento() {
    movimientoServiceImplUnderTest.eliminarMovimiento(0L);

    verify(mockMovimientoRepository).deleteById(0L);
  }

  @Test
  void testFindAllMovimientosPorCuentaId() {
    final Movement movimiento = new Movement();
    final Account cuenta = new Account();
    cuenta.setSaldoInicial(800.0);
    cuenta.setMovimientos(List.of(new Movement()));
    movimiento.setCuenta(cuenta);
    movimiento.setDate(LocalDate.of(2020, 1, 1));
    movimiento.setMovementType("Credito");
    movimiento.setValor(500.0);
    movimiento.setSaldo(1300.0);
    final List<Movement> movimientoList = List.of(movimiento);
    when(mockMovimientoRepository.findAllByCuenta_Id(0L)).thenReturn(movimientoList);

    final List<Movement> result = movimientoServiceImplUnderTest.findAllMovimientosPorCuentaId(
        0L);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(movimientoList);
    verify(mockMovimientoRepository, times(1)).findAllByCuenta_Id(0L);
  }

  @Test
  void testFindAllMovimientosPorCuentaId_MovimientoRepositoryReturnsNoItems() {
    when(mockMovimientoRepository.findAllByCuenta_Id(0L)).thenReturn(Collections.emptyList());

    final List<Movement> result = movimientoServiceImplUnderTest.findAllMovimientosPorCuentaId(
        0L);

    assertThat(result).isEqualTo(Collections.emptyList());
  }
}
