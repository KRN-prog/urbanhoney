import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../../core/services/articles.service';
import { ActivatedRoute } from '@angular/router';
import { Article } from '../../core/models/Article';
import { NgFor, NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { ArticleSchemeComponent } from "../../components/article-scheme/article-scheme.component";

@Component({
  selector: 'app-articles-page',
  imports: [NgIf, NgFor, ArticleSchemeComponent],
  templateUrl: './articles-page.component.html',
  styleUrl: './articles-page.component.scss'
})
export class ArticlesPageComponent implements OnInit {
  articles: Array<Article> = [];
  error: boolean = false;
  errorMsg!: HttpErrorResponse;

  constructor(private route: ActivatedRoute, private articlesService: ArticlesService) {}

  ngOnInit(): void {
    console.log(this.route.snapshot.paramMap.get('articleType'));  
    this.getArticlesByType();
  }

  getArticlesByType():any {
    this.route.snapshot.paramMap.get('articleType')?.replace(" ","_");
    this.articlesService.getArticlesByType(this.route.snapshot.paramMap.get('articleType')).subscribe({
      next: (response) => {
        this.articles = response.success;
        console.log(response.success);
        
      },
      error: (err) => {
        console.error(err);
        this.error = true;
        this.errorMsg = err;
      }
    });
  }
}
