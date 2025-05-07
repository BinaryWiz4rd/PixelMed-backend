package org.example.pharmacy.controller.dto;

public class CreateUserResponseDto {

    private long id;

    public CreateUserResponseDto(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}