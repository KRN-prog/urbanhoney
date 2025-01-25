package com.urbanhoney.backend.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name = "articles")
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "sub_categorie_linked_id", nullable = false)
    private SubCategorieEntity subCategorieLinkedId;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private String price;

    @Column(name = "colors")
    private String color;

    @Column(name = "size")
    private String size;

    @Column(name = "pictures")
    private String pictures;

    @Column(name = "composition")
    private String composition;

    @Column(name = "entretient")
    private String entretien;

    @ManyToMany(mappedBy = "articlesEntities")
    @JsonIgnore
    private List<OrdersEntity> orders = new ArrayList<>();
}
