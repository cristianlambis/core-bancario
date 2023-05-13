package com.devsu.corebancario.dto;

import java.math.BigDecimal;
import java.util.List;

public class CuentaDTO {

  private Long id;
  private String numeroCuenta;
  private String tipoCuenta;
  private String estado;
  private Double saldoInicial;
  private ClienteDTO cliente;
  private List<MovimientoDTO> movimientos;
  private Double saldo;
  private Double totalDebitos;
  private Double totalCreditos;

  public CuentaDTO() {
  }

  public CuentaDTO(Long id, String numeroCuenta, String tipoCuenta, BigDecimal saldo) {
  }

  public CuentaDTO(Long id, String numeroCuenta, String tipoCuenta, String estado,
      Double saldoInicial, double saldo, ClienteDTO cliente, double totalDebitos,
      double totalCreditos) {
    this.id = id;
    this.numeroCuenta = numeroCuenta;
    this.tipoCuenta = tipoCuenta;
    this.estado = estado;
    this.saldoInicial = saldoInicial;
    this.saldo = saldo;
    this.cliente = cliente;
    this.totalDebitos = totalDebitos;
    this.totalCreditos = totalCreditos;
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

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public Double getSaldoInicial() {
    return saldoInicial;
  }

  public void setSaldoInicial(Double saldoInicial) {
    this.saldoInicial = saldoInicial;
  }

  public ClienteDTO getCliente() {
    return cliente;
  }

  public void setCliente(ClienteDTO cliente) {
    this.cliente = cliente;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public double getTotalDebitos() {
    return totalDebitos;
  }

  public void setTotalDebitos(double totalDebitos) {
    this.totalDebitos = totalDebitos;
  }

  public double getTotalCreditos() {
    return totalCreditos;
  }

  public void setTotalCreditos(double totalCreditos) {
    this.totalCreditos = totalCreditos;
  }

  public void setSaldoInicial(double saldoInicial) {
    this.saldoInicial = saldoInicial;
  }

  public List<MovimientoDTO> getMovimientos() {
    return movimientos;
  }

  public void setMovimientos(List<MovimientoDTO> movimientos) {
    this.movimientos = movimientos;
  }
}