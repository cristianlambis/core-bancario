package com.devsu.corebancario.dto;

import java.util.List;

public class ClienteDTO {

  private Long id;
  private String nombre;
  private String genero;
  private Integer edad;
  private String identificacion;
  private String direccion;
  private String telefono;
  private String clienteId;
  private List<CuentaDTO> cuentas;

  public ClienteDTO() {
  }

  public ClienteDTO(Long id, String nombre, String genero, Integer edad, String identificacion,
      String direccion, String telefono) {
    this.id = id;
    this.nombre = nombre;
    this.genero = genero;
    this.edad = edad;
    this.identificacion = identificacion;
    this.direccion = direccion;
    this.telefono = telefono;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    this.edad = edad;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getClienteId() {
    return clienteId;
  }

  public void setClienteId(String clienteId) {
    this.clienteId = clienteId;
  }

  public List<CuentaDTO> getCuentas() {
    return cuentas;
  }

  public void setCuentas(List<CuentaDTO> cuentas) {
    this.cuentas = cuentas;
  }
}
