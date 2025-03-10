import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../../core/services/articles.service';
import { ActivatedRoute } from '@angular/router';
import { Article } from '../../core/models/Article';
import { NgFor, NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { ArticleSchemeComponent } from "../../components/article-scheme/article-scheme.component";
import { CategorieService } from '../../core/services/categorie.service';
import { SubCategorie } from '../../core/models/SubCategorie';
import { HeaderComponent } from "../../components/header/header.component";

@Component({
  selector: 'app-articles-page',
  imports: [NgIf, NgFor, ArticleSchemeComponent, HeaderComponent],
  templateUrl: './articles-page.component.html',
  styleUrl: './articles-page.component.scss'
})
export class ArticlesPageComponent implements OnInit {
  subCategories: Array<SubCategorie> = [];
  articles: Array<Article> = [];
  error: boolean = false;
  errorMsg!: HttpErrorResponse;

  constructor(private route: ActivatedRoute, private articlesService: ArticlesService, private categoriesService: CategorieService) {}

  ngOnInit(): void {
    this.getArticlesByType();
    this.getAllSubCategoriesByCategoriename();
  }

  getAllSubCategoriesByCategoriename(): any {
    this.route.snapshot.paramMap.get('articleType')?.replace(" ","_");
    this.categoriesService.getAllSubCategoriesByCategoryName(this.route.snapshot.paramMap.get('articleType')).subscribe(
      (response: any) => {
        this.subCategories = response.success;
      }
    );;
  }

  getArticlesByType():any {
    this.route.snapshot.paramMap.get('articleType')?.replace(" ","_");
    this.articlesService.getArticlesByType(this.route.snapshot.paramMap.get('articleType')).subscribe({
      next: (response) => {
        this.articles = response.success;
      },
      error: (err) => {
        this.error = true;
        this.errorMsg = err;
      }
    });
  }

  getArticlesByCategorieName(categorieName: string): any {
    this.articlesService.getArticlesBySubCategoryName(categorieName).subscribe({
      next: (response) => {
        this.articles = response.success;
        this.error = false;
      },
      error: (err) => {
        this.error = true;
        this.errorMsg = err;
      }
    });
  }
}
