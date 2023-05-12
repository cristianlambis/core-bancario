package com.devsu.corebancario.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends Persona {

  @Column(name = "cliente_id")
  private String clienteId;
  @Column(name = "contrasena")
  private String contrasena;
  @Column(name = "estado")
  private String estado;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente")
  @JsonManagedReference
  private List<Cuenta> cuentas;

  public Cliente(Long id, String nombre, String genero, Integer edad, String identificacion,
      String direccion, String telefono, String clienteId, String contrasena, String estado,
      List<Cuenta> cuentas) {
    super(id, nombre, genero, edad, identificacion, direccion, telefono);
    this.clienteId = clienteId;
    this.contrasena = contrasena;
    this.estado = estado;
    this.cuentas = cuentas;
  }


  public Cliente() {
  }

  public String getClienteId() {
    return clienteId;
  }

  public void setClienteId(String clienteId) {
    this.clienteId = clienteId;
  }

  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public List<Cuenta> getCuentas() {
    return cuentas;
  }

  public void setCuentas(List<Cuenta> cuentas) {
    this.cuentas = cuentas;
  }
}
