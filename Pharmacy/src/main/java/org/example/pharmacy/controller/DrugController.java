package org.example.pharmacy.controller;

import org.example.pharmacy.controller.dto.CreateDrugRequestDto;
import org.example.pharmacy.controller.dto.DrugResponseDto;
import org.example.pharmacy.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    //public GetDrugDto getDrugById(@PathVariable Long id){return drugService.getD}
    @PreAuthorize("permitAll()")
    public DrugResponseDto getDrug(@PathVariable Long id){
        return drugService.getDrug(id);
    }

    /*
    @ResponseStatus
     */
    @PostMapping
    public DrugResponseDto createDrug(@Validated @RequestBody CreateDrugRequestDto drug) {
        return drugService.create(drug);
    }
    /*
    @PostMapping
    public ResponceEntity<CreateDrugResponseDto> createDrug(@Validated @RequestBody CreateDrugResponse)
        var newDrug = drugService.createDrug(drug);
        return new ResponseEntity<>(newDrug,HttpStatus.BAD_GATEWAY);

        albo
    @PostMapping
    public ResponceEntity<CreateDrugResponseDto> createDrug(@Validated @RequestBody CreateDrugResponse)
        var newDrug = drugService.createDrug(drug);
        return newDrug;
    */

    /*@DeleteMapping("{id}")
    public ResponseEntity<Object> deleteDrug(@PathVariable Long id)
        drugService.deleteDrug(id);
        return ResponseEntity.noContent().build()
        */

}