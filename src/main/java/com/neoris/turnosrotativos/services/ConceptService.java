package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.controllers.responses.ConceptResponse;

import java.util.List;

public interface ConceptService {

    List<ConceptResponse> getConcepts();
}
