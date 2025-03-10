import { Component, OnInit } from '@angular/core';
import { CategorieService } from '../../core/services/categorie.service';
import { Category } from '../../core/models/Category';
import { CategorieSchemeComponent } from "../../components/categorie-scheme/categorie-scheme.component";
import { NgFor } from '@angular/common';
import { ArticlesService } from '../../core/services/articles.service';
import { Article } from '../../core/models/Article';
import { ArticleSchemeComponent } from "../../components/article-scheme/article-scheme.component";
import { HeaderComponent } from "../../components/header/header.component";

@Component({
  selector: 'app-landing-page',
  imports: [CategorieSchemeComponent, NgFor, ArticleSchemeComponent, HeaderComponent],
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
        this.categories = response.success;
      }
    );
  }

  getAllArticles(): any {
    this.articlesService.getAllArticles().subscribe(
      (response: any) => {
        this.articles = response.success;
      }
    );
  }
}
