package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanhoney.backend.services.CategorieService;
import com.urbanhoney.backend.usecase.dto.request.AddCategorieRequestDto;
import com.urbanhoney.backend.usecase.dto.request.AddSubCategorieRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/urbanhoney/categorie")
public class CategoriesController {

    @Autowired
    private CategorieService categorieService;
    
    @PostMapping("/new")
    public ResponseEntity<?> postNewCategorie(@RequestBody AddCategorieRequestDto addCategorieRequestDto , Authentication authentication) {
        return categorieService.addNewCategorie(addCategorieRequestDto);
    }

    @PostMapping("/subCategorie/new")
    public ResponseEntity<?> postNewSubCategorie(@RequestBody AddSubCategorieRequestDto AddSubCategorieRequestDto , Authentication authentication) {
        return categorieService.addNewSubCategorie(AddSubCategorieRequestDto);
    }
    
}
