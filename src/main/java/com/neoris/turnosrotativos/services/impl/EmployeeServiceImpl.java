package com.neoris.turnosrotativos.services.impl;

import com.neoris.turnosrotativos.controllers.requests.EmployeeRequest;
import com.neoris.turnosrotativos.controllers.responses.EmployeeResponse;
import com.neoris.turnosrotativos.entities.Employee;
import com.neoris.turnosrotativos.exceptions.ConflictException;
import com.neoris.turnosrotativos.exceptions.ErrorMessage;
import com.neoris.turnosrotativos.exceptions.NoDataFoundException;
import com.neoris.turnosrotativos.exceptions.ResourceNotFoundException;
import com.neoris.turnosrotativos.mappers.EmployeeMapper;
import com.neoris.turnosrotativos.repositories.EmployeeRepository;
import com.neoris.turnosrotativos.services.EmployeeService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        //mapeo la response a entity para poder ir al repositorio y chequear si los datos ya existen
        var e = employeeMapper.toEntity(employeeRequest);

        if (employeeRepository.existByDocumentNumber(e.getNroDocumento())) {
            throw new ConflictException(ErrorMessage.EXISTING_DOCUMENT);
        }

        if (employeeRepository.existByEmail(e.getEmail())) {
            throw new ConflictException(ErrorMessage.EXISTING_EMAIL);
        }

        e = employeeRepository.save(e);

        return employeeMapper.toResponse(e);
    }

    @Override
    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest employeeRequest) {
        var e = employeeMapper.toEntity(employeeRequest);

        //tiro error si el empleado no existe
        var existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.EMPLOYEE_NOT_FOUND + id));

        if (e.getNroDocumento() == null) {
            throw new NoDataFoundException(ErrorMessage.DOCUMENT_NOT_NULL);
        }

        if(employeeRepository.existOtherEmployeeWithSameDocumentNumber(e.getNroDocumento(), existingEmployee.getId())){
            throw new ConflictException(ErrorMessage.EXISTING_DOCUMENT);
        }

        if(employeeRepository.existOtherEmployeeWithSameEmail(e.getEmail(), existingEmployee.getId())){
            throw new ConflictException(ErrorMessage.EXISTING_EMAIL);
        }

        //seteo los campos actualizados
        existingEmployee.setNroDocumento(e.getNroDocumento());
        existingEmployee.setNombre(e.getNombre());
        existingEmployee.setApellido(e.getApellido());
        existingEmployee.setEmail(e.getEmail());
        existingEmployee.setFechaNacimiento(e.getFechaNacimiento());
        existingEmployee.setFechaIngreso(e.getFechaIngreso());

        employeeRepository.save(existingEmployee);

        return employeeMapper.toResponse(existingEmployee);
    }

    @Override
    public List<EmployeeResponse> getEmployees() {
        List<EmployeeResponse> employeeResponses = employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());

        return employeeResponses;
    }

    @Override
    public EmployeeResponse getEmployee(Integer id) {
        Optional<Employee> e = employeeRepository.findById(id);

        if (e.isPresent()) {
            EmployeeResponse employeeResponse = employeeMapper.toResponse(e.get());
            return employeeResponse;
        } else {
            throw new ResourceNotFoundException(ErrorMessage.EMPLOYEE_NOT_FOUND + id);
        }
    }

    @Override
    public void deleteEmployee(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.delete(employee.get());
        } else {
            throw new ResourceNotFoundException(ErrorMessage.EMPLOYEE_NOT_FOUND + id);
        }
    }
}