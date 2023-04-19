package com.neoris.turnosrotativos.services.impl;

import com.neoris.turnosrotativos.controllers.requests.ShiftRequest;
import com.neoris.turnosrotativos.controllers.responses.ShiftResponse;
import com.neoris.turnosrotativos.entities.Concept;
import com.neoris.turnosrotativos.entities.Shift;
import com.neoris.turnosrotativos.exceptions.ErrorMessage;
import com.neoris.turnosrotativos.exceptions.InvalidConceptDataException;
import com.neoris.turnosrotativos.exceptions.ResourceNotFoundException;
import com.neoris.turnosrotativos.mappers.ShiftMapper;
import com.neoris.turnosrotativos.repositories.ConceptRepository;
import com.neoris.turnosrotativos.repositories.EmployeeRepository;
import com.neoris.turnosrotativos.repositories.ShiftRepository;
import com.neoris.turnosrotativos.services.ConceptType;
import com.neoris.turnosrotativos.services.ShiftService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    ShiftRepository shiftRepository;

    @Autowired
    ShiftMapper shiftMapper;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ConceptRepository conceptRepository;

    @Override
    public ShiftResponse createShift(ShiftRequest shiftRequest) {
        var employee = employeeRepository.findById(shiftRequest.getIdEmpleado())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.EMPLOYEE_NOT_EXIST));

        var concept = conceptRepository.findById(shiftRequest.getIdConcepto())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CONCEPT_NOT_FOUND));

        validateWorkedHours(concept, shiftRequest.getHorasTrabajadas());

        var shift = shiftMapper.toEntity(shiftRequest);

        shift.addConcept(concept);

        shift.setEmployee(employee);

        validateShift(shift, concept);

        validateWorkedHoursSameDay(shift, concept);

        validateFreeDaysWeek(shift, concept);

        validateShiftWeek(shift, concept);

        shiftRepository.save(shift);

        return shiftMapper.toResponse(shift, concept);
    }

    @Override
    public List<ShiftResponse> getByFilter(Integer nroDocumento, LocalDate fecha) {
        List<ShiftResponse> jornadas = shiftRepository.getShiftsByFilter(nroDocumento, fecha)
                .stream()
                .flatMap(shift -> shift.getConceptos()
                        .stream()
                        .map(concept -> shiftMapper.toResponse(shift, concept)))
                .collect(Collectors.toList());

        return jornadas;
    }

    private void validateWorkedHours(Concept concept, Integer workedHours) {
        var conceptType = concept.getConceptType();
        if (conceptType == ConceptType.NORMAL || conceptType == ConceptType.EXTRA) {
            if (workedHours == null) {
                throw new InvalidConceptDataException(ErrorMessage.HS_TRABAJADAS_REQUIRED);
            }
            if (conceptType == ConceptType.NORMAL) {
                if (workedHours < 6 || workedHours > 8) {
                    throw new InvalidConceptDataException(ErrorMessage.HORAS_NORMAL);
                }
            } else if (conceptType == ConceptType.EXTRA) {
                if (workedHours < 2 || workedHours > 6) {
                    throw new InvalidConceptDataException(ErrorMessage.HORAS_EXTRA);
                }
            }
        } else if (conceptType == ConceptType.FREE) {
            if (workedHours != null) {
                throw new InvalidConceptDataException(ErrorMessage.HS_TRABAJADAS_NOT_REQUIRED);
            }
        }
    }

    private void validateShift(Shift shift, Concept concept) {
        var shifts = shiftRepository.shiftsEmployeeSameDay(shift.getEmployee()
                                                                         .getNroDocumento(), shift.getFecha());
        shifts.stream()
                .map(Shift::getConceptos)
                .flatMap(List::stream)
                .forEach(c -> {
                    if (concept.getConceptType() == ConceptType.FREE) {
                        throw new InvalidConceptDataException(ErrorMessage.NO_FREE_DAY_IN_A_WORK_DAY);
                    }
                    if (c.getConceptType() == concept.getConceptType()) {
                        throw new InvalidConceptDataException(ErrorMessage.EMPLOYEE_WITH_SAME_CONCEPT);
                    }
                    if (c.getConceptType() == ConceptType.FREE) {
                        throw new InvalidConceptDataException(ErrorMessage.EMPLOYEE_WITH_FREE_DAY);
                    }
                });
    }

    private void validateWorkedHoursSameDay(Shift shift, Concept concept) {
        if (concept.getConceptType() == ConceptType.FREE) {
            return;
        }

        var jornadas = shiftRepository.shiftsEmployeeSameDay(shift.getEmployee()
                                                                         .getNroDocumento(), shift.getFecha());
        int workedHoursTotal = jornadas.stream()
                .mapToInt(Shift::getHorasTrabajadas)
                .reduce(0, Integer::sum);

        int workedHoursNewJornada = shift.getHorasTrabajadas();

        if (workedHoursTotal + workedHoursNewJornada > 12) {
            throw new InvalidConceptDataException(ErrorMessage.NO_MORE_THAN_12_HOURS_DAY);
        }
    }

    private void validateShiftWeek(Shift shift, Concept concept) {
        LocalDate fecha = shift.getFecha();
        LocalDate monday = fecha.with(DayOfWeek.MONDAY);
        LocalDate sunday = fecha.with(DayOfWeek.SUNDAY);

        if (concept.getConceptType() == ConceptType.FREE) {
            return;
        }

        var shifts = shift.getEmployee()
                .getShifts()
                .stream()
                .filter(j -> j.getFecha()
                        .isEqual(monday) || j.getFecha()
                        .isEqual(sunday) || (j.getFecha()
                        .isAfter(monday) && j.getFecha()
                        .isBefore(sunday)))
                .collect(Collectors.toList());

        int workedHoursTotal = shifts.stream()
                .filter(j -> j.getHorasTrabajadas() != null)
                .mapToInt(Shift::getHorasTrabajadas)
                .reduce(0, Integer::sum);

        int workedHoursNewJornada = shift.getHorasTrabajadas();

        if (workedHoursTotal + workedHoursNewJornada > 48) {
            throw new InvalidConceptDataException(ErrorMessage.NO_MORE_THAN_48_HOURS_A_WEEK);
        }

        long extraTurns = shifts.stream()
                .map(Shift::getConceptos)
                .flatMap(List::stream)
                .filter(c -> c.getConceptType() == ConceptType.EXTRA)
                .count();

        if (extraTurns == 3 && concept.getConceptType() == ConceptType.EXTRA) {
            throw new InvalidConceptDataException(ErrorMessage.NO_MORE_THAN_3_EXTRA_TURNS_A_WEEK);
        }

        long normalTurns = shifts.stream()
                .map(Shift::getConceptos)
                .flatMap(List::stream)
                .filter(c -> c.getConceptType() == ConceptType.NORMAL)
                .count();

        if (normalTurns == 5 && concept.getConceptType() == ConceptType.NORMAL) {
            throw new InvalidConceptDataException(ErrorMessage.NO_MORE_THAN_5_NORMAL_TURNS_A_WEEK);
        }
    }

    private void validateFreeDaysWeek(Shift shift, Concept concept) {
        LocalDate fecha = shift.getFecha();
        LocalDate monday = fecha.with(DayOfWeek.MONDAY);
        LocalDate sunday = fecha.with(DayOfWeek.SUNDAY);

        var jornadas = shift.getEmployee()
                .getShifts()
                .stream()
                .filter(j -> j.getFecha()
                        .isEqual(monday) || j.getFecha()
                        .isEqual(sunday) || (j.getFecha()
                        .isAfter(monday) && j.getFecha()
                        .isBefore(sunday)))
                .collect(Collectors.toList());

        long freeDays = jornadas.stream()
                .map(Shift::getConceptos)
                .flatMap(List::stream)
                .filter(c -> c.getConceptType() == ConceptType.FREE)
                .count();

        if (freeDays == 2 && concept.getConceptType() == ConceptType.FREE) {
            throw new InvalidConceptDataException(ErrorMessage.NO_MORE_THAN_2_FREE_DAYS_A_WEEK);
        }
    }
}