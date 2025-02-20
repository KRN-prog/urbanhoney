import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../../core/services/articles.service';
import { Article } from '../../core/models/Article';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-article-page',
  imports: [],
  templateUrl: './article-page.component.html',
  styleUrl: './article-page.component.scss'
})
export class ArticlePageComponent implements OnInit {
  private article!: Article;

  constructor(private route: ActivatedRoute, private articlesService: ArticlesService) {}

  ngOnInit(): void {
    this.getArticle();
  }

  getArticle():any {
    this.articlesService.getArticleById(this.route.snapshot.paramMap.get('articleId')).subscribe(
      (response: any) => {
        console.log(response.success);
        this.article = response.success;
      }
    );
  }
}
