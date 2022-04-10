package com.project.repository;

import com.project.entity.UnitTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitTypesRepo extends JpaRepository<UnitTypes, Long> {
}
