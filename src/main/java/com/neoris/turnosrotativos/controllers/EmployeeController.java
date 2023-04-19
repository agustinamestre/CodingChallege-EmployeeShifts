package com.neoris.turnosrotativos.controllers;

import com.neoris.turnosrotativos.controllers.requests.EmployeeRequest;
import com.neoris.turnosrotativos.controllers.responses.EmployeeResponse;
import com.neoris.turnosrotativos.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "empleado")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
          return employeeService.createEmployee(employeeRequest);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponse> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse getEmployee(@PathVariable Integer id) {
        return employeeService.getEmployee(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse updateEmployee(@PathVariable Integer id, @Valid @RequestBody EmployeeRequest employeeRequest){
       return employeeService.updateEmployee(id, employeeRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
    }
}
