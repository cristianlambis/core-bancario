package com.devsu.corebancario.service;


import com.devsu.corebancario.model.Movimiento;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IMovimientoService {

  Optional<Object> crearMovimiento(Movimiento movimiento);

  Movimiento obtenerMovimientoPorId(Long id);

  List<Movimiento> obtenerMovimientos();

  Movimiento actualizarParcialmenteMovimiento(Long id, Map<String, Object> campos);

  void eliminarMovimiento(Long id);

  List<Movimiento> findAllMovimientosPorCuentaId(Long cuentaId);


}
