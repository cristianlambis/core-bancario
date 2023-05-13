package com.devsu.corebancario.service;

import com.devsu.corebancario.model.Cliente;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IClienteService {

  Cliente crearCliente(Cliente cliente);

  Optional<Cliente> obtenerClientePorId(Long id);

  List<Cliente> obtenerTodosLosClientes();

  void eliminarCliente(Long id);

  Cliente actualizarCliente(Long id, Cliente cliente);

  Cliente obtenerClientePorClienteId(String clienteId);

  Cliente obtenerMovimientosFiltradosPorClienteId(String clienteId, LocalDate fechaDesde,
      LocalDate fechaHasta);


}
