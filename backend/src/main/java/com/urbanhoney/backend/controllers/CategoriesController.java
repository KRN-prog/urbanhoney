package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanhoney.backend.services.CategorieService;
import com.urbanhoney.backend.services.IsAdminService;
import com.urbanhoney.backend.usecase.dto.request.AddCategorieRequestDto;
import com.urbanhoney.backend.usecase.dto.request.AddSubCategorieRequestDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/urbanhoney/categorie")
public class CategoriesController {

    @Autowired
    private CategorieService categorieService;

    private final IsAdminService isAdminService;

    public CategoriesController(IsAdminService isAdminService) {
        this.isAdminService = isAdminService;
    }
    
    @PostMapping("/new")
    public ResponseEntity<?> postNewCategorie(@Valid @RequestBody AddCategorieRequestDto addCategorieRequestDto, HttpServletRequest request) {
        if (!isAdminService.isAdmin(request)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "You don't have the permission to do that action"));
        }
        return categorieService.addNewCategorie(addCategorieRequestDto);
    }

    @PostMapping("/sub_categorie/new")
    public ResponseEntity<?> postNewSubCategorie(@Valid @RequestBody AddSubCategorieRequestDto AddSubCategorieRequestDto, HttpServletRequest request) {
        if (!isAdminService.isAdmin(request)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "You don't have the permission to do that action"));
        }
        return categorieService.addNewSubCategorie(AddSubCategorieRequestDto);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllCategories() {
        return categorieService.getAllCategories();
    }
    
    @GetMapping("/get/{categorieName}")
    public ResponseEntity<?> getCategorieById(@PathVariable("categorieName") String categorieName) {
        return categorieService.getCategorieByName(categorieName);
    }
    
    @GetMapping("/sub_categorie")
    public ResponseEntity<?> getAllSubCategories() {
        return categorieService.getAllSubCategories();
    }

    @GetMapping("/sub_categorie/{subCategorieName}")
    public ResponseEntity<?> getSubCategorieByName(@PathVariable("subCategorieName") String subCategorieName) {
        return categorieService.getSubCategorieByName(subCategorieName);
    }

    @GetMapping("/get/sub_categorie/categorie/{categorieName}")
    public ResponseEntity<?> getSubCategorieByCategorieName(@PathVariable("categorieName") String categorieName) {
        return categorieService.getAllSubCategoriesByCategorieName(categorieName);
    }
    
    @DeleteMapping("/sub_categorie/delete/{subCategorieName}")
    public ResponseEntity<?> deleteSubCategorieByName(@PathVariable("subCategorieName") String subCategorieName, HttpServletRequest request) {
        if (!isAdminService.isAdmin(request)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "You don't have the permission to do that action"));
        }
        return categorieService.deleteSubCategorieByName(subCategorieName);
    }
}
