package com.neoris.turnosrotativos.controllers.requests;

import com.neoris.turnosrotativos.controllers.validations.Adult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeRequest implements Serializable {

    Integer id;
    Integer nroDocumento;

    @NotBlank(message = "El nombre no puede estar vacio.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$", message = "Solo se permiten letras en el campo nombre.")
    String nombre;

    @NotBlank(message = "El apellido no puede estar vacio.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$", message = "Solo se permiten letras en el campo apellido.")
    String apellido;

    @Email(message = "El email ingresado no es correcto.")
    @NotBlank(message = "El email no puede estar vacio.")
    String email;

    @NotNull(message = "La fecha de nacimiento no puede ser null.")
    @PastOrPresent(message = "La fecha de nacimiento no puede ser posterior al día de la fecha.")
    @Adult
    LocalDate fechaNacimiento;

    @NotNull(message = "La fecha de ingreso no puede ser null.")
    @PastOrPresent(message = "La fecha de ingreso no puede ser posterior al día de la fecha.")
    LocalDate fechaIngreso;

    @CreatedDate
    LocalDate fechaCreacion = LocalDate.now();
}
