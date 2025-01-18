package com.urbanhoney.backend.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urbanhoney.backend.models.CategorieEntity;
import com.urbanhoney.backend.models.SubCategorieEntity;
import com.urbanhoney.backend.repository.CategorieRepository;
import com.urbanhoney.backend.repository.SubCategorieRepository;
import com.urbanhoney.backend.usecase.dto.CategorieDto;
import com.urbanhoney.backend.usecase.dto.SubCategorieDto;
import com.urbanhoney.backend.usecase.dto.mapper.CategorieMapper;
import com.urbanhoney.backend.usecase.dto.mapper.SubCategorieMapper;
import com.urbanhoney.backend.usecase.dto.request.AddCategorieRequestDto;
import com.urbanhoney.backend.usecase.dto.request.AddSubCategorieRequestDto;

@Service
public class CategorieService {
    
    private CategorieRepository categorieRepository;

    @Autowired
    private SubCategorieRepository subCategorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public ResponseEntity<?> addNewCategorie(AddCategorieRequestDto addCategorieRequestDto) {

        Map<String, String> response = new HashMap<>();

        if (addCategorieRequestDto.getCategorie().isEmpty() || addCategorieRequestDto.getGender().isEmpty()) {
            response.put("error", "Please fill requirement !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        CategorieEntity categorieEntity = CategorieMapper.mapToCategorieEntity(addCategorieRequestDto);
        categorieRepository.save(categorieEntity);
        response.put("success", "Categorie added");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<?> addNewSubCategorie(AddSubCategorieRequestDto addSubCategorieRequestDto) {

        Map<String, String> response = new HashMap<>();

        CategorieEntity categorieEntity = categorieRepository.findByCategorieId(addSubCategorieRequestDto.getCategorieLinkId().getCategorieId()).orElse(null);

        if (categorieEntity == null) {
            response.put("error", "Categorie not found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        if (addSubCategorieRequestDto.getGender().isEmpty()) {
            response.put("error", "Please select a valid gender");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        SubCategorieEntity subCategorieEntity = SubCategorieMapper.mapToSubCategorieEntity(addSubCategorieRequestDto);
        subCategorieRepository.save(subCategorieEntity);
        response.put("success", "Sub categorie added");
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<?> getAllCategories() {

        Map<String, String> response = new HashMap<>();

        List<CategorieEntity> categorieEntity = categorieRepository.findAll();

        if (categorieEntity == null) {
            response.put("error", "categories not found, an error has occured !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List<CategorieDto> categoriesDtos = categorieEntity.stream()
                .map(CategorieMapper::mapToCategorieDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(categoriesDtos);
    }

    public ResponseEntity<?> getCategorieByName(String subCategorieName) {

        Map<String, String> response = new HashMap<>();

        CategorieEntity categorieEntity = categorieRepository.findByCategorie(subCategorieName).orElse(null);
        if (categorieEntity == null) {
            response.put("error", "Categorie not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        CategorieDto CategorieDto = CategorieMapper.mapToCategorieDto(categorieEntity);
        return ResponseEntity.status(HttpStatus.OK).body(CategorieDto);
    }


    public ResponseEntity<?> getAllSubCategories() {

        Map<String, String> response = new HashMap<>();

        List<SubCategorieEntity> subCategorieEntities = subCategorieRepository.findAll();
        if (subCategorieEntities == null) {
            response.put("error", "sub categories not found, an error has occured !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List<SubCategorieDto> subCategorieDto = subCategorieEntities.stream()
                .map(SubCategorieMapper::mapToSubCategorieDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(subCategorieDto);
    }

    public ResponseEntity<?> getSubCategorieByName(String subCategorieName) {

        Map<String, String> response = new HashMap<>();
        
        SubCategorieEntity subCategorieEntity = subCategorieRepository.findBySubCategorieName(subCategorieName).orElse(null);
        if (subCategorieEntity == null) {
            response.put("error", "Sub categorie not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        SubCategorieDto subCategorieDto = SubCategorieMapper.mapToSubCategorieDto(subCategorieEntity);
        return ResponseEntity.status(HttpStatus.OK).body(subCategorieDto);
    }

    @Transactional
    public ResponseEntity<?> deleteSubCategorieByName(String subCategorieName) {

        Map<String, String> response = new HashMap<>();
        
        SubCategorieEntity findSubCategorieEntity = subCategorieRepository.findBySubCategorieName(subCategorieName).orElse(null);
        if (findSubCategorieEntity == null) {
            response.put("error", "Sub categorie not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
        }

        subCategorieRepository.deleteBySubCategorieName(subCategorieName);
        response.put("success", "Sub categorie"+subCategorieName+" deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
