package org.example.pharmacy.service;

import org.example.pharmacy.controller.dto.CreateDrugRequestDto;
import org.example.pharmacy.controller.dto.DrugResponseDto;
import org.example.pharmacy.controller.dto.PurchaseRequestDto; // Import new DTO
import org.example.pharmacy.infrastructure.entity.DrugEntity;
import org.example.pharmacy.infrastructure.repository.IDrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional
import org.springframework.web.server.ResponseStatusException; // Import ResponseStatusException

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrugService {
    private final IDrugRepository drugRepository;

    @Autowired
    public DrugService(IDrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    public DrugResponseDto getDrug(Long id) {
        var drug = drugRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drug not found"));
        return new DrugResponseDto(drug.getId(), drug.getName(), drug.getPrice(), drug.getDescription(), drug.getStock());
    }

    public List<DrugResponseDto> getAllDrugs() {
        return drugRepository.findAll().stream()
                .map(drugEntity -> new DrugResponseDto(drugEntity.getId(), drugEntity.getName(), drugEntity.getPrice(), drugEntity.getDescription(), drugEntity.getStock()))
                .collect(Collectors.toList());
    }

    public DrugResponseDto create(CreateDrugRequestDto drug) {
        var drugEntity = new DrugEntity();
        drugEntity.setName(drug.getName());
        drugEntity.setPrice(drug.getPrice());
        drugEntity.setDescription(drug.getDescription());
        drugEntity.setStock(drug.getStock()); // Set initial stock

        var savedDrug = drugRepository.save(drugEntity);

        return new DrugResponseDto(savedDrug.getId(), savedDrug.getName(), savedDrug.getPrice(), savedDrug.getDescription(), savedDrug.getStock());
    }

    @Transactional // Ensure atomicity for purchase operation
    public DrugResponseDto purchaseDrug(PurchaseRequestDto purchaseDto) {
        DrugEntity drug = drugRepository.findById(purchaseDto.getDrugId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drug not found"));

        if (drug.getStock() < purchaseDto.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock for drug: " + drug.getName());
        }

        drug.setStock(drug.getStock() - purchaseDto.getQuantity());
        DrugEntity updatedDrug = drugRepository.save(drug);

        return new DrugResponseDto(updatedDrug.getId(), updatedDrug.getName(), updatedDrug.getPrice(), updatedDrug.getDescription(), updatedDrug.getStock());
    }
}
