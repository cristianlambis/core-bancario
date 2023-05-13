package com.devsu.corebancario.service.Impl;

import com.devsu.corebancario.model.Cliente;
import com.devsu.corebancario.model.Cuenta;
import com.devsu.corebancario.repository.CuentaRepository;
import com.devsu.corebancario.repository.MovimientoRepository;
import com.devsu.corebancario.service.ICuentaService;
import java.util.List;
import java.util.Map;
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
  public List<Cuenta> obtenerCuentas() {
    return cuentaRepository.findAll();
  }

  @Override
  public Optional<Cuenta> obtenerCuentaPorId(Long id) {
    return cuentaRepository.findById(id);
  }

  @Override
  public Optional<Cuenta> obtenerCuentaPorNumeroCuenta(String numeroCuenta) {
    return Optional.ofNullable(cuentaRepository.findCuentaByNumeroCuenta(numeroCuenta));
  }

  @Override
  public Cuenta crearCuenta(Cuenta cuenta) {
    return cuentaRepository.save(cuenta);
  }

  @Override
  public Cuenta actualizarCuenta(Long id, Cuenta cuenta) {
    Optional<Cuenta> cuentaOptional = cuentaRepository.findById(id);
    if (cuentaOptional.isPresent()) {
      Cuenta cuentaActual = cuentaOptional.get();
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
  public List<Cuenta> findCuentasByClienteId(Cliente cliente) {
    return null;
  }

}