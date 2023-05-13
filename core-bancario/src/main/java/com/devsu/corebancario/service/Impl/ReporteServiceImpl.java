package com.devsu.corebancario.service.Impl;

import com.devsu.corebancario.dto.EstadoCuentaDTO;
import com.devsu.corebancario.model.Cliente;
import com.devsu.corebancario.service.IClienteService;
import com.devsu.corebancario.service.IReporteService;
import java.time.LocalDate;

public class ReporteServiceImpl implements IReporteService {

  private final IClienteService clienteService;

  public ReporteServiceImpl(IClienteService clienteService) {
    this.clienteService = clienteService;
  }

  @Override
  public EstadoCuentaDTO ObtenerReportePorCliente(String clienteId, LocalDate fechaDesde,
      LocalDate fechaHasta) {

    return null;

  }
}
