package com.devsu.corebancario.dto;

import java.util.List;

public class EstadoCuentaDTO {

  private String clienteId;
  private String nombre;
  private List<CuentaDTO> cuentas;

  public EstadoCuentaDTO() {
  }

  public EstadoCuentaDTO(String clienteId, String nombreCliente, List<CuentaDTO> cuentas) {
    this.clienteId = clienteId;
    this.nombre = nombreCliente;
    this.cuentas = cuentas;
  }

  public String getIdCliente() {
    return clienteId;
  }

  public void setIdCliente(String idCliente) {
    this.clienteId = clienteId;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<CuentaDTO> getCuentas() {
    return cuentas;
  }

  public void setCuentas(List<CuentaDTO> cuentas) {
    this.cuentas = cuentas;
  }
}