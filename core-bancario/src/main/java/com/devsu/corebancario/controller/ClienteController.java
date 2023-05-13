package com.devsu.corebancario.controller;

import com.devsu.corebancario.dto.ClienteDTO;
import com.devsu.corebancario.model.Cliente;
import com.devsu.corebancario.model.Cuenta;
import com.devsu.corebancario.service.IClienteService;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

  @Autowired
  private IClienteService clienteService;

  @PostMapping
  public ResponseEntity<Cliente> crearCliente(@RequestBody ClienteDTO clienteDTO) {
    Cliente cliente = new Cliente();
    cliente.setClienteId(clienteDTO.getClienteId());
    cliente.setContrasena(clienteDTO.getContrasena());
    cliente.setEstado(clienteDTO.getEstado());
    cliente.setId(clienteDTO.getId());
    cliente.setNombre(clienteDTO.getNombre());
    cliente.setGenero(clienteDTO.getGenero());
    cliente.setEdad(clienteDTO.getEdad());
    cliente.setIdentificacion(clienteDTO.getIdentificacion());
    cliente.setDireccion(clienteDTO.getDireccion());
    cliente.setTelefono(clienteDTO.getTelefono());
    List<Cuenta> cuentas = new ArrayList<>();
    cliente.setCuentas(cuentas);
    Cliente clienteCreado = clienteService.crearCliente(cliente);
    return ResponseEntity.created(URI.create("/clientes" + clienteCreado.getId()))
        .body(clienteCreado);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
    Optional<Cliente> clienteOptional = clienteService.obtenerClientePorId(id);
    if (clienteOptional.isPresent()) {
      return ResponseEntity.ok(clienteOptional.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
    List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
    if (clientes.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(clientes);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
    clienteService.eliminarCliente(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id,
      @RequestBody ClienteDTO clienteDTO) {
    Cliente cliente = new Cliente();
    cliente.setClienteId(clienteDTO.getClienteId());
    cliente.setContrasena(clienteDTO.getContrasena());
    cliente.setEstado(clienteDTO.getEstado());
    cliente.setId(clienteDTO.getId());
    cliente.setNombre(clienteDTO.getNombre());
    cliente.setGenero(clienteDTO.getGenero());
    cliente.setEdad(clienteDTO.getEdad());
    cliente.setIdentificacion(clienteDTO.getIdentificacion());
    cliente.setDireccion(clienteDTO.getDireccion());
    cliente.setTelefono(clienteDTO.getTelefono());
    List<Cuenta> cuentas = new ArrayList<>();
    cliente.setCuentas(cuentas);
    Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
    if (clienteActualizado == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(clienteActualizado);
  }

}