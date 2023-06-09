package com.devsu.corebancario.service.Impl;

import com.devsu.corebancario.model.Cliente;
import com.devsu.corebancario.model.Account;
import com.devsu.corebancario.repository.CuentaRepository;
import com.devsu.corebancario.repository.MovimientoRepository;
import com.devsu.corebancario.service.ICuentaService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CuentaServiceImpl implements ICuentaService {

  private final CuentaRepository cuentaRepository;

  private final MovimientoRepository movimientoRepository;

  public CuentaServiceImpl(CuentaRepository cuentaRepository,
      MovimientoRepository movimientoRepository) {
    this.cuentaRepository = cuentaRepository;
    this.movimientoRepository = movimientoRepository;
  }

  @Override
  public List<Account> obtenerCuentas() {
    return cuentaRepository.findAll();
  }

  @Override
  public Optional<Account> obtenerCuentaPorId(Long id) {
    return cuentaRepository.findById(id);
  }

  @Override
  public Optional<Account> obtenerCuentaPorNumeroCuenta(String numeroCuenta) {
    return Optional.ofNullable(cuentaRepository.findCuentaByNumeroCuenta(numeroCuenta));
  }

  @Override
  public Account crearCuenta(Account cuenta) {
    return cuentaRepository.save(cuenta);
  }

  @Override
  public Account actualizarCuenta(Long id, Account cuenta) {
    Optional<Account> cuentaOptional = cuentaRepository.findById(id);
    if (cuentaOptional.isPresent()) {
      Account cuentaActual = cuentaOptional.get();
      cuentaActual.setTipoCuenta(cuenta.getTipoCuenta());
      cuentaActual.setSaldoInicial(cuenta.getSaldoInicial());
      cuentaActual.setEstado(cuenta.getEstado());
      return cuentaRepository.save(cuentaActual);
    }
    return null;
  }


  @Override
  public void eliminarCuenta(Long id) {
    cuentaRepository.findById(id).ifPresent(cuentaRepository::delete);

  }

  @Override
  public List<Account> findCuentasByClienteId(Cliente cliente) {
    return null;
  }

}