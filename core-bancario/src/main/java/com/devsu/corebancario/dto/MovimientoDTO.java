package com.devsu.corebancario.dto;

import java.time.LocalDate;

public class MovimientoDTO {

  private Long id;
  private CuentaDTO cuenta;
  private LocalDate fecha;
  private String tipoMovimiento;
  private double valor;
  private double saldo;

  public MovimientoDTO() {
  }

  public MovimientoDTO(Long id, LocalDate fecha, String tipoMovimiento, double valor,
      double saldo) {
    this.id = id;
    this.fecha = fecha;
    this.tipoMovimiento = tipoMovimiento;
    this.valor = valor;
    this.saldo = saldo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public String getTipoMovimiento() {
    return tipoMovimiento;
  }

  public void setTipoMovimiento(String tipoMovimiento) {
    this.tipoMovimiento = tipoMovimiento;
  }

  public double getValor() {
    return valor;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public CuentaDTO getCuenta() {
    return cuenta;
  }

  public void setCuenta(CuentaDTO cuenta) {
    this.cuenta = cuenta;
  }
}