package com.devsu.corebancario.service.Impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.devsu.corebancario.model.Cuenta;
import com.devsu.corebancario.model.Movimiento;
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
    final Movimiento movimiento = new Movimiento();
    final Cuenta cuenta = new Cuenta();
    cuenta.setSaldoInicial(0.0);
    cuenta.setMovimientos(List.of(new Movimiento()));
    movimiento.setCuenta(cuenta);
    movimiento.setFecha(LocalDate.of(2020, 1, 1));
    movimiento.setTipoMovimiento("Credito");
    movimiento.setValor(1000.0);
    movimiento.setSaldo(200.0);

    final Movimiento movimiento1 = new Movimiento();
    final Cuenta cuenta1 = new Cuenta();
    cuenta1.setSaldoInicial(500.0);
    cuenta1.setMovimientos(List.of(new Movimiento()));
    movimiento1.setCuenta(cuenta1);
    movimiento1.setFecha(LocalDate.of(2020, 1, 1));
    movimiento1.setTipoMovimiento("Debito");
    movimiento1.setValor(300.0);
    movimiento1.setSaldo(400.0);
    when(mockMovimientoRepository.save(any(Movimiento.class))).thenReturn(movimiento1);

    final Optional<Object> result = movimientoServiceImplUnderTest.crearMovimiento(movimiento);

    assertTrue(result.isPresent());
    assertSame(movimiento1, result.get());

    verify(mockMovimientoRepository, times(1)).save(movimiento);

  }

  @Test
  void testObtenerMovimientoPorId() {
    final Movimiento movimiento1 = new Movimiento();
    final Cuenta cuenta = new Cuenta();
    cuenta.setSaldoInicial(600.0);
    cuenta.setMovimientos(List.of(new Movimiento()));
    movimiento1.setCuenta(cuenta);
    movimiento1.setFecha(LocalDate.of(2020, 1, 1));
    movimiento1.setTipoMovimiento("Credito");
    movimiento1.setValor(500.0);
    movimiento1.setSaldo(700.0);
    final Optional<Movimiento> movimiento = Optional.of(movimiento1);
    when(mockMovimientoRepository.findById(0L)).thenReturn(movimiento);

    final Movimiento result = movimientoServiceImplUnderTest.obtenerMovimientoPorId(0L);

    assertNotNull(result);
    assertSame(movimiento1, result);

    verify(mockMovimientoRepository, times(1)).findById(0L);
  }

  @Test
  void testObtenerMovimientoPorId_MovimientoRepositoryReturnsAbsent() {
    when(mockMovimientoRepository.findById(0L)).thenReturn(Optional.empty());

    final Movimiento result = movimientoServiceImplUnderTest.obtenerMovimientoPorId(0L);

    assertThat(result).isNull();
  }

  @Test
  void testObtenerMovimientos() {
    final Movimiento movimiento = new Movimiento();
    final Cuenta cuenta = new Cuenta();
    cuenta.setSaldoInicial(100.0);
    cuenta.setMovimientos(List.of(new Movimiento()));
    movimiento.setCuenta(cuenta);
    movimiento.setFecha(LocalDate.of(2020, 1, 1));
    movimiento.setTipoMovimiento("Credito");
    movimiento.setValor(2000.0);
    movimiento.setSaldo(100.0);
    final List<Movimiento> movimientoList = List.of(movimiento);
    when(mockMovimientoRepository.findAll()).thenReturn(movimientoList);

    final List<Movimiento> result = movimientoServiceImplUnderTest.obtenerMovimientos();

    assertThat(result).isEqualTo(movimientoList);
    verify(mockMovimientoRepository, times(1)).findAll();
  }

  @Test
  void testObtenerMovimientos_MovimientoRepositoryReturnsNoItems() {
    when(mockMovimientoRepository.findAll()).thenReturn(Collections.emptyList());

    final List<Movimiento> result = movimientoServiceImplUnderTest.obtenerMovimientos();

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
    final Movimiento movimiento = new Movimiento();
    final Cuenta cuenta = new Cuenta();
    cuenta.setSaldoInicial(800.0);
    cuenta.setMovimientos(List.of(new Movimiento()));
    movimiento.setCuenta(cuenta);
    movimiento.setFecha(LocalDate.of(2020, 1, 1));
    movimiento.setTipoMovimiento("Credito");
    movimiento.setValor(500.0);
    movimiento.setSaldo(1300.0);
    final List<Movimiento> movimientoList = List.of(movimiento);
    when(mockMovimientoRepository.findAllByCuenta_Id(0L)).thenReturn(movimientoList);

    final List<Movimiento> result = movimientoServiceImplUnderTest.findAllMovimientosPorCuentaId(
        0L);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(movimientoList);
    verify(mockMovimientoRepository, times(1)).findAllByCuenta_Id(0L);
  }

  @Test
  void testFindAllMovimientosPorCuentaId_MovimientoRepositoryReturnsNoItems() {
    when(mockMovimientoRepository.findAllByCuenta_Id(0L)).thenReturn(Collections.emptyList());

    final List<Movimiento> result = movimientoServiceImplUnderTest.findAllMovimientosPorCuentaId(
        0L);

    assertThat(result).isEqualTo(Collections.emptyList());
  }
}
