package org.example.pharmacy.service;

import org.example.pharmacy.controller.dto.CreateDrugRequestDto;
import org.example.pharmacy.controller.dto.DrugResponseDto;
import org.example.pharmacy.infrastructure.entity.DrugEntity;
import org.example.pharmacy.infrastructure.repository.IDrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugService {
    private final IDrugRepository drugRepository;
    @Autowired
    public DrugService(IDrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    public DrugResponseDto getDrug(Long id) {
        var drug = drugRepository.findById(id).orElseThrow(() -> new RuntimeException("Drug not found"));
        return new DrugResponseDto(drug.getId(), drug.getName(), drug.getPrice(), drug.getDescription());
    }

    public DrugResponseDto create(CreateDrugRequestDto drug) {
        var drugEntity = new DrugEntity();
        drugEntity.setName(drug.getName());
        drugEntity.setPrice(drug.getPrice());
        drugEntity.setDescription(drug.getDescription());

        var savedDrug = drugRepository.save(drugEntity);

        return new DrugResponseDto(savedDrug.getId(), savedDrug.getName(), savedDrug.getPrice(), savedDrug.getDescription());
    }
}
