package com.neoris.turnosrotativos.controllers;

import com.neoris.turnosrotativos.controllers.responses.ConceptResponse;
import com.neoris.turnosrotativos.services.ConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("concepto")
public class ConceptController {

    @Autowired
    ConceptService conceptService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ConceptResponse> getConcepts() {
        return conceptService.getConcepts();
    }
}
