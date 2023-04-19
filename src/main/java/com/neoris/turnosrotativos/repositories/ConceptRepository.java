package com.neoris.turnosrotativos.repositories;

import com.neoris.turnosrotativos.entities.Concept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptRepository extends JpaRepository<Concept, Integer> {}
