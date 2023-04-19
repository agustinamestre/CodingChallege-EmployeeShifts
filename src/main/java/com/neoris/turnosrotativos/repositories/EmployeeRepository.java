package com.neoris.turnosrotativos.repositories;

import com.neoris.turnosrotativos.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM employee e WHERE e.nroDocumento = ?1")
    boolean existByDocumentNumber(Integer nroDocumento);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM employee e WHERE  e.email = ?1")
    boolean existByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM employee e WHERE (e.nroDocumento = ?1) AND (e.id != ?2)")
    boolean existOtherEmployeeWithSameDocumentNumber(Integer nroDocumento, Integer id);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM employee e WHERE (e.email = ?1) AND (e.id != ?2)")
    boolean existOtherEmployeeWithSameEmail(String email, Integer id);
}