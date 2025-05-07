package org.example.pharmacy.service.valueObjects;

public class Price {
    private final float value;

    public Price(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public static Price create(float value) {
        if (value < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        var roundedPrice = (float)(Math.round(value * 100.0) / 100.0);

        return new Price(roundedPrice);
    }
}