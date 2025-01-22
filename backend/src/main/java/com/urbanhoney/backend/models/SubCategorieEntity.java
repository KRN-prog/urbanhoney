package com.urbanhoney.backend.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @Column(name = "sub_categorie_id")
    private Integer subCategorieId;

    @Column(name = "sub_categorie_name")
    private String subCategorieName;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "categorie_linked_id", referencedColumnName = "categorie_id", nullable = false)
    private CategorieEntity categorie;

    @Column(name = "gender")
    private String gender;
}
