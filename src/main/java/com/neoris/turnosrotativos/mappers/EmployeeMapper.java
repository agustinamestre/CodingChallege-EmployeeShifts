package com.neoris.turnosrotativos.mappers;

import com.neoris.turnosrotativos.controllers.requests.EmployeeRequest;
import com.neoris.turnosrotativos.controllers.responses.EmployeeResponse;
import com.neoris.turnosrotativos.entities.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

//Para mejorar un poco la aplicacion, cree las entities de request y response, junto a los mappers.
//La habia empezado a hacer mas sencilla ya que al no tener mucho conocimiento de Java no queria
//arriesgarme a sea un desastre de errores jaja
//Anteriormente habia creado una api rest pero con express asi que tome mas o menos la estructura y la represente aca.

@Component
public class EmployeeMapper {

    ModelMapper modelMapper = new ModelMapper();

    public EmployeeResponse toResponse(Employee e) {
        return new EmployeeResponse(
                e.getId(),
                e.getNroDocumento(),
                e.getNombre(),
                e.getApellido(),
                e.getEmail(),
                e.getFechaNacimiento(),
                e.getFechaIngreso(),
                e.getFechaCreacion()
        );
    }

    public Employee toEntity(EmployeeRequest req) {
        return modelMapper.map(req, Employee.class);
    }
}
