package com.devsu.corebancario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuenta")
public class Cuenta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "numero_cuenta", nullable = false)
  private String numeroCuenta;

  @Column(name = "tipo_cuenta")
  private String tipoCuenta;

  @Column(name = "estado")
  private String estado;

  @Column(name = "saldo_inicial")
  private Double saldoInicial;


  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  @JsonBackReference
  private Cliente cliente;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "cuenta")
  @JsonManagedReference
  private List<Movimiento> movimientos = new ArrayList<>();


  public Cuenta(Long id, String numeroCuenta, String tipoCuenta, Double saldoInicial, String estado,
      Cliente cliente, List<Movimiento> movimientos) {
    this.id = id;
    this.numeroCuenta = numeroCuenta;
    this.tipoCuenta = tipoCuenta;
    this.saldoInicial = saldoInicial;
    this.estado = estado;
    this.cliente = cliente;
    this.movimientos = movimientos;
  }

  public Cuenta() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumeroCuenta() {
    return numeroCuenta;
  }

  public void setNumeroCuenta(String numeroCuenta) {
    this.numeroCuenta = numeroCuenta;
  }

  public String getTipoCuenta() {
    return tipoCuenta;
  }

  public void setTipoCuenta(String tipoCuenta) {
    this.tipoCuenta = tipoCuenta;
  }

  public Double getSaldoInicial() {
    return saldoInicial;
  }

  public void setSaldoInicial(Double saldoInicial) {
    this.saldoInicial = saldoInicial;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }
}
