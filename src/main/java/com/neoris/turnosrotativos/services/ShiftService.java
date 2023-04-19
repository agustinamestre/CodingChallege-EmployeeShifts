package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.controllers.requests.ShiftRequest;
import com.neoris.turnosrotativos.controllers.responses.ShiftResponse;

import java.time.LocalDate;
import java.util.List;

public interface ShiftService {

    ShiftResponse createShift(ShiftRequest shiftRequest);

    List<ShiftResponse> getByFilter(Integer nroDocumento, LocalDate fecha);
}

