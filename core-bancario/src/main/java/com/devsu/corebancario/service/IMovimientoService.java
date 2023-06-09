package com.devsu.corebancario.service;


import com.devsu.corebancario.model.Movement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IMovimientoService {

  Optional<Object> crearMovimiento(Movement movimiento);

  Movement obtenerMovimientoPorId(Long id);

  List<Movement> obtenerMovimientos();

  Movement actualizarParcialmenteMovimiento(Long id, Map<String, Object> campos);

  void eliminarMovimiento(Long id);

  List<Movement> findAllMovimientosPorCuentaId(Long cuentaId);


}
