package org.example.pharmacy.controller;

import org.example.pharmacy.controller.dto.CreateDrugRequestDto;
import org.example.pharmacy.controller.dto.DrugResponseDto;
import org.example.pharmacy.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drugs")
@PreAuthorize("isAuthenticated()")
public class DrugController {
    private final DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("permitAll()")
    public DrugResponseDto getDrug(@PathVariable Long id){
        return drugService.getDrug(id);
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public List<DrugResponseDto> getAllDrugs() {
        return drugService.getAllDrugs();
    }

    @PostMapping
    public DrugResponseDto createDrug(@Validated @RequestBody CreateDrugRequestDto drug) {
        return drugService.create(drug);
    }
}