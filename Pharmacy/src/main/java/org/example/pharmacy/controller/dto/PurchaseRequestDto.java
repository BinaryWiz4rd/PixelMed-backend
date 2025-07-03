package org.example.pharmacy.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PurchaseRequestDto {
    @NotNull(message = "drug ID cannot be null")
    private Long drugId;

    @Min(value = 1, message = "quantity must be at least 1")
    private int quantity;

    public PurchaseRequestDto(Long drugId, int quantity) {
        this.drugId = drugId;
        this.quantity = quantity;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
