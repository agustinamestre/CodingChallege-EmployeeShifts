package com.neoris.turnosrotativos.mappers;

import com.neoris.turnosrotativos.controllers.requests.ShiftRequest;
import com.neoris.turnosrotativos.controllers.responses.ShiftResponse;
import com.neoris.turnosrotativos.entities.Concept;
import com.neoris.turnosrotativos.entities.Shift;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//como la estructura de datos de la entidad no sea la misma que la estructura de datos que se debe mostrar en
// la respuesta que se envia al cliente, es conveniente realizar un mapper para hacer una conversion correcta y
//mantener la separación de responsabilidades.

@AllArgsConstructor
@Component
public class ShiftMapper {

    private final ConceptMapper conceptMapper;

    public ShiftResponse toResponse(Shift shift, Concept concept) {

        var nombreCompleto = shift.getEmployee()
                .getNombre() + " " + shift.getEmployee()
                .getApellido();

        return ShiftResponse.builder()
                .idJornada(shift.getIdJornada())
                .nombreCompleto(nombreCompleto)
                .concepto(concept.getNombre())
                .nroDocumento(shift.getEmployee().getNroDocumento())
                .fecha(shift.getFecha())
                .horasTrabajadas(shift.getHorasTrabajadas())
                .build();
    }

    public Shift toEntity(ShiftRequest req) {
        return Shift.builder()
                .fecha(req.getFecha())
                .horasTrabajadas(req.getHorasTrabajadas())
                .conceptos(new ArrayList<>())
                .build();
    }
}
