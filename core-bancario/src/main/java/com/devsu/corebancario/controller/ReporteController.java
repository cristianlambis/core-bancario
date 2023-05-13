package com.devsu.corebancario.controller;

import com.devsu.corebancario.dto.ClienteDTO;
import com.devsu.corebancario.dto.CuentaDTO;
import com.devsu.corebancario.dto.MovimientoDTO;
import com.devsu.corebancario.model.Cliente;
import com.devsu.corebancario.model.Cuenta;
import com.devsu.corebancario.model.Movimiento;
import com.devsu.corebancario.service.IClienteService;
import com.devsu.corebancario.service.ICuentaService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

  @Autowired
  private ICuentaService cuentaService;

  @Autowired
  private IClienteService clienteService;


  @GetMapping
  @Transient
  public ResponseEntity<ClienteDTO> generarReporte(
      @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
      @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
      @RequestParam("clienteId") String clienteId) {

    Cliente cliente = clienteService.obtenerMovimientosFiltradosPorClienteId(clienteId, fechaDesde,
        fechaHasta);

    if (cliente == null) {
      return ResponseEntity.notFound().build();
    }

    ClienteDTO clienteDTO = new ClienteDTO();
    clienteDTO.setId(cliente.getId());
    clienteDTO.setClienteId(cliente.getClienteId());
    clienteDTO.setNombre(cliente.getNombre());
    clienteDTO.setIdentificacion(cliente.getIdentificacion());

    List<CuentaDTO> cuentasDTO = new ArrayList<>();

    for (Cuenta cuenta : cliente.getCuentas()) {
      CuentaDTO cuentaDTO = new CuentaDTO();
      cuentaDTO.setId(cuenta.getId());
      cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
      cuentaDTO.setEstado(cuenta.getEstado());
      cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
      cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());

      double saldoActual = cuenta.getSaldoInicial();
      List<MovimientoDTO> movimientosDTO = new ArrayList<>();

      for (Movimiento m : cuenta.getMovimientos()) {
        MovimientoDTO movimientoDto = new MovimientoDTO();
        movimientoDto.setId(m.getId());
        movimientoDto.setFecha(m.getFecha());
        movimientoDto.setTipoMovimiento(m.getTipoMovimiento());
        movimientoDto.setValor(m.getValor());

        if ("Credito".equals(m.getTipoMovimiento())){
          saldoActual += m.getValor();
        }else if ("Debito".equals(m.getTipoMovimiento())){
          saldoActual -=m.getValor();
        }
        movimientoDto.setSaldo(saldoActual);
        movimientosDTO.add(movimientoDto);

      }
      cuentaDTO.setMovimientos(movimientosDTO);
      cuentaDTO.setTotalCreditos(cuenta.getMovimientos()
          .stream()
          .filter(m -> "Credito".equals(m.getTipoMovimiento()))
          .mapToDouble(Movimiento::getValor)
          .sum());
      cuentaDTO.setTotalDebitos(cuenta.getMovimientos()
          .stream()
          .filter(m -> "Debito".equals(m.getTipoMovimiento()))
          .mapToDouble(Movimiento::getValor)
          .sum());
      cuentaDTO.setSaldo(cuentaDTO.getSaldoInicial() + cuentaDTO.getTotalCreditos() - cuentaDTO.getTotalDebitos());
      cuentasDTO.add(cuentaDTO);
    }

    clienteDTO.setCuentas(cuentasDTO);
    double saldoTotal = cuentasDTO.stream()
        .mapToDouble(c -> c.getSaldoInicial() + c.getTotalCreditos() - c.getTotalDebitos())
        .sum();

    clienteDTO.setCuentas(cuentasDTO);

    return ResponseEntity.ok(clienteDTO);
  }
}