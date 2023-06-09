package com.devsu.corebancario.repository;


import com.devsu.corebancario.model.Movement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movement, Long> {

  List<Movement> findAllByCuenta_Id(Long cuentaId);


}
