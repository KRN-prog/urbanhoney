import { Component, OnInit } from '@angular/core';
import { CategorieService } from '../../core/services/categorie.service';
import { Category } from '../../core/models/Category';
import { CategorieSchemeComponent } from "../../components/categorie-scheme/categorie-scheme.component";
import { NgFor } from '@angular/common';
import { ArticlesService } from '../../core/services/articles.service';
import { Article } from '../../core/models/Article';

@Component({
  selector: 'app-landing-page',
  imports: [CategorieSchemeComponent, NgFor],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.scss'
})
export class LandingPageComponent implements OnInit {
  categories: Array<Category> = [];
  articles: Array<Article> = [];

  constructor(private categorieService: CategorieService, private articlesService: ArticlesService) {}

  ngOnInit(): void {
    this.getCategories();
    this.getAllArticles();
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
        console.log(response);
        this.articles = response.success;
      }
    );
  }
}
