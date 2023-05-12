package com.devsu.corebancario.repository;

import com.devsu.corebancario.model.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

  Cliente findClienteByClienteId(String clienteId);

}
