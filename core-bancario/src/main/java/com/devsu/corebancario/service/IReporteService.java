package com.devsu.corebancario.service;

import com.devsu.corebancario.dto.EstadoCuentaDTO;
import java.time.LocalDate;

public interface IReporteService {

  EstadoCuentaDTO ObtenerReportePorCliente(String clienteId, LocalDate fechaDesde,
      LocalDate fechaHasta);
}
