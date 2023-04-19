package com.neoris.turnosrotativos.controllers.requests;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConceptRequest implements Serializable {
     Integer id;
     String nombre;
     Boolean laborable;
     Integer hsMinimo;
     Integer hsMaximo;
}
