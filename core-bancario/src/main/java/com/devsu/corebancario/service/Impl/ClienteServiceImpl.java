package com.devsu.corebancario.service.Impl;

import com.devsu.corebancario.model.Cliente;
import com.devsu.corebancario.model.Movimiento;
import com.devsu.corebancario.repository.ClienteRepository;
import com.devsu.corebancario.service.IClienteService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements IClienteService {

  private final ClienteRepository clienteRepository;

  public ClienteServiceImpl(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }


  @Override
  public Cliente crearCliente(Cliente cliente) {
    return clienteRepository.save(cliente);
  }

  @Override
  public Optional<Cliente> obtenerClientePorId(Long id) {
    return clienteRepository.findById(id);

  }

  @Override
  public List<Cliente> obtenerTodosLosClientes() {
    return (List<Cliente>) clienteRepository.findAll();
  }

  @Override
  public void eliminarCliente(Long id) {
    Cliente cliente = obtenerClientePorId(id).get();
    clienteRepository.delete(cliente);

  }

  @Override
  public Cliente actualizarCliente(Long id, Cliente cliente) {
    Cliente clienteActual = obtenerClientePorId(id).get();
    clienteActual.setNombre(cliente.getNombre());
    clienteActual.setGenero(cliente.getGenero());
    clienteActual.setEdad(cliente.getEdad());
    clienteActual.setIdentificacion(cliente.getIdentificacion());
    clienteActual.setDireccion(cliente.getDireccion());
    clienteActual.setTelefono(cliente.getTelefono());
    clienteActual.setClienteId(cliente.getClienteId());
    clienteActual.setContrasena(cliente.getContrasena());
    clienteActual.setEstado(cliente.getEstado());
    return clienteRepository.save(clienteActual);

  }

  @Override
  public Cliente actualizarParcialmenteCliente(Long id, Map<String, Object> campos) {
    Cliente clienteActual = obtenerClientePorId(id).get();
    for (Map.Entry<String, Object> entry : campos.entrySet()) {
      String campo = entry.getKey();
      Object valor = entry.getValue();

    }
    return clienteRepository.save(clienteActual);

  }

  @Override
  public Cliente obtenerClientePorClienteId(String clienteId) {
    return clienteRepository.findClienteByClienteId(clienteId);
  }

  @Override
  public Cliente obtenerMovimientosFiltradosPorClienteId(String clienteId, LocalDate fechaDesde,
      LocalDate fechaHasta) {
    final Cliente cliente = this.obtenerClientePorClienteId(clienteId);

    cliente.getCuentas().forEach(c -> {
      List<Movimiento> movimientoList =
          c.getMovimientos()
              .stream()
              .filter(m -> (m.getFecha().isAfter(fechaDesde) || m.getFecha().isEqual(fechaDesde)) &&
                  (m.getFecha().isBefore(fechaHasta) || m.getFecha().isEqual(fechaHasta)))
              .toList();

      c.setMovimientos(movimientoList);

    });

    return cliente;
  }
}
