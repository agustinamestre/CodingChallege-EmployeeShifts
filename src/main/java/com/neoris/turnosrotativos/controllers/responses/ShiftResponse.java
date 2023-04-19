package com.neoris.turnosrotativos.controllers.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class ShiftResponse implements Serializable {
     Integer idJornada;
     Integer nroDocumento;
     String nombreCompleto;
     LocalDate fecha;
     String concepto;
     Integer horasTrabajadas;
}
