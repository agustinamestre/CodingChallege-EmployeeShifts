package com.neoris.turnosrotativos.mappers;

import com.neoris.turnosrotativos.controllers.requests.ConceptRequest;
import com.neoris.turnosrotativos.controllers.responses.ConceptResponse;
import com.neoris.turnosrotativos.entities.Concept;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ConceptMapper {

    ModelMapper modelMapper = new ModelMapper();

    public ConceptResponse toResponse(Concept c){
        return new ConceptResponse(
                c.getId(),
                c.getNombre(),
                c.getLaborable(),
                c.getHsMinimo(),
                c.getHsMaximo()
        );
    }

    public Concept toEntity(ConceptRequest req){
        return modelMapper.map(req, Concept.class);
    }
}
