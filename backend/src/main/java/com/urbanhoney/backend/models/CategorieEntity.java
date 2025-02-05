package com.urbanhoney.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class CategorieEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @JsonProperty("category_id")
    private Integer categoryId;

    @Column(name = "category_name")
    @JsonProperty("category_name")
    private String categoryName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "category_picture")
    private String categoryPicture;
    
}
