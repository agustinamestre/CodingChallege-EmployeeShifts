package com.neoris.turnosrotativos.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity(name = "jornadas")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idJornada;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idEmpleado", referencedColumnName = "id")
    private Employee employee;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "jornada_concepto",
            joinColumns = @JoinColumn(name = "jornada_id"),
            inverseJoinColumns = @JoinColumn(name = "concepto_id"))
    private List<Concept> conceptos = new ArrayList<>();

    @Column(nullable = false)
    private LocalDate fecha;

    private Integer horasTrabajadas;

    public void addConcept(Concept concept){
        conceptos.add(concept);
    }

    public void setEmployee(Employee employee){
       this.employee = employee;
    }
}
