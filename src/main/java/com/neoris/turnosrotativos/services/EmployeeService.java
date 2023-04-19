package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.controllers.requests.EmployeeRequest;
import com.neoris.turnosrotativos.controllers.responses.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest employeeRequest);

    List<EmployeeResponse> getEmployees();
    EmployeeResponse getEmployee(Integer id);
    void deleteEmployee(Integer id);
    EmployeeResponse updateEmployee(Integer id, EmployeeRequest employeeRequest);
}
