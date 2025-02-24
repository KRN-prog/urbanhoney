package com.urbanhoney.backend.services;

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

    public ResponseEntity<Map<String, String>> addNewCategorie(AddCategorieRequestDto addCategorieRequestDto) {

        if (addCategorieRequestDto.getCategoryName() == null || addCategorieRequestDto.getCategoryName().isEmpty() ||
            addCategorieRequestDto.getGender() == null || addCategorieRequestDto.getGender().isEmpty() ||
            addCategorieRequestDto.getCategoryPicture() == null || addCategorieRequestDto.getCategoryPicture().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Please fill in all required fields!"));
        }
    
        String normalizedCategorie = addCategorieRequestDto.getCategoryName().trim().replace(" ", "_");
        addCategorieRequestDto.setCategoryName(normalizedCategorie);
    
        List<String> validGenders = List.of("H", "F", "H/F");
        if (!validGenders.contains(addCategorieRequestDto.getGender())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid gender. Please select a valid option: H, F, or H/F."));
        }
    
        CategorieEntity categorieEntity = CategorieMapper.mapToCategorieEntity(addCategorieRequestDto);
        categorieRepository.save(categorieEntity);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("success", "Category added successfully!"));
    }
    

    public ResponseEntity<Map<String, String>> addNewSubCategorie(AddSubCategorieRequestDto addSubCategorieRequestDto) {

        String normalizedSubCategorie = addSubCategorieRequestDto.getSubCategorieName().trim().replace(" ", "_");
        addSubCategorieRequestDto.setSubCategorieName(normalizedSubCategorie);

        if (categorieRepository.existsByCategoryId(addSubCategorieRequestDto.getCategorieLinkId().getCategoryId()) == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Categorie not found !"));
        }
        
        List<String> validGenders = List.of("H", "F", "H/F");
        if (!validGenders.contains(addSubCategorieRequestDto.getGender())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Please select a valid gender"));
        }

        SubCategorieEntity subCategorieEntity = SubCategorieMapper.mapToSubCategorieEntity(addSubCategorieRequestDto);
        subCategorieRepository.save(subCategorieEntity);
        
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", "Sub categorie added"));
    }

    public ResponseEntity<?> getAllCategories() {

        List<CategorieEntity> categorieEntity = categorieRepository.findAll();

        if (categorieEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "categories not found, an error has occured !"));
        }

        List<CategorieDto> categoriesDtos = categorieEntity.stream()
                .map(CategorieMapper::mapToCategorieDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", categoriesDtos));
    }

    public ResponseEntity<?> getCategorieByName(String subCategorieName) {

        CategorieEntity categorieEntity = categorieRepository.findByCategoryName(subCategorieName).orElse(null);
        if (categorieEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Categorie not found"));
        }

        CategorieDto CategorieDto = CategorieMapper.mapToCategorieDto(categorieEntity);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", CategorieDto));
    }

    public ResponseEntity<?> getAllSubCategories() {

        List<SubCategorieEntity> subCategorieEntities = subCategorieRepository.findAll();
        if (subCategorieEntities == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "sub categories not found, an error has occured !"));
        }

        List<SubCategorieDto> subCategorieDto = subCategorieEntities.stream()
                .map(SubCategorieMapper::mapToSubCategorieDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", subCategorieDto));
    }

    public ResponseEntity<?> getSubCategorieByName(String subCategorieName) {
        
        SubCategorieEntity subCategorieEntity = subCategorieRepository.findBySubCategoryName(subCategorieName).orElse(null);
        if (subCategorieEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Sub categorie not found"));
        }

        SubCategorieDto subCategorieDto = SubCategorieMapper.mapToSubCategorieDto(subCategorieEntity);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", subCategorieDto));
    }

    public ResponseEntity<?> getAllSubCategoriesByCategorieName(String categorieName) {
        
        List<SubCategorieEntity> subCategorieEntity = subCategorieRepository.findAllByCategorie_CategoryName(categorieName);
        if (subCategorieEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Sub categorie not found"));
        }

        List<SubCategorieDto> subCategorieDto = subCategorieEntity.stream()
                .map(SubCategorieMapper::mapToSubCategorieDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", subCategorieDto));
    }

    @Transactional
    public ResponseEntity<?> deleteSubCategorieByName(String subCategorieName) {
        
        SubCategorieEntity findSubCategorieEntity = subCategorieRepository.findBySubCategoryName(subCategorieName).orElse(null);
        if (findSubCategorieEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Sub categorie not found")); 
        }

        subCategorieRepository.deleteBySubCategoryName(subCategorieName);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", "Sub categorie"+subCategorieName+" deleted"));
    }
}
