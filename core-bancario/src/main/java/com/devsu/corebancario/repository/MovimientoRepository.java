package com.devsu.corebancario.repository;


import com.devsu.corebancario.model.Movimiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

  List<Movimiento> findAllByCuenta_Id(Long cuentaId);


}
