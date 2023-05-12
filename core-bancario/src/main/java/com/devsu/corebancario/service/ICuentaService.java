package com.devsu.corebancario.service;

import com.devsu.corebancario.model.Cliente;
import com.devsu.corebancario.model.Cuenta;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICuentaService {

  List<Cuenta> obtenerCuentas();

  public Optional<Cuenta> obtenerCuentaPorId(Long id);

  Cuenta crearCuenta(Cuenta cuenta);

  Cuenta actualizarCuenta(Long id, Cuenta cuenta);

  Cuenta actualizarParcialmenteCuenta(Long id, Map<String, Object> campos);

  void eliminarCuenta(Long id);

  List<Cuenta> findCuentasByClienteId(Cliente cliente);


}
