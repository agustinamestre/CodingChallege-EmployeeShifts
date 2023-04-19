package com.neoris.turnosrotativos.services.impl;

import com.neoris.turnosrotativos.controllers.responses.ConceptResponse;
import com.neoris.turnosrotativos.entities.Concept;
import com.neoris.turnosrotativos.mappers.ConceptMapper;
import com.neoris.turnosrotativos.repositories.ConceptRepository;
import com.neoris.turnosrotativos.services.ConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConceptServiceImpl implements ConceptService {

    @Autowired
    ConceptRepository conceptRepository;

    @Autowired
    ConceptMapper conceptMapper;

    @Override
    public List<ConceptResponse> getConcepts() {
       var concepts = conceptRepository.findAll();
        List<ConceptResponse> conceptResponses = new ArrayList<>();
        //Como obtengo una lista de concepts, tengo que mapear los conceptos para que sean de tipo response.
        for(Concept c : concepts){
            var conceptResponse = conceptMapper.toResponse(c);
            conceptResponses.add(conceptResponse);
        }

        return conceptResponses;
    }
}
