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

    ClienteDTO clienteDTO = new ClienteDTO();
    clienteDTO.setClienteId(cliente.getClienteId());
    clienteDTO.setNombre(cliente.getNombre());
    clienteDTO.setIdentificacion(cliente.getIdentificacion());

    List<CuentaDTO> cuentasDTO = new ArrayList<>();

    for (Cuenta cuenta : cliente.getCuentas()) {
      CuentaDTO cuentaDTO = new CuentaDTO();
      cuentaDTO.setId(cuenta.getId());
      cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
      cuentaDTO.setSaldo(cuenta.getSaldoInicial());

      List<MovimientoDTO> movimientosDTO = new ArrayList<>();

      double totalCreditos = cuenta.getMovimientos()
          .stream()
          .filter(m -> "Credito".equals(m.getTipoMovimiento()))
          .mapToDouble(Movimiento::getValor)
          .sum();

      double totalDebitos = cuenta.getMovimientos()
          .stream()
          .filter(m -> "Debito".equals(m.getTipoMovimiento()))
          .mapToDouble(Movimiento::getValor)
          .sum();

      for (Movimiento m : cuenta.getMovimientos()) {
        MovimientoDTO movimientoDto = new MovimientoDTO();
        movimientoDto.setId(m.getId());
        movimientoDto.setFecha(m.getFecha());
        movimientoDto.setTipoMovimiento(m.getTipoMovimiento());
        movimientoDto.setValor(m.getValor());
        movimientosDTO.add(movimientoDto);

      }

      cuentaDTO.setMovimientos(movimientosDTO);
      cuentaDTO.setTotalCreditos(totalCreditos);
      cuentaDTO.setTotalDebitos(totalDebitos);
      cuentasDTO.add(cuentaDTO);
    }

    clienteDTO.setCuentas(cuentasDTO);

    if (cliente == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(clienteDTO);
  }




        /*
        Double totalCredito = c.getMovimientos()
                    .stream()
                    .filter(m -> (m.getFecha().isAfter(fechaDesde) || m.getFecha().isEqual(fechaDesde)) &&
                            (m.getFecha().isBefore(fechaHasta) || m.getFecha().isEqual(fechaHasta))
                            && "Credito".equals(m.getTipoMovimiento())).mapToDouble(movimiento -> movimiento.getValor()).sum();

            Double totalDebito = c.getMovimientos()
                    .stream()
                    .filter(m -> (m.getFecha().isAfter(fechaDesde) || m.getFecha().isEqual(fechaDesde)) &&
                            (m.getFecha().isBefore(fechaHasta) || m.getFecha().isEqual(fechaHasta))
                            && "Debito".equals(m.getTipoMovimiento())).mapToDouble(movimiento -> movimiento.getValor()).sum();
         */


}

