package com.neoris.turnosrotativos.controllers.responses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class EmployeeResponse implements Serializable {
     Integer id;
     Integer nroDocumento;
     String nombre;
     String apellido;
     String email;
     LocalDate fechaNacimiento;
     LocalDate fechaIngreso;
     LocalDate fechaCreacion;
}
