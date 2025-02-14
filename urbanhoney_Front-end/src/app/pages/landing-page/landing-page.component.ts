import { Component, OnInit } from '@angular/core';
import { CategorieService } from '../../core/services/categorie.service';
import { Category } from '../../core/models/Category';
import { CategorieSchemeComponent } from "../../components/categorie-scheme/categorie-scheme.component";
import { NgFor } from '@angular/common';
import { ArticlesService } from '../../core/services/articles.service';

@Component({
  selector: 'app-landing-page',
  imports: [CategorieSchemeComponent, NgFor],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.scss'
})
export class LandingPageComponent implements OnInit {
  categories: Array<Category> = [];

  constructor(private categorieService: CategorieService, private articlesService: ArticlesService) {}

  ngOnInit(): void {
    this.getCategories();
    this.getAllArticles();
  }

  normalizeArray(arr: string[]): string[] {
    try {
      const combinedString = arr.join("");

      const cleanedString = combinedString.replace(/[\[\]]/g, ""); 
      const splitColors = cleanedString.split(",");

      const colorList = splitColors.map(color => color);

      return colorList;
    } catch (error) {
      console.error("Erreur lors de la normalisation du tableau de couleurs :", error);
      return [];
    }
  }
  

  getCategories(): any {
    this.categorieService.getAllCategories().subscribe(
      (response: any) => {
        console.log(response);
        this.categories = response.success;
      }
    );
  }

  getAllArticles(): any {
    this.articlesService.getAllArticles().subscribe(
      (response: any) => {
        let liste = response.success
        console.log(liste);
        console.log(this.normalizeArray(response.success[0].color));
      }
    );
  }
}
