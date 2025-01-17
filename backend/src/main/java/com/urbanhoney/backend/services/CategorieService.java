package com.urbanhoney.backend.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.urbanhoney.backend.models.CategorieEntity;
import com.urbanhoney.backend.repository.CategorieRepository;
import com.urbanhoney.backend.repository.SubCategorieRepository;
import com.urbanhoney.backend.usecase.dto.mapper.CategorieMapper;
import com.urbanhoney.backend.usecase.dto.request.AddCategorieRequestDto;
import com.urbanhoney.backend.usecase.dto.request.AddSubCategorieRequestDto;

@Service
public class CategorieService {
    
    private CategorieRepository categorieRepository;

    private SubCategorieRepository subCategorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    Map<String, String> response = new HashMap<>();

    public ResponseEntity<?> addNewCategorie(AddCategorieRequestDto addCategorieRequestDto) {

        if (addCategorieRequestDto.getCategorie().isEmpty() || addCategorieRequestDto.getGender().isEmpty()) {
            response.put("error", "Please fill requirement !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        CategorieEntity categorieEntity = CategorieMapper.mapToUserDto(addCategorieRequestDto);
        categorieRepository.save(categorieEntity);
        response.put("success", "Categorie added");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<?> addNewSubCategorie(AddSubCategorieRequestDto addSubCategorieRequestDto) {
        CategorieEntity categorieEntity = categorieRepository.findByCategorieId(addSubCategorieRequestDto.getCategorieLinkId().getCategorieId()).orElse(null);

        if (categorieEntity == null) {
            response.put("error", "Categorie not found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        System.out.println(categorieEntity);
        return ResponseEntity.status(HttpStatus.OK).body("response");
    }
}
