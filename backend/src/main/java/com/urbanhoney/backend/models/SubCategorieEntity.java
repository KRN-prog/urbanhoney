package com.urbanhoney.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "sub_categorie")
@AllArgsConstructor
@NoArgsConstructor
public class SubCategorieEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id")
    @JsonProperty("sub_category_id")
    private Integer subcategoryId;

    @Column(name = "sub_category_name")
    @JsonProperty("sub_category_name")
    private String subCategoryName;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_linked_id", referencedColumnName = "category_id", nullable = false)
    private CategorieEntity categorie;

    @Column(name = "gender")
    private String gender;
}
