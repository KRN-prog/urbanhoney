package com.urbanhoney.backend.models;

import java.util.List;

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
@Table(name = "articles")
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer article_id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "categorie_id")
    private Integer categorie_id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private List<String> price;

    @Column(name = "colors")
    private List<String> color;

    @Column(name = "size")
    private List<String> size;

    @Column(name = "pictures")
    private String pictures;

    @Column(name = "composition")
    private String composition;

    @Column(name = "entretien")
    private String entretien;
}
