package com.devsu.corebancario.repository;

import com.devsu.corebancario.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    /*@Query("SELECT * FROM Cuenta WHERE cliente_id =:clienteId")
    List<Cuenta> obtenerCuentasPorCliente(@Param("clienteId") Long clienteId);*/
}
