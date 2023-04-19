package com.neoris.turnosrotativos.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

//cree este enum ya que los tipos de concepto son constantes y para poder validar de una manera mas sencilla

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ConceptType {

    NORMAL(1), EXTRA(2), FREE(3);

    Integer conceptId;

    ConceptType(Integer conceptId) {
        this.conceptId = conceptId;
    };

    public static ConceptType ofId(Integer conceptId) {
        if (conceptId == 1) {
            return ConceptType.NORMAL;
        } else if (conceptId == 2) {
            return ConceptType.EXTRA;
        } else {
            return ConceptType.FREE;
        }
    }
}
