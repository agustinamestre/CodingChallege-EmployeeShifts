package com.neoris.turnosrotativos.entities;

import com.neoris.turnosrotativos.services.ConceptType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "conceptos")
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nombre;
    private Boolean laborable;
    private Integer hsMinimo;
    private Integer hsMaximo;

    public ConceptType getConceptType() {
        return ConceptType.ofId(id);
    }
}
