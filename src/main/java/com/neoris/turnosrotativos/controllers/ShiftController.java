package com.neoris.turnosrotativos.controllers;

import com.neoris.turnosrotativos.controllers.requests.ShiftRequest;
import com.neoris.turnosrotativos.controllers.responses.ShiftResponse;
import com.neoris.turnosrotativos.services.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("jornada")
public class ShiftController {

    @Autowired
    ShiftService shiftService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ShiftResponse createJornada(@Valid @RequestBody ShiftRequest shiftRequest){
        return shiftService.createShift(shiftRequest);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ShiftResponse> getJornadasByDocumentAndDate(
            @RequestParam(required = false) Integer nroDocumento,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha){
        return shiftService.getByFilter(nroDocumento, fecha);
    }
}
