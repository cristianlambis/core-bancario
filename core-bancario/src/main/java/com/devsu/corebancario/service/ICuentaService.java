package com.devsu.corebancario.service;

import com.devsu.corebancario.model.Cliente;
import com.devsu.corebancario.model.Account;
import java.util.List;
import java.util.Optional;

public interface ICuentaService {

  List<Account> obtenerCuentas();

  Optional<Account> obtenerCuentaPorId(Long id);

  Optional<Account> obtenerCuentaPorNumeroCuenta(String numeroCuenta);

  Account crearCuenta(Account cuenta);

  Account actualizarCuenta(Long id, Account cuenta);


  void eliminarCuenta(Long id);

  List<Account> findCuentasByClienteId(Cliente cliente);


}
