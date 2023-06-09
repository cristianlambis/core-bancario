package com.devsu.corebancario.service.Impl;

import com.devsu.corebancario.exception.CoreBancarioSystemException;
import com.devsu.corebancario.model.Account;
import com.devsu.corebancario.model.Movement;
import com.devsu.corebancario.repository.MovimientoRepository;
import com.devsu.corebancario.service.IMovimientoService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

  private static final Logger logger = LogManager.getLogger(MovimientoServiceImpl.class);

  public static final String SALDO_NO_DISPONIBLE = "Saldo no disponible...";

  private final MovimientoRepository movimientoRepository;

  public MovimientoServiceImpl(MovimientoRepository movimientoRepository) {
    this.movimientoRepository = movimientoRepository;
  }

  @Override
  public Optional<Object> crearMovimiento(Movement movimiento) {

    try {
      double saldoTotal = 0;
      final Account cuenta = movimiento.getCuenta();

      if (Objects.isNull(cuenta.getSaldoInicial())) {
        return Optional.of(SALDO_NO_DISPONIBLE);
      }

      if (cuenta.getMovimientos().size() == 0) {
        saldoTotal = hacerMovimiento(movimiento.getMovementType(), cuenta.getSaldoInicial(),
            movimiento.getValor());
      } else {
        double saldoUltimoMovimiento = obtenerUltimoMovimiento(cuenta.getMovimientos());
        saldoTotal = hacerMovimiento(movimiento.getMovementType(), saldoUltimoMovimiento,
            movimiento.getValor());
      }

      if (saldoTotal < 0) {
        return Optional.of(SALDO_NO_DISPONIBLE);
      }

      if (validarSaldoExcedido(cuenta.getMovimientos(), movimiento)) {
        return Optional.of("Cupo diarío excedido...");
      }

      movimiento.setSaldo(saldoTotal);
      logger.info("Saldo Total movimiento:  {}", saldoTotal);
      return Optional.of(movimientoRepository.save(movimiento));
    } catch (final Exception ex) {
      throw new CoreBancarioSystemException(ex.getMessage(), ex.getCause());
    }
  }

  @Override
  public Movement obtenerMovimientoPorId(Long id) {
    return movimientoRepository.findById(id).orElse(null);
  }

  @Override
  public List<Movement> obtenerMovimientos() {
    return movimientoRepository.findAll();
  }

  @Override
  public Movement actualizarParcialmenteMovimiento(Long id, Map<String, Object> campos) {
    return null;
  }

  @Override
  public void eliminarMovimiento(Long id) {
    movimientoRepository.deleteById(id);

  }

  @Override
  public List<Movement> findAllMovimientosPorCuentaId(Long cuentaId) {
    return movimientoRepository.findAllByCuenta_Id(cuentaId);
  }

  private double obtenerUltimoMovimiento(List<Movement> movimientoList) {
    Movement movimiento = movimientoList.stream().reduce((first, second) -> second)
        .orElse(null);

    return Objects.nonNull(movimiento) ? movimiento.getSaldo() : 0;
  }

  private double hacerMovimiento(String tipoMovimiento, double saldo, double valorMovimiento) {
    double saldoTotal = 0;
    if (tipoMovimiento.equalsIgnoreCase("Debito")) {
      saldoTotal = saldo - valorMovimiento;
    } else if (tipoMovimiento.equalsIgnoreCase("Credito")) {
      saldoTotal = saldo + valorMovimiento;
    }
    return saldoTotal;
  }

  private boolean validarSaldoExcedido(List<Movement> movimientoList, Movement movimiento) {

    try {
      final AtomicReference<Double> saldoTotalDia = new AtomicReference<>((double) 0);
      final List<Movement> movimientoLis =
          movimientoList.stream()
              .filter(m -> m.getDate().isEqual(movimiento.getDate())
                  && m.getMovementType().equalsIgnoreCase("Debito"))
              .toList();

      movimientoLis.forEach(mov -> {
        saldoTotalDia.set(saldoTotalDia.get() + mov.getValor());
      });

      if (movimiento.getMovementType().equalsIgnoreCase("Debito")) {
        saldoTotalDia.set(saldoTotalDia.get() + movimiento.getValor());
      }

      logger.info("Saldo total en la fecha: {} es : {}", movimiento.getDate(),
          saldoTotalDia.get());
      return saldoTotalDia.get() >= 1000 ? Boolean.TRUE : Boolean.FALSE;
    } catch (final Exception ex) {
      throw new CoreBancarioSystemException(ex.getMessage(), ex.getCause());
    }
  }
}
