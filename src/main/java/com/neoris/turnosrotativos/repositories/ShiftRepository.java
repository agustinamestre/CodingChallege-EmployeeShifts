package com.neoris.turnosrotativos.repositories;

import com.neoris.turnosrotativos.entities.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    @Query("SELECT j FROM jornadas j WHERE j.employee.nroDocumento = :nroDocumento AND j.fecha = :fecha")
    List<Shift> shiftsEmployeeSameDay(@Param("nroDocumento") Integer nroDocumento, @Param("fecha") LocalDate fecha);

    @Query("SELECT j FROM jornadas j WHERE (:nroDocumento is NULL or j.employee.nroDocumento = :nroDocumento) and " + "(:fecha is NULL or j.fecha = :fecha)")
    List<Shift> getShiftsByFilter(@Param("nroDocumento") Integer nroDocumento, @Param("fecha") LocalDate fecha);
}
