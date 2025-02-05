package com.urbanhoney.backend.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return String.join(",", attribute); // Transformation de la liste en une chaîne séparée par des virgules
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return new ArrayList<>(); // Si la base de données est vide ou mal formatée, retourne une liste vide
        }
        return Arrays.asList(dbData.split(",")); // Sépare les éléments par une virgule
    }
}