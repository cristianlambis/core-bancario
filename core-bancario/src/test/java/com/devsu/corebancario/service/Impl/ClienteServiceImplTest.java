package com.devsu.corebancario.service.Impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.devsu.corebancario.model.Cliente;
import com.devsu.corebancario.model.Account;
import com.devsu.corebancario.model.Movement;
import com.devsu.corebancario.repository.ClienteRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

  @Mock
  private ClienteRepository mockClienteRepository;

  private ClienteServiceImpl clienteServiceImplUnderTest;

  @BeforeEach
  void setUp() {
    clienteServiceImplUnderTest = new ClienteServiceImpl(mockClienteRepository);
  }

  @Test
  void testCrearCliente() {

    final Cliente cliente = new Cliente();
    cliente.setNombre("Chris");
    cliente.setGender("F");
    cliente.setAge(5);
    cliente.setIdentificacion("1234567");
    cliente.setAddress("Avenida2");
    cliente.setPhoneNumber("453673838");
    cliente.setClienteId("12345");
    cliente.setContrasena("34hhhdy");
    cliente.setEstado("true");
    final Account cuenta = new Account();
    final Movement movimiento = new Movement();
    movimiento.setDate(LocalDate.of(2020, 1, 2));
    cuenta.setMovimientos(List.of(movimiento));
    cliente.setCuentas(List.of(cuenta));

    final Cliente cliente1 = new Cliente();
    cliente1.setNombre("Chris");
    cliente1.setGender("F");
    cliente1.setAge(5);
    cliente1.setIdentificacion("1234567");
    cliente1.setAddress("Avenida2");
    cliente1.setPhoneNumber("453673838");
    cliente1.setClienteId("12345");
    cliente1.setContrasena("34hhhdy");
    cliente1.setEstado("true");
    final Account cuenta1 = new Account();
    final Movement movimiento1 = new Movement();
    movimiento1.setDate(LocalDate.of(2020, 1, 2));
    cuenta1.setMovimientos(List.of(movimiento1));
    cliente1.setCuentas(List.of(cuenta1));
    when(mockClienteRepository.save(any(Cliente.class))).thenReturn(cliente1);

    final Cliente result = clienteServiceImplUnderTest.crearCliente(cliente);

    assertNotNull(result);
    assertEquals(cliente.getNombre(), result.getNombre());
    assertEquals(cliente.getGender(), result.getGender());
    assertEquals(cliente.getAge(), result.getAge());
    assertEquals(cliente.getIdentificacion(), result.getIdentificacion());
    assertEquals(cliente.getAddress(), result.getAddress());
    assertEquals(cliente.getPhoneNumber(), result.getPhoneNumber());
    assertEquals(cliente.getClienteId(), result.getClienteId());
    assertEquals(cliente.getContrasena(), result.getContrasena());
    assertEquals(cliente.getEstado(), result.getEstado());
    assertNotNull(result.getCuentas());
    assertEquals(cliente.getCuentas().size(), result.getCuentas().size());
    assertEquals(cliente.getCuentas().get(0).getMovimientos().size(), result.getCuentas().get(0).getMovimientos().size());
  }

  @Test
  void testObtenerClientePorId() {
    final Cliente cliente1 = new Cliente();
    cliente1.setNombre("juan");
    cliente1.setGender("m");
    cliente1.setAge(42);
    cliente1.setIdentificacion("1222333444");
    cliente1.setAddress("Guayabal");
    cliente1.setPhoneNumber("30163535353");
    cliente1.setClienteId("12234");
    cliente1.setContrasena("stiler");
    cliente1.setEstado("true");
    final Account cuenta = new Account();
    final Movement movimiento = new Movement();
    movimiento.setDate(LocalDate.of(2022, 1, 4));
    cuenta.setMovimientos(List.of(movimiento));
    cliente1.setCuentas(List.of(cuenta));
    final Optional<Cliente> cliente = Optional.of(cliente1);
    when(mockClienteRepository.findById(0L)).thenReturn(cliente);

    final Optional<Cliente> result = clienteServiceImplUnderTest.obtenerClientePorId(0L);

    assertTrue(result.isPresent());
    assertEquals(cliente1, result.get());
  }

  @Test
  void testObtenerClientePorId_ClienteRepositoryReturnsAbsent() {
    when(mockClienteRepository.findById(0L)).thenReturn(Optional.empty());

    final Optional<Cliente> result = clienteServiceImplUnderTest.obtenerClientePorId(0L);

    assertThat(result).isEmpty();
  }

  @Test
  void testObtenerTodosLosClientes() {
    final Cliente cliente = new Cliente();
    cliente.setNombre("Gabriel");
    cliente.setGender("M");
    cliente.setAge(73);
    cliente.setIdentificacion("7267266");
    cliente.setAddress("Medellin 223");
    cliente.setPhoneNumber("302776577");
    cliente.setClienteId("9876");
    cliente.setContrasena("la contrasena");
    cliente.setEstado("true");
    final Account cuenta = new Account();
    final Movement movimiento = new Movement();
    movimiento.setDate(LocalDate.of(2023, 1, 1));
    cuenta.setMovimientos(List.of(movimiento));
    cliente.setCuentas(List.of(cuenta));
    final Iterable<Cliente> clientes = List.of(cliente);
    when(mockClienteRepository.findAll()).thenReturn(clientes);

    final List<Cliente> result = clienteServiceImplUnderTest.obtenerTodosLosClientes();

    assertEquals(1, result.size());
    Cliente clienteObtenido = result.get(0);
    assertEquals(cliente.getNombre(), clienteObtenido.getNombre());
    assertEquals(cliente.getGender(), clienteObtenido.getGender());
    assertEquals(cliente.getAge(), clienteObtenido.getAge());
    assertEquals(cliente.getIdentificacion(), clienteObtenido.getIdentificacion());
    assertEquals(cliente.getAddress(), clienteObtenido.getAddress());
    assertEquals(cliente.getPhoneNumber(), clienteObtenido.getPhoneNumber());
    assertEquals(cliente.getClienteId(), clienteObtenido.getClienteId());
    assertEquals(cliente.getContrasena(), clienteObtenido.getContrasena());
    assertEquals(cliente.getEstado(), clienteObtenido.getEstado());
    assertEquals(1, clienteObtenido.getCuentas().size());
    Account cuentaObtenida = clienteObtenido.getCuentas().get(0);
    assertEquals(1, cuentaObtenida.getMovimientos().size());
    Movement movimientoObtenido = cuentaObtenida.getMovimientos().get(0);
    assertEquals(LocalDate.of(2023, 1, 1), movimientoObtenido.getDate());
  }

  @Test
  void testObtenerTodosLosClientes_ClienteRepositoryReturnsNoItems() {
    when(mockClienteRepository.findAll()).thenReturn(Collections.emptyList());

    final List<Cliente> result = clienteServiceImplUnderTest.obtenerTodosLosClientes();

    assertThat(result).isEqualTo(Collections.emptyList());
  }

  @Test
  void testEliminarCliente() {
    final Cliente cliente1 = new Cliente();
    cliente1.setNombre("Roberto");
    cliente1.setGender("F");
    cliente1.setAge(50);
    cliente1.setIdentificacion("937367363");
    cliente1.setAddress("El pozon");
    cliente1.setPhoneNumber("30452525267");
    cliente1.setClienteId("3637");
    cliente1.setContrasena("7365553e");
    cliente1.setEstado("true");
    final Account cuenta = new Account();
    final Movement movimiento = new Movement();
    movimiento.setDate(LocalDate.of(2023, 1, 19));
    cuenta.setMovimientos(List.of(movimiento));
    cliente1.setCuentas(List.of(cuenta));
    final Optional<Cliente> cliente = Optional.of(cliente1);
    when(mockClienteRepository.findById(0L)).thenReturn(cliente);

    clienteServiceImplUnderTest.eliminarCliente(0L);

    verify(mockClienteRepository).delete(any(Cliente.class));
  }

  @Test
  void testEliminarCliente_ClienteRepositoryFindByIdReturnsAbsent() {
    when(mockClienteRepository.findById(0L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> clienteServiceImplUnderTest.eliminarCliente(0L))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void testActualizarCliente() {
    final Cliente cliente = new Cliente();
    cliente.setNombre("Katte");
    cliente.setGender("F");
    cliente.setAge(37);
    cliente.setIdentificacion("15452424");
    cliente.setAddress("Guayabal");
    cliente.setPhoneNumber("30162652525");
    cliente.setClienteId("7654");
    cliente.setContrasena("la mia");
    cliente.setEstado("true");
    final Account cuenta = new Account();
    final Movement movimiento = new Movement();
    movimiento.setDate(LocalDate.of(2023, 12, 9));
    cuenta.setMovimientos(List.of(movimiento));
    cliente.setCuentas(List.of(cuenta));

    final Cliente cliente2 = new Cliente();
    cliente2.setNombre("pedro");
    cliente2.setGender("M");
    cliente2.setAge(23);
    cliente2.setIdentificacion("6y73673575");
    cliente2.setAddress("la esperanza");
    cliente2.setPhoneNumber("783676363");
    cliente2.setClienteId("876");
    cliente2.setContrasena("kjsdhsdhsh");
    cliente2.setEstado("true");
    final Account cuenta1 = new Account();
    final Movement movimiento1 = new Movement();
    movimiento1.setDate(LocalDate.of(2020, 1, 1));
    cuenta1.setMovimientos(List.of(movimiento1));
    cliente2.setCuentas(List.of(cuenta1));
    final Optional<Cliente> cliente1 = Optional.of(cliente2);
    when(mockClienteRepository.findById(0L)).thenReturn(cliente1);

    final Cliente cliente3 = new Cliente();
    cliente3.setNombre("Katte");
    cliente3.setGender("F");
    cliente3.setAge(37);
    cliente3.setIdentificacion("15452424");
    cliente3.setAddress("Guayabal");
    cliente3.setPhoneNumber("30162652525");
    cliente3.setClienteId("7654");
    cliente3.setContrasena("la mia");
    cliente3.setEstado("true");
    final Account cuenta2 = new Account();
    final Movement movimiento2 = new Movement();
    movimiento2.setDate(LocalDate.of(2023, 12, 9));
    cuenta2.setMovimientos(List.of(movimiento2));
    cliente3.setCuentas(List.of(cuenta2));
    when(mockClienteRepository.save(any(Cliente.class))).thenReturn(cliente3);

    final Cliente result = clienteServiceImplUnderTest.actualizarCliente(0L, cliente);

    assertNotNull(result);
    assertEquals(result.getNombre(), cliente.getNombre());
    assertEquals(result.getGender(), cliente.getGender());
    assertEquals(result.getAge(), cliente.getAge());
    assertEquals(result.getIdentificacion(), cliente.getIdentificacion());
    assertEquals(result.getAddress(), cliente.getAddress());
    assertEquals(result.getPhoneNumber(), cliente.getPhoneNumber());
    assertEquals(result.getClienteId(), cliente.getClienteId());
    assertEquals(result.getContrasena(), cliente.getContrasena());
    assertEquals(result.getEstado(), cliente.getEstado());
    assertEquals(result.getCuentas().size(), cliente.getCuentas().size());
    assertEquals(result.getCuentas().get(0).getMovimientos().size(), cliente.getCuentas().get(0).getMovimientos().size());
  }

  @Test
  void testActualizarCliente_ClienteRepositoryFindByIdReturnsAbsent() {
    final Cliente cliente = new Cliente();
    cliente.setNombre("franck");
    cliente.setGender("M");
    cliente.setAge(70);
    cliente.setIdentificacion("36533553");
    cliente.setAddress("no lo se");
    cliente.setPhoneNumber("9383838");
    cliente.setClienteId("098765");
    cliente.setContrasena("1246737838");
    cliente.setEstado("true");
    final Account cuenta = new Account();
    final Movement movimiento = new Movement();
    movimiento.setDate(LocalDate.of(2022, 5, 10));
    cuenta.setMovimientos(List.of(movimiento));
    cliente.setCuentas(List.of(cuenta));

    when(mockClienteRepository.findById(0L)).thenReturn(Optional.empty());

    assertThatThrownBy(
        () -> clienteServiceImplUnderTest.actualizarCliente(0L, cliente))
        .isInstanceOf(NoSuchElementException.class);
  }


  @Test
  void testObtenerClientePorClienteId() {

    final Cliente cliente = new Cliente();
    cliente.setNombre("Luca");
    cliente.setGender("M");
    cliente.setAge(1);
    cliente.setIdentificacion("125366363");
    cliente.setAddress("envigado 344");
    cliente.setPhoneNumber("736363");
    cliente.setClienteId("0967");
    cliente.setContrasena("la mejor");
    cliente.setEstado("true");
    final Account cuenta = new Account();
    final Movement movimiento = new Movement();
    movimiento.setDate(LocalDate.of(2022, 4, 3));
    cuenta.setMovimientos(List.of(movimiento));
    cliente.setCuentas(List.of(cuenta));
    when(mockClienteRepository.findClienteByClienteId("clienteId")).thenReturn(cliente);

    final Cliente result = clienteServiceImplUnderTest.obtenerClientePorClienteId("clienteId");

    assertEquals(cliente, result);
  }

  @Test
  void testObtenerMovimientosFiltradosPorClienteId() {
    final Cliente cliente = new Cliente();
    cliente.setNombre("Lili");
    cliente.setGender("M");
    cliente.setAge(30);
    cliente.setIdentificacion("7653535");
    cliente.setAddress("Itagui 76");
    cliente.setPhoneNumber("3016272772");
    cliente.setClienteId("5638");
    cliente.setContrasena("otra contrasena");
    cliente.setEstado("true");
    final Account cuenta = new Account();
    final Movement movimiento = new Movement();
    movimiento.setDate(LocalDate.of(2019, 10, 18));
    cuenta.setMovimientos(List.of(movimiento));
    cliente.setCuentas(List.of(cuenta));
    when(mockClienteRepository.findClienteByClienteId("clienteId")).thenReturn(cliente);

    final Cliente result = clienteServiceImplUnderTest.obtenerMovimientosFiltradosPorClienteId(
        "clienteId", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1));

    verify(mockClienteRepository).findClienteByClienteId("clienteId");
    assertNotNull(result);
    assertEquals(1, result.getCuentas().size());
    final Account cuentaResultado = result.getCuentas().get(0);
    assertNotNull(cuentaResultado.getMovimientos());
    assertEquals(0, cuentaResultado.getMovimientos().size());



  }
}
