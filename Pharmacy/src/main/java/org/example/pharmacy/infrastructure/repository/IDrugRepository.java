package org.example.pharmacy.infrastructure.repository;

import org.example.pharmacy.infrastructure.entity.DrugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDrugRepository extends JpaRepository<DrugEntity, Long> {

}