package com.neoris.turnosrotativos.controllers.requests;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
@Setter
public class ShiftRequest {

     Integer idJornada;

    @NotNull(message = "El Id del empleado no puede estar vacia")
     Integer idEmpleado;

    @NotNull(message = "El Id del concepto no puede estar vacia")
     Integer idConcepto;

    @NotNull(message = "La fecha no puede estar vacia")
     LocalDate fecha;

     Integer horasTrabajadas;
}
