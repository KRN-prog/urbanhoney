import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../../core/services/articles.service';
import { Article } from '../../core/models/Article';
import { ActivatedRoute } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-article-page',
  imports: [NgFor, NgIf],
  templateUrl: './article-page.component.html',
  styleUrl: './article-page.component.scss'
})
export class ArticlePageComponent implements OnInit {
  article!: Article;
  mainImage!: string;

  constructor(private route: ActivatedRoute, private articlesService: ArticlesService) {}

  ngOnInit(): void {
    this.getArticle();
  }

  getCurrentImage(image: any) {
    this.mainImage = image;
  }

  getArticle():any {
    this.articlesService.getArticleById(this.route.snapshot.paramMap.get('articleId')).subscribe({
      next: (response) => {
        this.article = response.success;
        this.mainImage = response.success.pictures[0];
        console.log(response.success);
        
      },
      error: (err) => {
        console.error('Erreur lors du chargement du produit :', err);
      }
    });
  }
}
