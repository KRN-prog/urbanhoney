package com.urbanhoney.backend.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.urbanhoney.backend.converter.StringListConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class OrdersEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "order_articles",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private List<ArticlesEntity> articlesEntities;

    @Convert(converter = StringListConverter.class)
    @Column(name = "size")
    private List<String> size;

    @Convert(converter = StringListConverter.class)
    @Column(name = "color")
    private List<String> color;

    @Convert(converter = StringListConverter.class)
    @Column(name = "total")
    private List<String> total;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity userId;

    @Override
    public String toString() {
        return "OrdersEntity{id=" + orderId + ", total=" + total + "}";
    }
}
