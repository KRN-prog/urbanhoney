import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../core/services/localStorage.service';
import { NgFor } from '@angular/common';
import { HeaderComponent } from "../../components/header/header.component";
import { ArticleCart } from '../../core/models/ArticleCart';

@Component({
  selector: 'app-cart-page',
  imports: [NgFor, HeaderComponent],
  templateUrl: './cart-page.component.html',
  styleUrl: './cart-page.component.scss'
})
export class CartPageComponent implements OnInit {
  uniqueArticles: ArticleCart[] = [];
  countMap: any = {};

  constructor(public localStorageService: LocalStorageService) {}

  ngOnInit(): void {
    console.log(this.localStorageService.getCard());

    this.localStorageService.getCard().forEach(item => {
      // Vérifier si l'objet existe déjà dans uniqueArticles
      const index = this.uniqueArticles.findIndex(obj => JSON.stringify(obj) === JSON.stringify(item));

      if (index !== -1) {
        // Si l'objet existe déjà, incrémenter son compteur
        this.countMap[index] += 1;
      } else {
        // Ajouter l'objet au tableau unique et initialiser son compteur
        this.uniqueArticles.push(item);
        this.countMap[this.uniqueArticles.length - 1] = 1;
      }
    });

    // Ajouter le compteur aux objets uniques
    const result = this.uniqueArticles.map((item, index) => ({ ...item, count: this.countMap[index] }));

    console.log(result);
    
  }

}
